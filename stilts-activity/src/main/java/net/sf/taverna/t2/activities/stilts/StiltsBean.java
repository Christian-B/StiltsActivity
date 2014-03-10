package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import net.sf.taverna.t2.activities.stilts.process.StiltsProcessBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class StiltsBean implements StiltsInterface, Serializable{
    //StilsPreProcessorBean preprocessor;
    private StiltsOutputFormat outputFormat;
    private StiltsOutputType outputType;
    private StiltsProcessBean process;
    private boolean debugMode = true;
    //StilsPostProcessorBean postprocessor;

    public StiltsBean() {}
    
    public StiltsBean(StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, StiltsOutputType outputTypeEnum,
            boolean debugMode)  throws ActivityConfigurationException{
        this.process = process;
        this.outputFormat = outputFormatEnum;
        this.outputType = outputTypeEnum;
        this.debugMode = debugMode;
    }

    public StiltsBean(StiltsInterface other) {
        this.process = other.getProcess();
        this.outputFormat = other.getOutputFormat();
        this.outputType = other.getOutputType();
        this.debugMode = other.isDebugMode();
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
    public StiltsProcessBean getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(StiltsProcessBean process) {
        this.process = process;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        if (getOutputFormat() == null){
            throw new ActivityConfigurationException("Output format not set.");
        }
        if (getOutputType() == null){
            throw new ActivityConfigurationException("Output type not set.");
        }
        process.checkValid();
    }

    /**
     * @return the outputFormat
     */
    public StiltsOutputFormat getOutputFormat() {
        return outputFormat;
    }

    /**
     * @param outputFormat the outputFormat to set
     */
    public void setOutputFormat(StiltsOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * @return the outputType
     */
    public StiltsOutputType getOutputType() {
        return outputType;
    }

    /**
     * @param outputType the outputType to set
     */
    public void setOutputType(StiltsOutputType outputType) {
        this.outputType = outputType;
    }

}
