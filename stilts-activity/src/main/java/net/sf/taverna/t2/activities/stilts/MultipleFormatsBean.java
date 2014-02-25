package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class MultipleFormatsBean extends MultipleInputsBean 
        implements MultipleFormatsInterface, Serializable {
    private List<String> formatsOfInputs;

    @Override
    public List<String> getFormatsOfInputs() {
        return formatsOfInputs;
    }

    @Override
    public void setFormatsOfInputs(List<String> formatsOfInput) {
        this.formatsOfInputs = formatsOfInput;
    }
      
}
