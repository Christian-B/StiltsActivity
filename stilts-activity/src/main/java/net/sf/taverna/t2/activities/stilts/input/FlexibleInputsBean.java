package net.sf.taverna.t2.activities.stilts.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Bean for any number of inputs where every table can have different formats and types
 * <p>
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class FlexibleInputsBean extends MultipleFormatsBean 
        implements Serializable {
    private int numberOfInputs;
    private final String NUMBER_OF_INPUTS_NAME = "Number of input tables";

    /**
     * Serialization constructor
     */
    public FlexibleInputsBean(){}
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * @param typesOfInputsEnum
     * @param formatsOfInputEnums 
     */
    public FlexibleInputsBean(List<StiltsInputType> typesOfInputsEnum, List<StiltsInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum, formatsOfInputEnums );
        this.numberOfInputs = formatsOfInputEnums.size();
    }

    /**
     * @return the numberOfInputs
     */
    public final int getNumberOfInputs() {
        return numberOfInputs;
    }

    /**
     * @param numberOfInputs the numberOfInputs to set
     */
    public final void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }
      
    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (numberOfInputs < 2){
            throw new ActivityConfigurationException("Number of inputs must be 2 or greater.");
        }
        super.checkValid();
    }
    
    @Override
    public final int retreiveNumberOfInputs() {
        return getNumberOfInputs();
    }

    /**
     * Resests the number of inputs, adjusting the length of the type and info arrays.
     * 
     * @param numberOfInputs 
     * @throws NullPointerException if called on a bean before all values have bee set at least once.
     */
    @Override
    public void resetNumberOfInputs(int numberOfInputs){
        this.numberOfInputs = numberOfInputs;
        super.resetNumberOfInputs(numberOfInputs);
    }     
    
    @Override
    public List<StiltsConfiguration> configurations() {
        List<StiltsConfiguration> configurations = super.configurations();
        configurations.add(new StiltsConfiguration (NUMBER_OF_INPUTS_NAME,  numberOfInputs, true));
        return configurations;        
    }
}   

