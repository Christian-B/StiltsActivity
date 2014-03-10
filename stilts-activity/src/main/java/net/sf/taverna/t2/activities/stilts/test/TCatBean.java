package net.sf.taverna.t2.activities.stilts.test;

/**
 *
 * @author christian
 */
public class TCatBean extends StiltsProcessBean {
 
    public TCatBean(){
        
    }
    
    public TCatBean(SingleFormatMultipleInputsBean inputBean){
        setInputs(inputBean);
    }
}
