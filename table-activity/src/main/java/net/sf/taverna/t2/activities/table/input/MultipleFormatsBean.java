package net.sf.taverna.t2.activities.table.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * AbstractBean for input where every table can have different formats and types
 * <p>
 * Super classes will determine how many tables there can be.
 * <p>
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class MultipleFormatsBean extends MultipleInputsBean 
        implements Serializable {
    private List<TableInputFormat> formatsOfInputs;
    private final String INPUT_FORMAT_NAME = "Format of input table ";
    
    /**
     * Serialization constructor
     */
    public MultipleFormatsBean(){}
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param typesOfInputsEnum
     * @param formatsOfInputEnums 
     */
    public MultipleFormatsBean(List<TableInputType> typesOfInputsEnum, List<TableInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum);
        this.formatsOfInputs = formatsOfInputEnums;
    }

    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (retreiveNumberOfInputs() < 2){
            throw new ActivityConfigurationException("Number of inputs must be 2 or greater.");
        }
        if (formatsOfInputs == null){
             throw new ActivityConfigurationException("Inputs formats not set.");
        }
        if (formatsOfInputs.size() != retreiveNumberOfInputs()){
             throw new ActivityConfigurationException("Length of Inputs formats: " + formatsOfInputs.size() 
                    + " does not match number of inputs: " + retreiveNumberOfInputs());
        }   
        super.checkValid();
    }

    /**
     * @return the formatsOfInputs
     */
    public List<TableInputFormat> getFormatsOfInputs() {
        return formatsOfInputs;
    }

    /**
     * @param formatsOfInputs the formatsOfInputs to set
     */
    public void setFormatsOfInputs(List<TableInputFormat> formatsOfInputs) {
        this.formatsOfInputs = formatsOfInputs;
    }
 
    @Override
   /**
     * Sets the number of inputs and adds if required assumes that the extra inputs will have the same type and format as the first.
     * @param numberOfInputs 
     * @throws NullPOinterException if called on a bean before all values have bee set at least once.
     */
    public void resetNumberOfInputs(int numberOfInputs){
        super.resetNumberOfInputs(numberOfInputs);
        while (formatsOfInputs.size() < numberOfInputs){
            formatsOfInputs.add(formatsOfInputs.get(0));
        }
    }

    @Override
    public List<TableParameterConfiguration> configurations() {
        ArrayList<TableParameterConfiguration> configurations = new ArrayList<TableParameterConfiguration>();
        for (int i = 0; i < retreiveNumberOfInputs(); i++){
            configurations.add(new TableParameterConfiguration("Input table " + (i+1) + " type", this.getTypesOfInputs().get(i)));
            configurations.add(new TableParameterConfiguration("Input table " + (i+1) + " format", formatsOfInputs.get(i)));
        }
        return configurations;
    }
    
 }
