package net.sf.taverna.t2.activities.stilts.test;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class StilsProcessBean {
    private StilsInputsBean inputs;

    /**
     * @return the inputs
     */
    public StilsInputsBean getInputs() {
        return inputs;
    }

    /**
     * @param inputs the inputs to set
     */
    public void setInputs(StilsInputsBean inputs) {
        this.inputs = inputs;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        inputs.checkValid();
    }

}
