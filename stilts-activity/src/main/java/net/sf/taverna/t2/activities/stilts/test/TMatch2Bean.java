package net.sf.taverna.t2.activities.stilts.test;

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

    public TMatch2Bean(){
        
    }
    
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

}
