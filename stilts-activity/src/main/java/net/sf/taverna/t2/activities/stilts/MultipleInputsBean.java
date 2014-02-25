package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class MultipleInputsBean extends AbstractStiltsBean 
        implements MultipleInputsnterface, Serializable {
    private int numberOfInputs;
    private List<String> typeOfInputs;

    @Override
    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    @Override
    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    @Override
    public List<String> getTypeOfInputs() {
        return typeOfInputs;
    }

    @Override
    public void setTypeOfInputs(List<String> typeOfInput) {
        this.typeOfInputs = typeOfInput;
    }
      
}
