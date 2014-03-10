package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.SingleFormatMultipleInputsBean;

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
