package net.sf.taverna.t2.activities.stilts.ui.config.process;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.ui.config.input.TwoInputsConfigurationPanel;

/**
 * Exact Match Process Configuration Panels
 * <p>
 * Specifies the number of columns to match exactly.
 * Workflow will specify the columns to match.
 * Superclass adds the specification of Stilts find join and fixCols parameters.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ExactMatchConfigurationPanel extends TMatch2ConfigurationPanel <ExactMatchBean>{
   
    private JTextField numberOfColumnsToMatchField;
    
    private static final String NUMBER_OF_COLUMNS_TO_MATCH_LABEL = "Number of colimns to Match";
   
    public ExactMatchConfigurationPanel(ExactMatchBean processBean){
        super(processBean);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        processPanel.add(new JLabel("Exact Match Tool"), c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        JLabel numberOfColumnsToMatchLabel = new JLabel(NUMBER_OF_COLUMNS_TO_MATCH_LABEL);
        processPanel.add(numberOfColumnsToMatchLabel,c );
        c.gridx = 1;
        numberOfColumnsToMatchField = new JTextField(processBean.getNumbertOfColumnsToMatch()+"");
        numberOfColumnsToMatchField.setColumns(3);
        processPanel.add(numberOfColumnsToMatchField, c);
    }

    @Override
    public boolean checkValues() {
        String numberOfInputsString = numberOfColumnsToMatchField.getText();
        if (numberOfInputsString == null || numberOfInputsString.isEmpty()){
            String message = NUMBER_OF_COLUMNS_TO_MATCH_LABEL + " must be specified";
            JOptionPane.showMessageDialog(this, message, "Missing " + NUMBER_OF_COLUMNS_TO_MATCH_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int numberOfColumnsToMatch;
        try{
            numberOfColumnsToMatch = Integer.parseInt(numberOfInputsString);
        } catch (NumberFormatException ex){
            String message = NUMBER_OF_COLUMNS_TO_MATCH_LABEL + " is not a valid possitive integer.";
            JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_COLUMNS_TO_MATCH_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;            
        }
        if (numberOfColumnsToMatch < 1){
            String message = NUMBER_OF_COLUMNS_TO_MATCH_LABEL + " should be 1 or more.";
            JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_COLUMNS_TO_MATCH_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;                        
        }
        return true;
    }
    
    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        String numberOfColumnsToMatchString = numberOfColumnsToMatchField.getText();
        if (numberOfColumnsToMatchString == null || numberOfColumnsToMatchString.isEmpty()){
            return true;
        }
        int numberOfColumnsToMatch;
        try{
            numberOfColumnsToMatch = Integer.parseInt(numberOfColumnsToMatchString);
        } catch (NumberFormatException ex){
            return true;            
        }
        if (numberOfColumnsToMatch !=  processBean.getNumbertOfColumnsToMatch()){
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
        processBean = new ExactMatchBean();    	
        super.noteConfiguration();
        processBean.setNumbertOfColumnsToMatch(Integer.parseInt(numberOfColumnsToMatchField.getText()));
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(ExactMatchBean inputbean) {
        super.refreshConfiguration(inputbean);
        numberOfColumnsToMatchField = new JTextField(processBean.getNumbertOfColumnsToMatch()+"");
    }
}
