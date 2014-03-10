package net.sf.taverna.t2.activities.stilts.test;

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
    public StilsProcessBean getProcess();
    
    /**
     * @return the debugMode
     */
    public boolean isDebugMode();


}
