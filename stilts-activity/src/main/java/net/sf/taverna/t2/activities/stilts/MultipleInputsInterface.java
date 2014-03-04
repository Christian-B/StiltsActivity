package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public interface MultipleInputsInterface extends AbstractStiltsInterface {

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
    public List<String> getTypesOfInputs();

    /**
     * @param typesOfInputs the typeOfInputs to set
     */
    public void setTypesOfInputs(List<String> typesOfInputs);
    
    /**
     * @return the fixedNumberOfInputs
     */
    public boolean isFixedNumberOfInputs();

    /**
     * @param fixedNumberOfInputs the fixedNumberOfInputs to set
     */
    public void setFixedNumberOfInputs(boolean fixedNumberOfInputs);

      
}
