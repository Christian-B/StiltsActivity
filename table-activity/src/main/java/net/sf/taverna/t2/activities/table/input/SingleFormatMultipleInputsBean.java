package net.sf.taverna.t2.activities.table.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
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
    private TableInputFormat formatOfInputs;
    private final String INPUT_FORMAT_NAME = "Format of input tables";
    private int numberOfInputs;

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
    public SingleFormatMultipleInputsBean(List<TableInputType> typesOfInputsEnum, TableInputFormat inputsFormatEnum){
        super(typesOfInputsEnum);
        this.formatOfInputs = inputsFormatEnum;
        numberOfInputs = typesOfInputsEnum.size();
    }
    
    @Override
    public int retreiveNumberOfInputs() {
        return numberOfInputs;
    }

    protected boolean flexibleNumberOfTables(){
        return true;
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
    public TableInputFormat getFormatOfInputs() {
        return formatOfInputs;
    }

    /**
     * @param formatOfInputs the formatOfInputs to set
     */
    public void setFormatOfInputs(TableInputFormat formatOfInputs) {
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
    public  List<TableParameterConfiguration> configurations() {
        ArrayList<TableParameterConfiguration> configurations = new ArrayList<TableParameterConfiguration>();
        configurations.add(new TableParameterConfiguration("All table format", formatOfInputs));
        for (int i = 0; i < retreiveNumberOfInputs(); i++){
            configurations.add(new TableParameterConfiguration("Input table " + (i+1) + " type", this.getTypesOfInputs().get(i)));
        }
        return configurations;
    }
}
 