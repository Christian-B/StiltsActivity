package net.sf.taverna.t2.activities.stilts;

/**
 * Stilts activity configuration bean.
 * 
 */
public interface AbstracMatchInterface extends MultipleFormatsInterface {

    /**
     * @return the findValue
     */
    public String getFindValue();

    /**
     * @param findValue the findValue to set
     */
    public void setFindValue(String findValue);

    /**
     * @return the joinValue
     */
    public String getJoinValue();

    /**
     * @param joinValue the joinValue to set
     */
    public void setJoinValue(String joinValue);

    /**
     * @return the fixcolsValue
     */
    public String getFixcolsValue();

    /**
     * @param fixcolsValue the fixcolsValue to set
     */
    public void setFixcolsValue(String fixcolsValue);
    
}
