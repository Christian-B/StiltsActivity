package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public abstract class StiltsPreProcessBean {
 
    public abstract void checkValid() throws ActivityConfigurationException;

}
