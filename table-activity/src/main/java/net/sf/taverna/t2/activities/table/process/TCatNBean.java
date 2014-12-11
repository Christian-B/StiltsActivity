package net.sf.taverna.t2.activities.table.process;

import net.sf.taverna.t2.activities.table.input.MultipleFormatsBean;

/**
 * Concatenates two or more tables into a single table, these tabkes may have different formats
 * 
 * Implements {$link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#tcatn}
 *
 * @author christian
 */
public class TCatNBean extends AbstractProcessBean {
 
    /**
     * Serialization constructor
     */
    public TCatNBean(){
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
    public TCatNBean(MultipleFormatsBean inputBean){
        setInputs(inputBean);
    }

    @Override
    public String retrieveStilsCommand() {
        return "tcatn";
    }
    
        @Override
    public String title() {
        return "Concatenate Multiple tables with a different formats";
    }

}
