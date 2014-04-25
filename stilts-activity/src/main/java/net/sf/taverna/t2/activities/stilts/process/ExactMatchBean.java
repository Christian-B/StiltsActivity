package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.TwoInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Extension of Stilts TMatch which matches/joines where values of one table exactly match does in another.
 * <p>
 * The number of columns to match is specified here.
 * The name/ number of the columnns to match are supplied in the workflow as input parameters.
 * <p>
 * 
 * @author christian
 */
public class ExactMatchBean extends TMatch2Bean {
 
    /**
     * The number of columns to match is specified here.
     * The name/ number of the columnns to match are supplied in the workflow as input parameters.
     */
    private int numbertOfColumnsToMatch;

    /**
     * Serialization constructor
     */
    public ExactMatchBean(){
        
    }
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param numbertOfColumnsToMatch
     * @param findEnum
     * @param fixcolsEnum
     * @param joinEnum
     * @param inputBean 
     */
    public ExactMatchBean(int numbertOfColumnsToMatch, StiltsFind findEnum, StiltsFixcols fixcolsEnum, StiltsJoin joinEnum, TwoInputsBean inputBean){
        super(findEnum, fixcolsEnum, joinEnum, inputBean);
        this.numbertOfColumnsToMatch = numbertOfColumnsToMatch;
    }
    
        /**
     * @return the numbertOfColumnsToMatch
     */
    public int getNumbertOfColumnsToMatch() {
        return numbertOfColumnsToMatch;
    }

    /**
     * @param numbertOfColumnsToMatch the numbertOfColumnsToMatch to set
     */
    public void setNumbertOfColumnsToMatch(int numbertOfColumnsToMatch) {
        this.numbertOfColumnsToMatch = numbertOfColumnsToMatch;
    }

    public void checkValid() throws ActivityConfigurationException{
        super.checkValid();
        if (numbertOfColumnsToMatch < 1){
            throw new ActivityConfigurationException("Number of columns to match must be 1 or more.");
        }
    }
}
