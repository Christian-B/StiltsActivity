package net.sf.taverna.t2.activities.stilts.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Stilts activity configuration bean.
 * 
 */
public abstract class MultipleInputsBean extends StilsInputsBean implements Serializable {
    
    private List<StiltsInputType> typesOfInputs;
    
    protected MultipleInputsBean(List<StiltsInputType> typesOfInputsEnum){
        this.typesOfInputs = typesOfInputsEnum;
    }
      
    protected MultipleInputsBean(){}
    
    /**
     * None getter method to obtain the number of inputs
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return 
     */
    public abstract int retreiveNumberOfInputs();
    
    public void checkValid() throws ActivityConfigurationException{
        if (getTypesOfInputs() == null){
             throw new ActivityConfigurationException("Inputs types not set.");
        }
        if (typesOfInputs.size() != retreiveNumberOfInputs()){
             throw new ActivityConfigurationException("Length of Inputs types: " + typesOfInputs.size() 
                    + " does not match number of inputs: " + retreiveNumberOfInputs());
        }   
    }

    /**
     * @return the typesOfInputs
     */
    public List<StiltsInputType> getTypesOfInputs() {
        return typesOfInputs;
    }

    /**
     * @param typesOfInputs the typesOfInputs to set
     */
    public void setTypesOfInputs(List<StiltsInputType> typesOfInputs) {
        this.typesOfInputs = typesOfInputs;
    }

}
