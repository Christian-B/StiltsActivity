package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.TwoInputsBean;
import net.sf.taverna.t2.activities.stilts.process.TMatch2Bean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class ExactMatchBean extends TMatch2Bean {
 
    private int numbertOfColumnsToMatch;

    public ExactMatchBean(){
        
    }
    
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
