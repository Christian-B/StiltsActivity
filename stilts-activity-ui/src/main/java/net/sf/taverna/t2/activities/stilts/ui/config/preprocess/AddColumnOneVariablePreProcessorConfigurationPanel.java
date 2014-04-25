package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnOneVariablesPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;

/**
 * add Column based on a single variable PreProcess Configuration Panels
 * <p>
 * Allows the specification of the operator and the variable, while location and name of that column are handled by the super class.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AddColumnOneVariablePreProcessorConfigurationPanel extends AddColumnPreProcessConfigurationPanel<AddColumnOneVariablesPreProcessorBean>{
 
    private JTextField variableField;
    private JComboBox<StiltsOneVariableOperator> operatorSelector;
    
    private static final String VARIABLE_LABEL = "Variable for expression";
    private static final String OPERATOR_LABEL = "Operator";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    public AddColumnOneVariablePreProcessorConfigurationPanel(AddColumnOneVariablesPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);        
    }
    
    @Override
    void addEditable(AddColumnOneVariablesPreProcessorBean preprocessBean){   
        super.addEditable(preprocessBean);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = nextRow();
        JLabel operatorLabel = new JLabel (OPERATOR_LABEL);
        add(operatorLabel, c);
        operatorSelector = new JComboBox<StiltsOneVariableOperator>(StiltsOneVariableOperator.values());
        operatorSelector.setSelectedItem(preprocessBean.getOperator());
        operatorSelector.setRenderer(listCellRenderer);
        c.gridx = 1;
        add(operatorSelector, c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy+=1;
        JLabel nameLabel = new JLabel ("Variables can be column names, column Number (prefixed with $)");
        add(nameLabel, c);
        
        c.gridy+=1;
        JLabel exprLabel = new JLabel ("Variables can also be any valid Stilts Expression:");
        add(exprLabel, c);
        
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy+=1;
        JLabel variableLabel = new JLabel (VARIABLE_LABEL);
        add(variableLabel, c);
        c.gridx = 1;
        variableField = new JTextField(preprocessBean.getVariable(), 20);
        add(variableField, c);
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
        if (variableField.getText().trim().isEmpty()){
            String message = VARIABLE_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + VARIABLE_LABEL, JOptionPane.ERROR_MESSAGE);
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
        if (!variableField.getText().equals(preprocessBean.getVariable())){
            return true;
        }
        if (!operatorSelector.getSelectedItem().equals(preprocessBean.getOperator())){
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
        preprocessBean = new AddColumnOneVariablesPreProcessorBean();
        super.noteConfiguration();
        preprocessBean.setVariable(variableField.getText());
        preprocessBean.setOperator((StiltsOneVariableOperator)operatorSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(AddColumnOneVariablesPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        variableField.setText(preprocessBean.getVariable());
        operatorSelector.setSelectedItem(preprocessBean.getOperator());
    }
}
