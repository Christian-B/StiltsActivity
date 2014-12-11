package net.sf.taverna.t2.activities.table.ui.config.preprocess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.table.preprocess.UserSpecifiedPreProcessorBean;
import net.sf.taverna.t2.activities.table.ui.textfield.NoneEmptyTextField;

/**
 * By Command PreProcess Configuration Panel
 * <p>
 * Handles the specification of any preprocessor command 
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class UserSpecifiedPreProcessorConfigurationPanel extends TablePreProcessConfigurationPanel<UserSpecifiedPreProcessorBean>{
 
    private NoneEmptyTextField preProcessCommandField;
    
    private static final String COMMAND_LABEL = "Stils filter command (excluding the \"cmd=\")";
    private static final String STILS_HELP_PAGE = "http://www.star.bristol.ac.uk/~mbt/stilts/sun256/filterSteps.html";
    
    public UserSpecifiedPreProcessorConfigurationPanel(UserSpecifiedPreProcessorBean preprocessBean){
        super(preprocessBean);
        JLabel seeLabel = new JLabel ("See: " + STILS_HELP_PAGE);
        addNextRow(seeLabel, 2);
        JLabel commandLabel = new JLabel (COMMAND_LABEL);
        addNextRow(commandLabel, 2);
        preProcessCommandField = new NoneEmptyTextField();
        addNextRow(preProcessCommandField, 2);
        refreshConfiguration(preprocessBean);
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
    @Override
    public boolean checkValues() {
         // All valid, return true
        if (!preProcessCommandField.getText().trim().isEmpty()){
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
    @Override
    public boolean isConfigurationChanged() {
        return (!preProcessCommandField.getText().equals(preprocessBean.getPreProcessCommand()));
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        noteConfiguration();
        preprocessBean.setPreProcessCommand(preProcessCommandField.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(UserSpecifiedPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        preProcessCommandField.setText(preprocessBean.getPreProcessCommand());
    }
}
