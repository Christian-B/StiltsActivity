package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public interface MultipleFormatsInterface extends MultipleInputsnterface {

    /**
     * @return the formatOfInputs
     */
    public List<String> getFormatsOfInputs();

    /**
     * @param formatsOfInputs the formatOfInpust to set
     */
    public void setFormatsOfInputs(List<String> formatsOfInputs);
      
}
