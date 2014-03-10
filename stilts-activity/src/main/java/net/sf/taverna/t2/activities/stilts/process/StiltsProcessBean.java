package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.StitlsInputsBean;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class StiltsProcessBean {
    private StitlsInputsBean inputs;

    /**
     * @return the inputs
     */
    public StitlsInputsBean getInputs() {
        return inputs;
    }

    /**
     * @param inputs the inputs to set
     */
    public void setInputs(StitlsInputsBean inputs) {
        this.inputs = inputs;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        inputs.checkValid();
    }

}
