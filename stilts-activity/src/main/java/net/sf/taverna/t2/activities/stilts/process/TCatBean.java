package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.SingleFormatMultipleInputsBean;

/**
 *
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
     * 
     * @param inputBean 
     */
    public TCatBean(SingleFormatMultipleInputsBean inputBean){
        setInputs(inputBean);
    }

    @Override
    public String retrieveStilsCommand() {
        return "tcat";
    }
}
