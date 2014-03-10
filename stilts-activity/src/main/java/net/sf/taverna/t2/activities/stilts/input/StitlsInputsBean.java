package net.sf.taverna.t2.activities.stilts.input;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public abstract class StitlsInputsBean {

    public abstract void checkValid() throws ActivityConfigurationException;
    
}
