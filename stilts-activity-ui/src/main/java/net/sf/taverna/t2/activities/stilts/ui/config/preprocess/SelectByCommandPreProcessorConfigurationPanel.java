package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.SelectByCommandPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.ui.config.FunctionWizard;

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
 
    private JLabel commandLabel;
    
    private static final String COMMAND_LABEL = "Stils select command (excluding the \"cmd=select\")";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    public SelectByCommandPreProcessorConfigurationPanel(SelectByCommandPreProcessorBean preprocessBean){
        super(preprocessBean);
        JLabel seeLabel = new JLabel ("See: " + STILS_HELP_PAGE);
        addNextRow(seeLabel, 2);
        addNextRow(new JLabel (COMMAND_LABEL), 2);
        commandLabel = new JLabel(preprocessBean.getCommand());
        addNextRow(commandLabel, 2);
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
                    "Please enter a boolean command to compute the value of the new column\n"
                    +"This must be according to the Stils rules for an expressions\n"
                    +"See: http://www.star.bris.ac.uk/~mbt/stilts/sun256/jel.html\n"
                    +"Warning: Entering an incorrect expression could cause the workfow to hang!",
                    "Select test expression",
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
                String command = FunctionWizard.getBoolean("Expression for select test");
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
         if (commandLabel.getText().trim().isEmpty()){
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
        if (!commandLabel.getText().equals(preprocessBean.getCommand())){
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
        preprocessBean.setCommand(commandLabel.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(SelectByCommandPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        commandLabel.setText(preprocessBean.getCommand());
    }
}
