package net.sf.taverna.t2.activities.stilts.test;

import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class TMatch2Bean extends StilsProcessBean {
 
    private StiltsFind findEnum;
    private StiltsJoin joinEnum;
    private StiltsFixcols fixcolsEnum;

    public TMatch2Bean(){
        
    }
    
    public TMatch2Bean(StiltsFind findEnum, StiltsFixcols fixcolsEnum, StiltsJoin joinEnum, TwoInputsBean inputBean){
        setInputs(inputBean);
        this.findEnum = findEnum;
        this.fixcolsEnum = fixcolsEnum;
        this.joinEnum = joinEnum;
    }
    
    /**
     * @return the findValue
     */
    public String getFindValue() {
        return findEnum.getStiltsName();
    }

    /**
     * @param findValue the findValue to set
     */
    public void setFindValue(String findValue) {
        this.findEnum = StiltsFind.byStiltsName(findValue);
    }

    /**
     * @return the joinValue
     */
    public String getJoinValue() {
        return joinEnum.getStiltsName();
    }

    /**
     * @param joinValue the joinValue to set
     */
    public void setJoinValue(String joinValue) {
        joinEnum = StiltsJoin.byStiltsName(joinValue);
    }

    /**
     * @return the fixcolsValue
     */
    public String getFixcolsValue() {
        return fixcolsEnum.getStiltsName();
    }

    /**
     * @param fixcolsValue the fixcolsValue to set
     */
    public void setFixcolsValue(String fixcolsValue) {
        fixcolsEnum = StiltsFixcols.byStiltsName(fixcolsValue);
    }
    
    public void checkValid() throws ActivityConfigurationException{
        super.checkValid();
        if (findEnum == null){
             throw new ActivityConfigurationException("Find parameter not set.");
        }
        if (joinEnum  == null){
             throw new ActivityConfigurationException("Join Paramter not set.");
        }
        if (fixcolsEnum == null){
             throw new ActivityConfigurationException("Fixcols parameter not set.");
        }
    }

}
