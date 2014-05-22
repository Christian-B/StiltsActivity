package net.sf.taverna.t2.activities.stilts.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Bean to define the inputs for a process that takes just a single input table.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class SingleInputBean extends StitlsInputsBean implements Serializable {
    private StiltsInputFormat inputFormatEnum;
    private String INPUT_FORMAT_NAME = "Format of Input Table";
    private StiltsInputType inputTypeEnum;
    private String INPUT_TYPE_NAME = "Type of Input Table";
      
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
    public SingleInputBean(StiltsInputFormat formatEnum, StiltsInputType typeEnum){
        this.inputFormatEnum = formatEnum;
        this.inputTypeEnum = typeEnum;
    }

    /**
     * @return the formatOfInput
     */
    public String getFormatOfInput() {
        return inputFormatEnum.getStiltsName();
    }

    /**
     * @param formatOfInput the formatOfInput to set
     */
    public void setFormatOfInput(String formatOfInput) {
        this.inputFormatEnum = StiltsInputFormat.byStiltsName(formatOfInput);
    }

    public void resetFormatOfInput(StiltsInputFormat formatOfInput) {
        this.inputFormatEnum = formatOfInput;
    }

    /**
     * None getter method to obtain the Input format as an ENUM.
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return the typeOfInput
     */    
    public StiltsInputFormat retreiveStiltsInputFormat(){
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
    public StiltsInputType retreiveStiltsInputType(){
        return inputTypeEnum;
    }
    
    /**
     * @param typeOfInput the typeOfInput to set
     */
    public void setTypeOfInput(String typeOfInput) {
        this.inputTypeEnum = StiltsInputType.byUserName(typeOfInput);
    }
    
    public void resetTypeOfInput(StiltsInputType typeOfInput) {
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
    public List<StiltsConfiguration> configurations() {
        ArrayList<StiltsConfiguration> configurations = new ArrayList<StiltsConfiguration>();
        configurations.add(new StiltsConfiguration (INPUT_FORMAT_NAME,  inputFormatEnum, true));
        configurations.add(new StiltsConfiguration (INPUT_TYPE_NAME,  inputTypeEnum, true));
        return configurations;        
    }

}
  