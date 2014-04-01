package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.TwoInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class TMatch2Bean extends StiltsProcessBean {
 
    private StiltsFind findValue;
    private StiltsJoin joinValue;
    private StiltsFixcols fixcolsValue;

    /**
     * Serialization constructor
     */
    public TMatch2Bean(){
    }
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param findEnum
     * @param fixcolsEnum
     * @param joinEnum
     * @param inputBean 
     */
    public TMatch2Bean(StiltsFind findEnum, StiltsFixcols fixcolsEnum, StiltsJoin joinEnum, TwoInputsBean inputBean){
        setInputs(inputBean);
        this.findValue = findEnum;
        this.fixcolsValue = fixcolsEnum;
        this.joinValue = joinEnum;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        super.checkValid();
        if (findValue == null){
             throw new ActivityConfigurationException("Find parameter not set.");
        }
        if (joinValue == null){
             throw new ActivityConfigurationException("Join Paramter not set.");
        }
        if (fixcolsValue == null){
             throw new ActivityConfigurationException("Fixcols parameter not set.");
        }
    }

    /**
     * @return the findValue
     */
    public StiltsFind getFindValue() {
        return findValue;
    }

    /**
     * @param findValue the findValue to set
     */
    public void setFindValue(StiltsFind findValue) {
        this.findValue = findValue;
    }

    /**
     * @return the joinValue
     */
    public StiltsJoin getJoinValue() {
        return joinValue;
    }

    /**
     * @param joinValue the joinValue to set
     */
    public void setJoinValue(StiltsJoin joinValue) {
        this.joinValue = joinValue;
    }

    /**
     * @return the fixcolsValue
     */
    public StiltsFixcols getFixcolsValue() {
        return fixcolsValue;
    }

    /**
     * @param fixcolsValue the fixcolsValue to set
     */
    public void setFixcolsValue(StiltsFixcols fixcolsValue) {
        this.fixcolsValue = fixcolsValue;
    }

    @Override
    public String retrieveStilsCommand() {
        return "tmatch2";
    }

}
