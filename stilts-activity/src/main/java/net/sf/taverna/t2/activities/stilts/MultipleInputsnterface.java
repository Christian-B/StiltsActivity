package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public interface MultipleInputsnterface extends AbstractStiltsInterface {

    /**
     * @return the numberOfInputs
     */
    public int getNumberOfInputs();

    /**
     * @param numberOfInputs the numberOfInputs to set
     */
    public void setNumberOfInputs(int numberOfInputs);
    
    /**
     * @return the typeOfInputs
     */
    public List<String> getTypeOfInputs();

    /**
     * @param typeOfInputs the typeOfInputs to set
     */
    public void setTypeOfInputs(List<String> typeOfInputs);
      
}
