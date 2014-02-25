package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class SingleFormatMultipleInputsBean extends MultipleInputsBean 
        implements SingleFormatMultipleInputsInterface, Serializable {
    private String formatOfInputs;

    @Override
    public String getFormatOfInputs() {
        return formatOfInputs;
    }

    @Override
    public void setFormatOfInputs(String formatOfInputs) {
        this.formatOfInputs = formatOfInputs;
    }
      
}
