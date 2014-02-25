package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class MultipleFormatsBean extends MultipleInputsBean 
        implements MultipleFormatsInterface, Serializable {
    private List<String> formatOfInputs;

    @Override
    public List<String> getFormatOfInputs() {
        return formatOfInputs;
    }

    @Override
    public void setFormatOfInputs(List<String> formatOfInput) {
        this.formatOfInputs = formatOfInput;
    }
      
}
