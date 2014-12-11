package net.sf.taverna.t2.activities.table.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Bean to define the inputs for a process that takes just a single input table.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class SingleInputBean extends AbstractInputsBean implements Serializable {
    private TableInputFormat inputFormatEnum;
    private final static String INPUT_FORMAT_NAME = "Format of input table";
    private TableInputType inputTypeEnum;
    private final static String INPUT_TYPE_NAME = "Type of input table";
      
    /**
     * Serialization constructor
     */
    public SingleInputBean(){}
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param formatEnum
     * @param typeEnum 
     */
    public SingleInputBean(TableInputFormat formatEnum, TableInputType typeEnum){
        this.inputFormatEnum = formatEnum;
        this.inputTypeEnum = typeEnum;
    }

    /**
     * @return the formatOfInput
     */
    public TableInputFormat getFormatOfInput() {
        return inputFormatEnum;
    }

    /**
     * @param formatOfInput the formatOfInput to set
     */
    public void setFormatOfInput(TableInputFormat formatOfInput) {
        this.inputFormatEnum = formatOfInput;
    }

    /**
     * None getter method to obtain the Input format as an ENUM.
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return the typeOfInput
     */    
    public TableInputFormat retreiveStiltsInputFormat(){
        return inputFormatEnum;
    }
    
    /**
     * @return the typeOfInput
     */
    public String getTypeOfInput() {
        return inputTypeEnum.getUserName();
    }

    /**
     * None getter method to obtain the Input type as an ENUM.
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return the typeOfInput
     */    
    public TableInputType retreiveStiltsInputType(){
        return inputTypeEnum;
    }
    
    /**
     * @param typeOfInput the typeOfInput to set
     */
    public void setTypeOfInput(TableInputType typeOfInput) {
        this.inputTypeEnum = typeOfInput;
    }
    
    @Override
    public void checkValid() throws ActivityConfigurationException{
        if (inputFormatEnum == null){
            throw new ActivityConfigurationException("Input format not set.");
        }
        if (inputTypeEnum == null){
            throw new ActivityConfigurationException("Input type not set.");
        }
    }
    
    @Override
    public List<TableParameterConfiguration> configurations() {
        ArrayList<TableParameterConfiguration> configurations = new ArrayList<TableParameterConfiguration>();
        configurations.add(new TableParameterConfiguration (INPUT_FORMAT_NAME,  inputFormatEnum));
        configurations.add(new TableParameterConfiguration (INPUT_TYPE_NAME,  inputTypeEnum));
        return configurations;        
    }

}
  