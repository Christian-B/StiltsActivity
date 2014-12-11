package net.sf.taverna.t2.activities.table.input;

import java.io.Serializable;
import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
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
    public FlexibleInputsBean(List<TableInputType> typesOfInputsEnum, List<TableInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum, formatsOfInputEnums );
        this.numberOfInputs = formatsOfInputEnums.size();
    }

    /**
     * @return the numberOfInputs
     */
    public final int getNumberOfInputs() {
        return numberOfInputs;
    }

    protected boolean flexibleNumberOfTables(){
        return true;
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
    public List<TableParameterConfiguration> configurations() {
        List<TableParameterConfiguration> configurations = super.configurations();
        return configurations;        
    }
    
}   

