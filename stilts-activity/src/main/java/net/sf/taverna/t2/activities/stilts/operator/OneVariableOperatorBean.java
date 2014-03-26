package net.sf.taverna.t2.activities.stilts.operator;

import net.sf.taverna.t2.activities.stilts.utils.StiltsOneVariableOperator;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Note: This was was an idea to split Operator from Preprocessor but is not currently being used.
 *
 * @author christian
 */
public class OneVariableOperatorBean implements StiltsOperatorBean{
    
    private StiltsOneVariableOperator operator;
    private String variable;

    public OneVariableOperatorBean(){  
    }

    public OneVariableOperatorBean(StiltsOneVariableOperator operator, String variable){
        super();
        this.operator = operator;
        this.variable = variable;
    }

    /**
     * @return the operator
     */
    public StiltsOneVariableOperator getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(StiltsOneVariableOperator operator) {
        this.operator = operator;
    }

    /**
     * @return the variable
     */
    public String getVariable() {
        return variable;
    }

    /**
     * @param variable the variable1 to set
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (variable == null){
            throw new ActivityConfigurationException("Variable not specified");
        }
        if (variable.trim().isEmpty()){
            throw new ActivityConfigurationException("Variable is empty");
        }
        if (operator == null ){
            throw new ActivityConfigurationException("Operator not specified");
        }
    }
 
    @Override
    public String retrieveStilsCommand(){
        return "\"" + operator.retrieveStilsCommand(variable) + "\"";
    }
            
}
