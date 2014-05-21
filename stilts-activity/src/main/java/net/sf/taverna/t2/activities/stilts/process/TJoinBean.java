package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.MultipleFormatsBean;

/**
 *
 * @author christian
 */
public class TJoinBean extends StiltsProcessBean {
 
    /**
     * Serialization constructor
     */
    public TJoinBean(){
        
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
    public TJoinBean(MultipleFormatsBean inputBean){
        setInputs(inputBean);
    }

    @Override
    public String retrieveStilsCommand() {
        return "tjoin";
    }
   
    @Override
    public String title() {
        return "Join Tables based on Row number";
    }

}
