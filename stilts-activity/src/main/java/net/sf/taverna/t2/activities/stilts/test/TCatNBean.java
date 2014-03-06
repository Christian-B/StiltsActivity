package net.sf.taverna.t2.activities.stilts.test;

/**
 *
 * @author christian
 */
public class TCatNBean extends StilsProcessBean {
 
    public TCatNBean(){
        
    }
    
    public TCatNBean(MultipleFormatsBean inputBean){
        setInputs(inputBean);
    }
}
