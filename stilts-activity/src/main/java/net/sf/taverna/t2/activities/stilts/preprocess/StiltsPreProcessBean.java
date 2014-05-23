package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.Collection;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Abstract Bean for all Stilts PreProcess Beans.
 * <p>
 * Defines that every Preprocess Bean must be able to check itself for validity 
 * and returns the String Stilts uses for this functionality.
 * <p>
 * Any Stilts process can take a Preprocess. 
 * Although the most likely use is with a TPipe process which does nothing more than pass the table on.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class StiltsPreProcessBean {
 
    /**
     * Check the bean has the expected values set in the expected way.
     * <p>
     * Note: Only basic checking such as the presence of a value is done.
     * Typically checks that a String value is not null or empty but not that the value makes sense.
     * Cam detect if an integer which is expected to be positive is, but can not check if the value is too high.
     * @throws ActivityConfigurationException Thrown if the bean and its current contents are known not to be valid.
     */
    public abstract void checkValid() throws ActivityConfigurationException;

    /**
     * Retrieves the String that Stilts uses for this preprocess, based on the current values.
     * <p>
     * 
     * @return 
     */
    public abstract String retrieveStilsCommand();

    public abstract String title();

    public String asRawTableRowsHtml() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public abstract List<StiltsConfiguration> configurations(); 

    public abstract void checkConfiguration(List<StiltsConfiguration> newConfigurations) throws ActivityConfigurationException;
    
}
