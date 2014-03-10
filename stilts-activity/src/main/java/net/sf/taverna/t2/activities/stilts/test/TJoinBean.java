package net.sf.taverna.t2.activities.stilts.test;

/**
 *
 * @author christian
 */
public class TJoinBean extends StiltsProcessBean {
 
    public TJoinBean(){
        
    }
    
    public TJoinBean(MultipleFormatsBean inputBean){
        setInputs(inputBean);
    }
}
