package net.sf.taverna.t2.activities.stilts.process;

import java.util.List;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.input.SingleFormatMultipleInputsBean;

/**
 * Concatenates two or more similar tables into a single table
 * 
 * <p>
 * As all the tables must be the same format the inputBean must be a SingleFormatMultipleInputsBean
 * 
 * Implements {$link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#tcat}
 * @author christian
 */
public class TCatBean extends StiltsProcessBean {
 
    /**
     * Serialization constructor
     */
    public TCatBean(){
        
    }
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * @param inputBean 
     */
    public TCatBean(SingleFormatMultipleInputsBean inputBean){
        setInputs(inputBean);
    }

    @Override
    public String retrieveStilsCommand() {
        return "tcat";
    }

    @Override
    public String title() {
        return "Concatenate Multiple tables with a Shared format";
    }
    
}
