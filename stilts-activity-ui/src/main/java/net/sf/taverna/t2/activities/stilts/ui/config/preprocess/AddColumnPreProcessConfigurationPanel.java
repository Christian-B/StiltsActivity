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
 
    private JComboBox<StiltsLocationType> locationTypeSelector;
    private ColumnIdTextField locationColumnField;
    private ColumnIdTextField newColumnNameField;
    private JLabel commandLabel;
    
    private static final String NEW_COLUMN_NAME_LABEL = "Name of new Column";
    private static final String NEW_COLUMN_LOCATION = "Location to add new column";
    private static final String NEW_COLUMN_REFFERENCE = "Reference Column for location" ;       
    private static final String COMMAND_LABEL = "add command";
    
    protected static ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    AddColumnPreProcessConfigurationPanel(AddColumnPreProcessorBean preprocessBean){
        super(preprocessBean);
    }

    @Override
    void initGui(AddColumnPreProcessorBean preprocessBean){ 
        addNextRow(new JLabel(NEW_COLUMN_NAME_LABEL), 1);
        newColumnNameField = new ColumnIdTextField(preprocessBean.getNewColName(), ColumnIdTextField.NAME_ONLY);
        addNextCol(newColumnNameField, 1);
        addNextRow(new JLabel(NEW_COLUMN_LOCATION), 1);
        StiltsLocationType locationType = preprocessBean.getNewColumnLocation();
        locationTypeSelector = new JComboBox<StiltsLocationType>(StiltsLocationType.values());
        locationTypeSelector.setSelectedItem(locationType);
        locationTypeSelector.setRenderer(listCellRenderer);
        addNextCol(locationTypeSelector, 1);
        addNextRow(new JLabel(NEW_COLUMN_REFFERENCE), 1);
        if (preprocessBean.getLocationColumn() != null){
            locationColumnField = new ColumnIdTextField(preprocessBean.getLocationColumn(), ColumnIdTextField.ALLOW_ID);
        } else {
            locationColumnField = new ColumnIdTextField("", ColumnIdTextField.ALLOW_ID);
        }
        addNextCol(locationColumnField, 1);
        //JLabel seeLabel = new JLabel ("See: " + STILS_HELP_PAGE);
        //add(seeLabel, c);
        JLabel commandLabel = new JLabel (COMMAND_LABEL);
        addNextRow(commandLabel, 1);
        this.commandLabel = new JLabel(preprocessBean.getCommand());
        addNextCol(this.commandLabel, 1);
        
        addNextRow (manualButton(), 1);
        addNextCol (wizardButton(), 1);
        
        //commandField = newTextField(preprocessBean.getCommand());
        //addNextCol(commandField);
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
                String message = NEW_COLUMN_REFFERENCE + " can not be empty when " + NEW_COLUMN_LOCATION + " set to " + locationTypeSelector.getSelectedItem(); 
                JOptionPane.showMessageDialog(this, message, "Empty " + NEW_COLUMN_REFFERENCE, JOptionPane.ERROR_MESSAGE);
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

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(AddColumnPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        newColumnNameField.setText(preprocessBean.getNewColName());
        locationTypeSelector.setSelectedItem(preprocessBean.getNewColumnLocation());
        locationColumnField.setText(preprocessBean.getNewColName());
        commandLabel.setText(preprocessBean.getCommand());
    }
}
