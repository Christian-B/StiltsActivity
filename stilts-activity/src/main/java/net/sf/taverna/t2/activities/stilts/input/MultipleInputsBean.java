package net.sf.taverna.t2.activities.stilts.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.configuration.ConfigurationGroup;
import net.sf.taverna.t2.activities.stilts.configuration.ListConfiguration;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Abstract Bean for when there are multiple inputs.
 * <p>
 * Super classes will determine if these must all be the same type or different types.
 * Super classes will determine how many tables there can be.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class MultipleInputsBean extends StitlsInputsBean implements Serializable {
    
    private List<StiltsInputType> typesOfInputs;
    final String INPUT_TYPE_NAME = "Type of input table ";
    final String NUMBER_OF_INPUTS_NAME = "Input Table";
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param typesOfInputsEnum 
     */
    protected MultipleInputsBean(List<StiltsInputType> typesOfInputsEnum){
        this.typesOfInputs = typesOfInputsEnum;
    }
      
     /**
     * Serialization constructor
     */
   protected MultipleInputsBean(){}
    
    /**
     * None getter method to obtain the number of inputs
     * 
     * Method name does not start with "get" so it is not picked up by the Serializer
     * @return 
     */
    public abstract int retreiveNumberOfInputs();
   
    protected abstract boolean flexibleNumberOfTables();
    
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
    
    @Override
    List<StiltsConfiguration> configurations() {
        ArrayList<StiltsConfiguration> configurations = new ArrayList<StiltsConfiguration>();
        configurations.add(new ListConfiguration (NUMBER_OF_INPUTS_NAME, INPUT_TYPE_NAME,  (List<Object>)(List<?>) typesOfInputs, flexibleNumberOfTables()));
        return configurations;        
    }
    
    public void checkConfiguration(ConfigurationGroup configurationGroup) throws ActivityConfigurationException{ 
        configurationGroup.checkClasses(NUMBER_OF_INPUTS_NAME, INPUT_TYPE_NAME, StiltsInputType.class);
    }

    public void noteConfiguration(ConfigurationGroup configurationGroup) throws ActivityConfigurationException {
        typesOfInputs = new ArrayList<StiltsInputType>();
        for(StiltsConfiguration config: configurationGroup.getList(NUMBER_OF_INPUTS_NAME, INPUT_TYPE_NAME)){
            typesOfInputs.add((StiltsInputType)config.getItem());
        }
    }

}
