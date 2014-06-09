package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.stilts.ui.config.FunctionWizard;
import net.sf.taverna.t2.activities.stilts.ui.textfield.ColumnIdTextField;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;

@SuppressWarnings("serial")
public class AddColumnPreProcessConfigurationPanel extends StiltsPreProcessConfigurationPanel<AddColumnPreProcessorBean>{
 
    private final JComboBox<StiltsLocationType> locationTypeSelector;
    private final JLabel locationColumnLabel;
    private final ColumnIdTextField locationColumnField;
    private final ColumnIdTextField newColumnNameField;
    private final JLabel commandLabel;
    
    
    private static final String NEW_COLUMN_NAME_LABEL = "Name of new Column";
    private static final String NEW_COLUMN_LOCATION = "Location to add new column";
    private static final String AFTER_COLUMN_REFFERENCE = "Column to place new column after" ;       
    private static final String BEFORE_COLUMN_REFFERENCE = "Column to place new column before" ;       
    private static final String END_COLUMN_REFFERENCE = "New column will be placed at the end" ;       
    private static final String COMMAND_LABEL = "add command";
    
    protected static ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    AddColumnPreProcessConfigurationPanel(AddColumnPreProcessorBean preprocessBean){
        super(preprocessBean);

        addNextRow(new JLabel(NEW_COLUMN_NAME_LABEL), 1);
        newColumnNameField = new ColumnIdTextField(preprocessBean.getNewColName(), ColumnIdTextField.NAME_ONLY);
        addNextCol(newColumnNameField, 1);

        addNextRow(new JLabel(NEW_COLUMN_LOCATION), 1);
        locationTypeSelector = new JComboBox<StiltsLocationType>(StiltsLocationType.values());
        locationTypeSelector.setRenderer(listCellRenderer);
        addNextCol(locationTypeSelector, 1);

        locationColumnLabel = new JLabel();
        addNextRow(locationColumnLabel, 1);
        if (preprocessBean.getLocationColumn() != null){
            locationColumnField = new ColumnIdTextField(preprocessBean.getLocationColumn(), ColumnIdTextField.ALLOW_ID);
        } else {
            locationColumnField = new ColumnIdTextField("", ColumnIdTextField.ALLOW_ID);
        }
        addNextCol(locationColumnField, 1);
        locationTypeSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateOnLocationTypeSelector();
            }
        });
        
        JLabel commandMessage = new JLabel (COMMAND_LABEL);
        addNextRow(commandMessage, 1);
        this.commandLabel = new JLabel(preprocessBean.getCommand());
        addNextCol(this.commandLabel, 1);
        
        addNextRow (manualButton(), 1);
        addNextCol (wizardButton(), 1);
        
        refreshConfiguration(preprocessBean);    
    }
    
    private JButton manualButton(){
        JButton manualButton = new JButton("Enter command manually");
        manualButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                Frame frame = Frame.getFrames()[0]; 
                String command = (String)JOptionPane.showInputDialog(
                    frame,
                    "Please enter a command to compute the value of the new column\n"
                    +"This must be according to the Stils rules for an expressions\n"
                    +"See: http://www.star.bris.ac.uk/~mbt/stilts/sun256/jel.html\n"
                    +"Warning: Entering an incorrect expression could cause the workfow to hang!",
                    "Add Column Expression",
                    JOptionPane.PLAIN_MESSAGE);
                if (command != null && !command.trim().isEmpty()){
                    commandLabel.setText(command);
                }
            }
        });    
        return manualButton;
    }
    
    private JButton wizardButton(){
        JButton wizardButton = new JButton("Use command wizard");
        wizardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String command = FunctionWizard.getCommand("Expression for add column");
                if (command != null && !command.trim().isEmpty()){
                    commandLabel.setText(command);
                }
            }
        });    
        return wizardButton;
    }

    /**
      * Check that user values in UI are valid
     * @return 
      */
    public boolean checkValues() {
        if (newColumnNameField.getText().trim().isEmpty()){
            String message = NEW_COLUMN_NAME_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + NEW_COLUMN_NAME_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!locationTypeSelector.getSelectedItem().equals(StiltsLocationType.END)){
            if (locationColumnField.getText().trim().isEmpty()){
                String message = locationColumnLabel.getText() + " can not be empty when " + NEW_COLUMN_LOCATION + " set to " + locationTypeSelector.getSelectedItem(); 
                JOptionPane.showMessageDialog(this, message, "Empty " + locationColumnLabel.getText(), JOptionPane.ERROR_MESSAGE);
                return false;
            }        
        }
        if (commandLabel.getText().trim().isEmpty()){
            String message = COMMAND_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + COMMAND_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    public boolean isConfigurationChanged() {
        if (!newColumnNameField.getText().equals(preprocessBean.getNewColName())){
            return true;
        }
        if (!locationTypeSelector.getSelectedItem().equals(StiltsLocationType.END)){
            if (!locationColumnField.getText().equals(preprocessBean.getLocationColumn())){
                return true;
            }
        }        
        if (!commandLabel.getText().equals(preprocessBean.getCommand())){
             return true;
        }
        return false;
    }
    
    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    public void noteConfiguration() {
        preprocessBean = new AddColumnPreProcessorBean();
        preprocessBean.setNewColName(newColumnNameField.getText());
        preprocessBean.setNewColumnLocation((StiltsLocationType)locationTypeSelector.getSelectedItem());
        if (preprocessBean.getNewColumnLocation().equals(StiltsLocationType.END)){
            preprocessBean.setLocationColumn(null);
        } else {
            preprocessBean.setLocationColumn(locationColumnField.getText());
        }
        preprocessBean.setCommand(commandLabel.getText());
    }

    private void updateOnLocationTypeSelector(){
        StiltsLocationType type = (StiltsLocationType)locationTypeSelector.getSelectedItem();
        switch (type){
            case AFTER:
                locationColumnLabel.setText(AFTER_COLUMN_REFFERENCE );
                locationColumnField.setVisible(true);
                break;
            case BEFORE:
                locationColumnLabel.setText(BEFORE_COLUMN_REFFERENCE );
                locationColumnField.setVisible(true);
                break;
            case END:
                locationColumnLabel.setText(END_COLUMN_REFFERENCE );
                locationColumnField.setVisible(false);
                break;
            default:
                throw new UnsupportedOperationException(type + " not supported");
        }
    }
    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(AddColumnPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        newColumnNameField.setText(preprocessBean.getNewColName());
        locationTypeSelector.setSelectedItem(preprocessBean.getNewColumnLocation());
        updateOnLocationTypeSelector();
        locationColumnField.setText(preprocessBean.getLocationColumn());
        commandLabel.setText(preprocessBean.getCommand());
    }
}
