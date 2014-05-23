package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.List;
import net.sf.taverna.t2.activities.stilts.configuration.ConfigurationGroup;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Add Column PreProcessor based on an Operator which takes two variables.
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the name of the new colum, and where to place it. 
 * and will define what to put in the new column.
 * <p>
 * The rule of what to add is based on one of Stilts Operators that take two variables.
 * The user will supply the operator and the two variables (which may be complex)
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class AddColumnTwoVariablesPreProcessorBean extends AddColumnPreProcessorBean{
    
    private StiltsTwoVariableOperator operator;
    private final String OPERATOR_NAME = "Operator ";
    private final String VARIABLE1_NAME = "Left Variable ";
    private String variable1;
    private final String VARIABLE2_NAME = "Right Variable ";
    private String variable2;

    /**
     * Serialization constructor
     */
    public AddColumnTwoVariablesPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param operator A Stilts Operator
     * @param variable1 Any legal Stilts expression such as a column name, column name or a complex expression
     * @param variable2 Any legal Stilts expression such as a column name, column name or a complex expression
     * @param newColName 
     */
    public AddColumnTwoVariablesPreProcessorBean(StiltsTwoVariableOperator operator, String variable1, String variable2, String newColName){
        super(newColName);
        this.operator = operator;
        this.variable1 = variable1;
        this.variable2 = variable2;
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param operator A Stilts Operator
     * @param variable1 Any legal Stilts expression such as a column name, column name or a complex expression
     * @param variable2 Any legal Stilts expression such as a column name, column name or a complex expression
     * @param newColName
     * @param newColumnLocation
     * @param locationColumn 
     */
    public AddColumnTwoVariablesPreProcessorBean(StiltsTwoVariableOperator operator, String variable1, String variable2, 
            String newColName, StiltsLocationType newColumnLocation,  String locationColumn){ 
        super(newColName, newColumnLocation, locationColumn);
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
        super.checkValid();
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
    }
    
    @Override
    public String retrieveStilsCommand(){
        return super.retrieveStilsCommand() + "\"" + operator.retrieveStilsCommand(variable1, variable2) + "\"";
    }
            
    @Override
    public String title() {
        return "Add column using a two column operator";
    }

    @Override
    List<StiltsConfiguration> configurations() {
        List<StiltsConfiguration> configurations = super.configurations();
        configurations.add(new StiltsConfiguration (OPERATOR_NAME,  operator, true));
        configurations.add(new StiltsConfiguration (VARIABLE1_NAME,  variable1, true));
        configurations.add(new StiltsConfiguration (VARIABLE2_NAME,  variable2, true));
        return configurations;        
    }

    public void checkConfiguration(ConfigurationGroup configurationGroup) throws ActivityConfigurationException{ 
        super.checkConfiguration(configurationGroup);
        configurationGroup.checkString(VARIABLE1_NAME);
        configurationGroup.checkString(VARIABLE2_NAME);
        configurationGroup.checkClass(OPERATOR_NAME, StiltsTwoVariableOperator.class);
    }

}
