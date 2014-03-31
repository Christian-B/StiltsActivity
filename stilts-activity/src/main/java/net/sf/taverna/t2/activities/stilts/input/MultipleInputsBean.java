package net.sf.taverna.t2.activities.stilts.input;

import java.io.Serializable;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Abstract Bean for when there are multiple inputs.
 * <p>
 * Super classes will determine if these must all be the same type or different types.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class MultipleInputsBean extends StitlsInputsBean implements Serializable {
    
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
   
    @Override
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

    /**
     * Sets the number of inputs and adds if required assumes that the extra inputs will have the same type as the first.
     * @param numberOfInputs 
     * @throws NullPOinterException if called on a bean before all values have bee set at least once.
    */
    public void resetNumberOfInputs(int numberOfInputs){
        while (typesOfInputs.size() < numberOfInputs){
            typesOfInputs.add(typesOfInputs.get(0));
        }
    }
}
