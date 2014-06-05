package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.SelectByCommandPreProcessorBean;

/**
 * Select by Command PreProcess Configuration Panel
 * <p>
 * Handles the Select command 
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SelectByCommandPreProcessorConfigurationPanel extends StiltsPreProcessConfigurationPanel<SelectByCommandPreProcessorBean>{
 
    private JTextField commandField;
    
    private static final String COMMAND_LABEL = "Stils add command (excluding the \"cmd=addcol\")";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    public SelectByCommandPreProcessorConfigurationPanel(SelectByCommandPreProcessorBean preprocessBean){
        super(preprocessBean);
    }
    
    @Override
    void initGui(SelectByCommandPreProcessorBean preprocessBean){ 
        JLabel seeLabel = new JLabel ("See: " + STILS_HELP_PAGE);
        addNextRow(seeLabel, 2);
        JLabel commandLabel = new JLabel (COMMAND_LABEL);
        addNextRow(commandLabel, 1);
        commandField = newTextField(preprocessBean.getCommand());
        addNextCol(commandField, 1);
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
    public boolean checkValues() {
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
        preprocessBean = new SelectByCommandPreProcessorBean();
        preprocessBean.setCommand(commandField.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(SelectByCommandPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        commandField = new JTextField(preprocessBean.getCommand());
    }
}
