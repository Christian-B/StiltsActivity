package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class SelectTwoVariablesPreProcessorBean extends StiltsPreProcessBean{
    
    private StiltsTwoVariableOperator operator;
    private String variable1;
    private String variable2;

    public SelectTwoVariablesPreProcessorBean(){  
    }

    public SelectTwoVariablesPreProcessorBean(StiltsTwoVariableOperator operator, String variable1, String variable2){
        this.operator = operator;
        this.variable1 = variable1;
        this.variable2 = variable2;
    }

    /**
     * @return the operator
     */
    public StiltsTwoVariableOperator getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(StiltsTwoVariableOperator operator) {
        this.operator = operator;
    }

    /**
     * @return the variable1
     */
    public String getVariable1() {
        return variable1;
    }

    /**
     * @param variable1 the variable1 to set
     */
    public void setVariable1(String variable1) {
        this.variable1 = variable1;
    }

    /**
     * @return the variable2
     */
    public String getVariable2() {
        return variable2;
    }

    /**
     * @param variable2 the variable2 to set
     */
    public void setVariable2(String variable2) {
        this.variable2 = variable2;
    }

    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (variable1 == null){
            throw new ActivityConfigurationException("Variable one not specified");
        }
        if (variable1.trim().isEmpty()){
            throw new ActivityConfigurationException("Variable one is empty");
        }
        if (variable2 == null){
            throw new ActivityConfigurationException("Variable two not specified");
        }
        if (variable2.trim().isEmpty()){
            throw new ActivityConfigurationException("Variable two is empty");
        }
        if (operator == null ){
            throw new ActivityConfigurationException("Operator not specified");
        }
        if (!operator.isBoolean()){
            throw new ActivityConfigurationException("Operator " + operator + " does not return boolean values.");
        }
        
    }
    
    @Override
    public String retrieveStilsCommand(){
        return "cmd=select \"" + operator.retrieveStilsCommand(variable1, variable2) + "\"";
    }
            
}
