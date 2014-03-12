package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.UserSpecifiedPreProcessorBean;

@SuppressWarnings("serial")
public class UserSpecifiedPreProcessorConfigurationPanel extends StiltsPreProcessConfigurationPanel<UserSpecifiedPreProcessorBean>{
 
    private JTextField preProcessCommandField;
    
    private static final String COMMAND_LABEL = "Stils filter command (excluding the \"cmd=\")";
    private static final String STILS_HELP_PAGE = "http://www.star.bristol.ac.uk/~mbt/stilts/sun256/filterSteps.html";
    
    public UserSpecifiedPreProcessorConfigurationPanel(UserSpecifiedPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        JLabel seeLabel = new JLabel ("See: " + STILS_HELP_PAGE);
        add(seeLabel, c);
        c.gridwidth = 1;
        c.gridy = 1;
        JLabel commandLabel = new JLabel (COMMAND_LABEL);
        add(commandLabel, c);
        c.gridx = 1;
        if (editable){
             preProcessCommandField = new JTextField(preprocessBean.getPreProcessCommand(), 20);
             add(preProcessCommandField, c);
        } else {
             JLabel preProcessCommandInfo = new JLabel(preprocessBean.getPreProcessCommand());
             add(preProcessCommandInfo, c);            
        }
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
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
    public boolean isConfigurationChanged() {
        return (!preProcessCommandField.getText().equals(preprocessBean.getPreProcessCommand()));
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        super.noteConfiguration();
        preprocessBean.setPreProcessCommand(preProcessCommandField.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(UserSpecifiedPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        preProcessCommandField = new JTextField(preprocessBean.getPreProcessCommand());
    }
}
