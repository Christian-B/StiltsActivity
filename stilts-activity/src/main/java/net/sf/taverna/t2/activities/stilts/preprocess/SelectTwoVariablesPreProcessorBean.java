package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Preprocess that selects columns based on a Stilts Operator, deleting any others.
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the select operator and the expressions it takes.
 * <p>
 * Based on
 *{@link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#select}
 * <p>
 * The variables must be a valid Stilts variables
 * {@linkhttp://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#jel}
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class SelectTwoVariablesPreProcessorBean extends StiltsPreProcessBean{
    
    /**
     * A Stilts operator that returns a boolean value
     */
    private StiltsTwoVariableOperator operator;
    private final String OPERATOR_NAME = "Operator ";
    /** 
     * Any valid Stilts expression which can be a column name, column number or a complex expression.
     */
    private String variable1;
    private final String VARIABLE1_NAME = "Left Variable ";
    /** 
     * Any valid Stilts expression which can be a column name, column number or a complex expression.
     */
    private String variable2;
    private final String VARIABLE2_NAME = "Right Variable ";

    /**
     * Serialization constructor
     */
    public SelectTwoVariablesPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param operator
     * @param variable1
     * @param variable2 
     */
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
    
    @Override
    public String title() {
        return "Select rows comparing two columns.";
    }

    @Override
    public List<StiltsConfiguration> configurations() {
        ArrayList<StiltsConfiguration> configurations = new ArrayList<StiltsConfiguration>();
        configurations.add(new StiltsConfiguration (OPERATOR_NAME,  operator, true));
        configurations.add(new StiltsConfiguration (VARIABLE1_NAME,  variable1, true));
        configurations.add(new StiltsConfiguration (VARIABLE2_NAME,  variable2, true));
        return configurations;        
    }

}
