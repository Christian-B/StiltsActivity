package net.sf.taverna.t2.activities.stilts.ui.config.operator;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.operator.OneVariableOperatorBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOneVariableOperator;

/**
 * Note: This was was an idea to split Operator from Preprocessor but is not currently being used.
 * 
 * @author christian
 */
@SuppressWarnings("serial")
public class OneVariableOperatorConfigurationPanel extends OperatorPreProcessConfigurationPanel<OneVariableOperatorBean>{
 
    private final JTextField variableField;
    private final JComboBox<StiltsOneVariableOperator> operatorSelector;
    
    private static final String VARIABLE_LABEL = "Variable for expression";
    private static final String OPERATOR_LABEL = "Operator";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    public OneVariableOperatorConfigurationPanel(OneVariableOperatorBean preprocessBean){
        super(preprocessBean);        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
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
        if (!variableField.getText().equals(operatorBean.getVariable())){
            return true;
        }
        if (!operatorSelector.getSelectedItem().equals(operatorBean.getOperator())){
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
        operatorBean = new OneVariableOperatorBean();
        operatorBean.setVariable(variableField.getText());
        operatorBean.setOperator((StiltsOneVariableOperator)operatorSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(OneVariableOperatorBean operator) {
        variableField.setText(operator.getVariable());
        operatorSelector.setSelectedItem(operator.getOperator());
    }
}
