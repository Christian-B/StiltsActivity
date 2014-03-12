package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.preprocess.StiltsPreProcessBean;
import net.sf.taverna.t2.activities.stilts.process.StiltsProcessBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

/**
 *
 * @author christian
 */
public interface StiltsInterface {

    /**
     * @return the outputFormat
     */
    public StiltsOutputFormat getOutputFormat();

    /**
     * @return the outputType
     */
    public StiltsOutputType getOutputType();

    /**
     * @return the process
     */
    public StiltsProcessBean getProcess();
    
   /**
     * @return the preprocess
     */
    public StiltsPreProcessBean getPreprocess();
    
     /**
     * @return the debugMode
     */
    public boolean isDebugMode();


}
