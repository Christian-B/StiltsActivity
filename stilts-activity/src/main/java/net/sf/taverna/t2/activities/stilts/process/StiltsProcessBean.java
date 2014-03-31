package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.StitlsInputsBean;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public abstract class StiltsProcessBean {
    private StitlsInputsBean inputs;

    StiltsProcessBean(){}
    
    StiltsProcessBean(StitlsInputsBean inputs){
        this.inputs = inputs;
    }
    
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

    /**
     * Obtains the Stilts name for the Process.
     * 
     * Intentional not a get method to avoid confusing the any bean parser.
     * @return command as expected by Stilts
     */
    public abstract String retrieveStilsCommand();

}
