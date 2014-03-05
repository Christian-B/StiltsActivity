package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class AbstractMatchBean extends MultipleFormatsBean 
        implements AbstracMatchInterface, Serializable {
 
    private String findValue;
    private String joinValue;
    private String fixcolsValue;

    public AbstractMatchBean(){
        this.setNumberOfInputs(2);
        this.setFixedNumberOfInputs(true);        
    }
    
    public AbstractMatchBean(AbstracMatchInterface other){
        super(other);
        this.setNumberOfInputs(2);
        this.setFixedNumberOfInputs(true);
        this.findValue = other.getFindValue();
        this.fixcolsValue = other.getFixcolsValue();
        this.joinValue= other.getJoinValue();
    }

    /**
     * @return the findValue
     */
    @Override
    public String getFindValue() {
        return findValue;
    }

    /**
     * @param findValue the findValue to set
     */
    @Override
    public void setFindValue(String findValue) {
        this.findValue = findValue;
    }

    /**
     * @return the joinValue
     */
    @Override
    public String getJoinValue() {
        return joinValue;
    }

    /**
     * @param joinValue the joinValue to set
     */
    @Override
    public void setJoinValue(String joinValue) {
        this.joinValue = joinValue;
    }

    /**
     * @return the fixcolsValue
     */
    @Override
    public String getFixcolsValue() {
        return fixcolsValue;
    }

    /**
     * @param fixcolsValue the fixcolsValue to set
     */
    @Override
    public void setFixcolsValue(String fixcolsValue) {
        this.fixcolsValue = fixcolsValue;
    }
}
