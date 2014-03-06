package net.sf.taverna.t2.activities.stilts.test;

import java.io.Serializable;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class StiltsBean implements Serializable{
    //StilsPreProcessorBean preprocessor;
    private StiltsOutputFormat outputFormatEnum;
    private StiltsOutputType outputTypeEnum;
    private StilsProcessBean process;
    private boolean debugMode = true;
    //StilsPostProcessorBean postprocessor;

    public StiltsBean() {}
    
    public StiltsBean(StilsProcessBean process, StiltsOutputFormat outputFormatEnum, StiltsOutputType outputTypeEnum,
            boolean debugMode) {
        this.process = process;
        this.outputFormatEnum = outputFormatEnum;
        this.outputTypeEnum = outputTypeEnum;
        this.debugMode = debugMode;
    }

    /**
     * @return the formatOfOutput
     */
    public String getFormatOfOutput() {
        return outputFormatEnum.getStiltsName();
    }

    /**
     * @param formatOfOutput the formatOfOutput to set
     */
    public void setFormatOfOutput(String formatOfOutput) {
        outputFormatEnum = StiltsOutputFormat.byStiltsName(formatOfOutput);
    }

    /**
     * @return the typeOfOutput
     */
    public String getTypeOfOutput() {
        return outputTypeEnum.getUserName();
    }

    /**
     * @param typeOfOutput the typeOfOutput to set
     */
    public void setTypeOfOutput(String typeOfOutput) {
        outputTypeEnum  = StiltsOutputType.byUserName(typeOfOutput);
    }
    
    /**
     * None getter method to obtain the Output type as an ENUM.
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return the typeOfOutput
     */    
    public StiltsOutputType retreiveStiltsOutputType(){
        return outputTypeEnum;
    }
    
    /**
     * @return the debugMode
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * @param debugMode the debugMode to set
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * @return the process
     */
    public StilsProcessBean getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(StilsProcessBean process) {
        this.process = process;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        if (outputFormatEnum == null){
            throw new ActivityConfigurationException("Output format not set.");
        }
        if (outputTypeEnum == null){
            throw new ActivityConfigurationException("Output type not set.");
        }
        process.checkValid();
    }
}
