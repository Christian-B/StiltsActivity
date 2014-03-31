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
     * Asks the bean to throw an ActivityConfigurationException if it is not valid.
     * @throws ActivityConfigurationException 
     */
    public abstract void checkValid() throws ActivityConfigurationException;
    
}
