package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnByCommandPreProcessorBean;

@SuppressWarnings("serial")
public class AddColumnByCommandPreProcessorConfigurationPanel extends AddColumnPreProcessConfigurationPanel<AddColumnByCommandPreProcessorBean>{
 
    private JTextField commandField;
    
    private static final String COMMAND_LABEL = "Stils add command (excluding the \"cmd=addcol\")";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    public AddColumnByCommandPreProcessorConfigurationPanel(AddColumnByCommandPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
    }
    
    @Override
    void addEditable(AddColumnByCommandPreProcessorBean preprocessBean){ 
        super.addEditable(preprocessBean);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = nextRow();
        JLabel seeLabel = new JLabel ("See: " + STILS_HELP_PAGE);
        add(seeLabel, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy+=1;
        JLabel commandLabel = new JLabel (COMMAND_LABEL);
        add(commandLabel, c);
        c.gridx = 1;
        commandField = new JTextField(preprocessBean.getCommand(), 20);
        add(commandField, c);
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
    public boolean checkValues() {
         // All valid, return true
        if (!super.checkValues()){
            return false;
        }
        if (commandField.getText().trim().isEmpty()){
            String message = COMMAND_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + COMMAND_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
     * @return 
      */
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (!commandField.getText().equals(preprocessBean.getCommand())){
             return true;
        }
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean = new AddColumnByCommandPreProcessorBean();
        super.noteConfiguration();
        preprocessBean.setCommand(commandField.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(AddColumnByCommandPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        commandField = new JTextField(preprocessBean.getCommand());
    }
}
