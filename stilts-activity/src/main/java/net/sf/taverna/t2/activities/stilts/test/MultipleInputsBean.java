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
    
    private List<StiltsInputType> typesOfInputsEnums;
    
    protected MultipleInputsBean(List<StiltsInputType> typesOfInputsEnum){
        this.typesOfInputsEnums = typesOfInputsEnum;
    }
      
    protected MultipleInputsBean(){}
    
    public List<String> getTypesOfInputs() {
        ArrayList<String> typesOfInputs = new ArrayList<String>();
        for (StiltsInputType type:typesOfInputsEnums){
            typesOfInputs.add(type.getUserName());
        }
        return typesOfInputs;
    }

    public void setTypesOfInputs(List<String> typesOfInput) {
        typesOfInputsEnums = new ArrayList<StiltsInputType>();
        for (String type:typesOfInput){
            typesOfInputsEnums.add(StiltsInputType.byUserName(type));
        }
    }

    /**
     * None getter method to obtain the Input types as ENUMs.
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return the typeOfInput
     */    
    public List<StiltsInputType> retreiveStiltsInputsType(){
        return typesOfInputsEnums;
    }
    

    /**
     * None getter method to obtain the number of inputs
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return 
     */
    public abstract int retreiveNumberOfInputs();
    
    public void checkValid() throws ActivityConfigurationException{
        if (typesOfInputsEnums == null){
             throw new ActivityConfigurationException("Inputs types not set.");
        }
        if (typesOfInputsEnums.size() != retreiveNumberOfInputs()){
             throw new ActivityConfigurationException("Length of Inputs types: " + typesOfInputsEnums.size() 
                    + " does not match number of inputs: " + retreiveNumberOfInputs());
        }   
    }
}
