package net.sf.taverna.t2.activities.stilts.operator;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Note: This was was an idea to split Operator from Preprocessor but is not currently being used.
 *
 * @author christian
 */
public interface StiltsOperatorBean {
 
    public abstract void checkValid() throws ActivityConfigurationException;

    public abstract String retrieveStilsCommand();

}
