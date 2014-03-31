package net.sf.taverna.t2.activities.stilts.ui.config.operator;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.operator.TwoVariablesOperatorBean;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;

/**
 * Note: This was was an idea to split Operator from Preprocessor but is not currently being used.
 * 
 * @author christian
 */
@SuppressWarnings("serial")
public class TwoVariablesOperatorConfigurationPanel extends OperatorPreProcessConfigurationPanel<TwoVariablesOperatorBean>{
 
    private final JTextField variable1Field;
    private final JTextField variable2Field;
    private final JComboBox<StiltsTwoVariableOperator> operatorSelector;
    
    private static final String VARIABLE1_LABEL = "First Variable for expression";
    private static final String VARIABLE2_LABEL = "Second Variable for expression";
    private static final String OPERATOR_LABEL = "Operator";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    public TwoVariablesOperatorConfigurationPanel(TwoVariablesOperatorBean preprocessBean){
        super(preprocessBean);        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        JLabel operatorLabel = new JLabel (OPERATOR_LABEL);
        add(operatorLabel, c);
        operatorSelector = new JComboBox<StiltsTwoVariableOperator>(StiltsTwoVariableOperator.values());
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
        JLabel variable1Label = new JLabel (VARIABLE1_LABEL);
        add(variable1Label, c);
        c.gridx = 1;
        variable1Field = new JTextField(preprocessBean.getVariable1(), 20);
        add(variable1Field, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy+=1;
        JLabel variable2Label = new JLabel (VARIABLE2_LABEL);
        add(variable2Label, c);
        c.gridx = 1;
        variable2Field = new JTextField(preprocessBean.getVariable2(), 20);
        add(variable2Field, c);
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
    public boolean checkValues() {
         // All valid, return true
        if (variable1Field.getText().trim().isEmpty()){
            String message = VARIABLE1_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + VARIABLE1_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (variable2Field.getText().trim().isEmpty()){
            String message = VARIABLE2_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + VARIABLE2_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
     * @return 
      */
    public boolean isConfigurationChanged() {
        if (!variable1Field.getText().equals(operatorBean.getVariable1())){
            return true;
        }
        if (!variable2Field.getText().equals(operatorBean.getVariable2())){
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
        operatorBean = new TwoVariablesOperatorBean();
        operatorBean.setVariable1(variable1Field.getText());
        operatorBean.setVariable2(variable2Field.getText());
        operatorBean.setOperator((StiltsTwoVariableOperator)operatorSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(TwoVariablesOperatorBean operatorBean) {
        variable1Field.setText(operatorBean.getVariable1());
        variable2Field.setText(operatorBean.getVariable2());
        operatorSelector.setSelectedItem(operatorBean.getOperator());
    }
}
