package net.sf.taverna.t2.activities.table;

import net.sf.taverna.t2.activities.table.preprocess.AbstractPreProcessBean;
import net.sf.taverna.t2.activities.table.process.AbstractProcessBean;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;

/**
 * This interface allows the {@link TableActivityConfigurationBean TableActivityConfigurationBean} to reconfigure itself from a configuration panel without knowing the panel.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public interface AbstractTableActivityConfiguration {

    /**
     * @return the outputFormat
     */
    public TableOutputFormat getOutputFormat();

    /**
     * @return the outputType
     */
    public TableOutputType getOutputType();

    /**
     * @return the process
     */
    public AbstractProcessBean getProcess();
    
   /**
     * @return the preprocess
     */
    public AbstractPreProcessBean getPreprocess();
    
     /**
     * @return the debugMode
     */
    public boolean isDebugMode();


}
