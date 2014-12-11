package net.sf.taverna.t2.activities.table.ui.config.process;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.table.input.AbstractInputsBean;
import net.sf.taverna.t2.activities.table.process.ExactMatchBean;
import net.sf.taverna.t2.activities.table.ui.textfield.IntegerTextField;

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
   
    private IntegerTextField numberOfColumnsToMatchField;
    
    private static final String NUMBER_OF_COLUMNS_TO_MATCH_LABEL = "Number of colimns to Match";
   
    public ExactMatchConfigurationPanel(ExactMatchBean processBean){
        super(processBean);
        addNextRow(new JLabel(NUMBER_OF_COLUMNS_TO_MATCH_LABEL),1 );
        numberOfColumnsToMatchField = new IntegerTextField();
        addNextCol(numberOfColumnsToMatchField, 1);
        refreshConfiguration(processBean);
    }

    @Override
    public boolean checkValues() {
        Integer numberOfInputs = numberOfColumnsToMatchField.getValue();
        if (numberOfInputs == null){
            String message = NUMBER_OF_COLUMNS_TO_MATCH_LABEL + " must be specified";
            JOptionPane.showMessageDialog(this, message, "Missing " + NUMBER_OF_COLUMNS_TO_MATCH_LABEL, JOptionPane.ERROR_MESSAGE);
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
     public void noteConfiguration(AbstractInputsBean inputBean) {
        processBean = new ExactMatchBean();    	
        super.noteConfiguration(inputBean);
        processBean.setNumbertOfColumnsToMatch(Integer.parseInt(numberOfColumnsToMatchField.getText()));
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(ExactMatchBean inputbean) {
        super.refreshConfiguration(inputbean);
        numberOfColumnsToMatchField.setValue(processBean.getNumbertOfColumnsToMatch());
    }
}
