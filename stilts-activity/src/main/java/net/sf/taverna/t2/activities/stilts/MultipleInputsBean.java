package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class MultipleInputsBean extends AbstractStiltsBean 
        implements MultipleInputsInterface, Serializable {
    private int numberOfInputs;
    private boolean fixedNumberOfInputs;        
    private List<String> typesOfInputs;

    public MultipleInputsBean(){}
    
    public MultipleInputsBean(MultipleInputsInterface other){
        super(other);
        this.fixedNumberOfInputs = other.isFixedNumberOfInputs();
        this.numberOfInputs = other.getNumberOfInputs();
        this.typesOfInputs = other.getTypesOfInputs();
    }
    
    @Override
    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    @Override
    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    @Override
    public List<String> getTypesOfInputs() {
        return typesOfInputs;
    }

    @Override
    public void setTypesOfInputs(List<String> typesOfInput) {
        this.typesOfInputs = typesOfInput;
    }

    /**
     * @return the fixedNumberOfInputs
     */
    @Override
    public boolean isFixedNumberOfInputs() {
        return fixedNumberOfInputs;
    }

    /**
     * @param fixedNumberOfInputs the fixedNumberOfInputs to set
     */
    @Override
    public void setFixedNumberOfInputs(boolean fixedNumberOfInputs) {
        this.fixedNumberOfInputs = fixedNumberOfInputs;
    }
      
}
