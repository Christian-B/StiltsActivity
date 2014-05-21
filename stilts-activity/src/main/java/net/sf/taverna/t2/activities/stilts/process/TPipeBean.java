package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;

/**
 * Simple process which just sends the table to output.
 * 
 * Can be used together with a processor or just as a format convertor.
 * 
 * @author christian
 */
public class TPipeBean extends StiltsProcessBean {
 
    /**
     * Serialization constructor
     */
    public TPipeBean(){
        
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
    public TPipeBean(SingleInputBean inputBean){
        super(inputBean);
    }

    @Override
    public String retrieveStilsCommand() {
        return "tpipe";
    }
    
    @Override
    public String title() {
        //This signifies that the title should come from the preprocessor
        return null;
    }

}
