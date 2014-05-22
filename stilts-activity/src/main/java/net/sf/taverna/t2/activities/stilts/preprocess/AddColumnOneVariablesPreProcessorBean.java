package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Add Column PreProcessor based on an Operator which takes a single variable.
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the name of the new colum, and where to place it. 
 * and will define what to put in the new column.
 * <p>
 * The rule of what to add is based on one of Stilts Operators that take a single variable.
 * The user will supply the operator and the variable (which may be complex)
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class AddColumnOneVariablesPreProcessorBean extends AddColumnPreProcessorBean{
    
    private StiltsOneVariableOperator operator;
    private final String OPERATOR_NAME = "Operator ";
    private String variable;
    private final String VARIABLE_NAME = "Variable ";

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
     * @param operator A Stilts Operator
     * @param variable Any legal Stilts expression such as a column name, column name or a complex expression
     * @param newColName 
     */
    public AddColumnOneVariablesPreProcessorBean(StiltsOneVariableOperator operator, String variable, String newColName){
        super(newColName);
        this.operator = operator;
        this.variable = variable;
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param operator A Stilts Operator
     * @param variable Any legal Stilts expression such as a column name, column name or a complex expression
     * @param newColName
     * @param newColumnLocation
     * @param locationColumn 
     */
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
   
    @Override
    public String title() {
        return "Add column using a one column operator";
    }

    @Override
    public List<StiltsConfiguration> configurations() {
        List<StiltsConfiguration> configurations = super.configurations();
        configurations.add(new StiltsConfiguration (OPERATOR_NAME,  operator, true));
        configurations.add(new StiltsConfiguration (VARIABLE_NAME,  variable, true));
        return configurations;        
    }
}
 