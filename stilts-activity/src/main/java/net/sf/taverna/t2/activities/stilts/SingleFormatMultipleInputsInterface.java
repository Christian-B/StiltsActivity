package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public interface SingleFormatMultipleInputsInterface extends MultipleInputsnterface {

    /**
     * @return the formatOfInputs
     */
    public String getFormatOfInputs();

    /**
     * @param formatOfInputs the formatOfInpust to set
     */
    public void setFormatOfInputs(String formatOfInputs);
      
}
