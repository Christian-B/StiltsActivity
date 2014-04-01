package net.sf.taverna.t2.activities.stilts.input;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Abstract base for all Input beans.
 * <p>
 * Used by the StiltsActivity to 
 * {@link net.sf.taverna.t2.activities.stilts.StiltsActivity#configureInputPorts(StitlsInputsBean) configure input ports}.
 * <p>
 * Used by the StiltsActivity to 
 * {@link net.sf.taverna.t2.activities.stilts.StiltsActivity#addInputParameters(net.sf.taverna.t2.activities.stilts.input.StitlsInputsBean, java.util.List, java.util.Map, net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback) To setup Stilts parameters}.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class StitlsInputsBean {

    /**
     * Check the bean has the expected values set in the expected way.
     * <p>
     * Note: Only basic checking such as the presence of a value is done.
     * Typically checks that a String value is not null or empty but not that the value makes sense.
     * Cam detect if an integer which is expected to be positive is, but can not check if the value is too high.
     * @throws ActivityConfigurationException Thrown if the bean and its current contents are known not to be valid.
     */
    public abstract void checkValid() throws ActivityConfigurationException;
    
}
