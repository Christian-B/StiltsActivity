package net.sf.taverna.t2.activities.stilts.input;

import java.io.Serializable;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Input bean for processes that accept multiple tables but which must all have the same format.
 * 
 * Note: The restrictions that all tables must have the same format is a Stilts one.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class SingleFormatMultipleInputsBean extends MultipleInputsBean 
        implements Serializable {
    private StiltsInputFormat formatOfInputs;
    private final String INPUT_FORMAT_NAME = "Format of input tables";
    private int numberOfInputs;
    private final String NUMBER_OF_INPUTS_NAME = "Number of input tables";

    /**
     * Serialization constructor
     */
    public SingleFormatMultipleInputsBean(){}
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param typesOfInputsEnum
     * @param inputsFormatEnum 
     */
    public SingleFormatMultipleInputsBean(List<StiltsInputType> typesOfInputsEnum, StiltsInputFormat inputsFormatEnum){
        super(typesOfInputsEnum);
        this.formatOfInputs = inputsFormatEnum;
        numberOfInputs = typesOfInputsEnum.size();
    }
    
    @Override
    public int retreiveNumberOfInputs() {
        return numberOfInputs;
    }

    /**
     * @return the numberOfInputs
     */
    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    /**
     * @param numberOfInputs the numberOfInputs to set
     */
    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }
      
    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (numberOfInputs < 2){
            throw new ActivityConfigurationException("Number of inputs must be 2 or greater.");
        }
        if (formatOfInputs == null){
            throw new ActivityConfigurationException("Inputs format not set.");
        }
        super.checkValid();
    }

    /**
     * @return the formatOfInputs
     */
    public StiltsInputFormat getFormatOfInputs() {
        return formatOfInputs;
    }

    /**
     * @param formatOfInputs the formatOfInputs to set
     */
    public void setFormatOfInputs(StiltsInputFormat formatOfInputs) {
        this.formatOfInputs = formatOfInputs;
    }

    /**
     * Resests the number of inputs, adjusting the length of the type and info arrays.
     * 
     * @param numberOfInputs 
     * @throws NullPOinterException if called on a bean before all values have bee set at least once.
     */
    public void resetNumberOfInputs(int numberOfInputs){
        this.numberOfInputs = numberOfInputs;
        super.resetNumberOfInputs(numberOfInputs);
    }     

    @Override
    public List<StiltsConfiguration> configurations() {
        List<StiltsConfiguration> configurations = super.configurations();
        configurations.add(new StiltsConfiguration (INPUT_FORMAT_NAME,  formatOfInputs, true));
        configurations.add(new StiltsConfiguration (NUMBER_OF_INPUTS_NAME,  numberOfInputs, true));
        return configurations;        
    }

}
 