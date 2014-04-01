package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class AddColumnOneVariablesPreProcessorBean extends AddColumnPreProcessorBean{
    
    private StiltsOneVariableOperator operator;
    private String variable;

    /**
     * Serialization constructor
     */
    public AddColumnOneVariablesPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param operator
     * @param variable
     * @param newColName 
     */
    public AddColumnOneVariablesPreProcessorBean(StiltsOneVariableOperator operator, String variable, String newColName){
        super(newColName);
        this.operator = operator;
        this.variable = variable;
    }

    public AddColumnOneVariablesPreProcessorBean(StiltsOneVariableOperator operator, String variable, 
            String newColName, StiltsLocationType newColumnLocation,  String locationColumn){ 
        super(newColName, newColumnLocation, locationColumn);
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
        super.checkValid();
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
        return super.retrieveStilsCommand() + "\"" + operator.retrieveStilsCommand(variable) + "\"";
    }
            
}
