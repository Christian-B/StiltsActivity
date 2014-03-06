package net.sf.taverna.t2.activities.stilts.test;

import java.io.Serializable;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Stilts activity configuration bean.
 * 
 */
public class SingleInputBean extends StilsInputsBean implements Serializable {
    private StiltsInputFormat inputFormatEnum;
    private StiltsInputType inputTypeEnum;
      
    public SingleInputBean(){}
    
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
    
    public void checkValid() throws ActivityConfigurationException{
        if (inputFormatEnum == null){
            throw new ActivityConfigurationException("Input format not set.");
        }
        if (inputTypeEnum == null){
            throw new ActivityConfigurationException("Input type not set.");
        }

    }
}
