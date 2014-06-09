package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.preprocess.StiltsPreProcessBean;
import net.sf.taverna.t2.activities.stilts.process.StiltsProcessBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Bean for a {@link StiltsActivity Stilts Activity}.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class StiltsBean implements StiltsInterface, Serializable{
    private StiltsPreProcessBean preprocess;
    private StiltsOutputFormat outputFormat;
    private final String OUTPUT_FORMAT_NAME = "Output table format";
    private StiltsOutputType outputType;
    private final String OUTPUT_TYPE_NAME = "Output table type";
    private StiltsProcessBean process;
    private boolean debugMode = true;
    private final String DEBUG_NAME = "Debug mode";

    private static final String OUTPUT_CATEGORY = "outputs";
    private static final String OUTPUT_TITLE = "Settings for Output Table";
    private static final String MISCELLANEOUS_CATEGORY = "miscellaneous";
    private static final String MISCELLANEOUS_TITLE = "Miscellaneous Settings";
    public static final String INPUTS_CATEGORY = "inputs";
    public static final String PROCESS_CATEGORY = "process";
    public static final String PREPROCESS_CATEGORY = "preprocess";
   
    public StiltsBean() {}
    
    public StiltsBean(StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, StiltsOutputType outputTypeEnum,
            boolean debugMode)  throws ActivityConfigurationException{
        this(null, process, outputFormatEnum, outputTypeEnum, debugMode);
    }
    
    public StiltsBean(StiltsPreProcessBean preprocessor, StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, StiltsOutputType outputTypeEnum,
            boolean debugMode)  throws ActivityConfigurationException{
        this.preprocess = preprocessor;
        this.process = process;
        this.outputFormat = outputFormatEnum;
        this.outputType = outputTypeEnum;
        this.debugMode = debugMode;
    }

    public StiltsBean(StiltsInterface other) {
        this.process = other.getProcess();
        this.preprocess = other.getPreprocess();
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
    
    /**
     * Check the bean and its sub Beans have the expected values set in the expected way.
     * <p>
     * Note: Only basic checking such as the presence of a value is done.
     * Typically checks that a String value is not null or empty but not that the value makes sense.
     * Cam detect if an integer which is expected to be positive is, but can not check if the value is too high.
     * @throws ActivityConfigurationException Thrown if the bean and its current contents are known not to be valid.
     */
    public void checkValid() throws ActivityConfigurationException{
        if (getOutputFormat() == null){
            throw new ActivityConfigurationException("Output format not set.");
        } 
        if (getOutputType() == null){
            throw new ActivityConfigurationException("Output type not set.");
        }
        process.checkValid();
        if (preprocess != null){
            preprocess.checkValid();
        }
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

    /**
     * @return the preprocess
     */
    public StiltsPreProcessBean getPreprocess() {
        return preprocess;
    }

    /**
     * @param preprocess the preprocess to set
     */
    public void setPreprocess(StiltsPreProcessBean preprocess) {
        this.preprocess = preprocess;
    }

    public String title() {
        if (preprocess == null){
            return process.title();
        } else {
             return process.title() + " and " + preprocess.title();
        }
     }

    public List<StiltsConfiguration> configurations(){
        List<StiltsConfiguration> configurations = new ArrayList<StiltsConfiguration>();
        configurations.addAll(process.getInputs().configurations());
        configurations.addAll(process.configurations());
        if (preprocess != null){
            configurations.addAll(preprocess.configurations());
        }
        configurations.add(new StiltsConfiguration (OUTPUT_FORMAT_NAME,  outputFormat));
        configurations.add(new StiltsConfiguration (OUTPUT_TYPE_NAME,  outputType));
        configurations.add(new StiltsConfiguration (DEBUG_NAME,  debugMode));
        return configurations;
    }

}
  

