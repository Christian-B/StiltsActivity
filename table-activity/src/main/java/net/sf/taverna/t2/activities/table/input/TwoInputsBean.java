package net.sf.taverna.t2.activities.table.input;

import java.io.Serializable;
import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;

/**
 * Bean that defines exactly two input tables which can be of different types and formats.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * 
 */
public class TwoInputsBean extends MultipleFormatsBean 
        implements Serializable {
 
    /**
     * Serialization constructor
     */
    public TwoInputsBean(){}
    
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
    public TwoInputsBean(List<TableInputType> typesOfInputsEnum, List<TableInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum, formatsOfInputEnums );
    }
     
    @Override
    public final int retreiveNumberOfInputs() {
        return 2;
    }

    protected boolean flexibleNumberOfTables(){
        return false;
    }

    /*
     * Should never be called, but just incase ignores any call with the value 2.
     * 
     * @param numberOfInputs Must be 2
     * @throws UnsupportedOperationException if called with anything but 2
     */
    public void resetNumberOfInputs(int numberOfInputs){
        if (numberOfInputs != 2){
            throw new UnsupportedOperationException("Illegal call!");   
        }
    }

}
