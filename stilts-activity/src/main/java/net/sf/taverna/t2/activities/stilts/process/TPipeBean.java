package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;

/**
 *
 * @author christian
 */
public class TPipeBean extends StiltsProcessBean {
 
    public TPipeBean(){
        
    }
    
    public TPipeBean(SingleInputBean inputBean){
        super(inputBean);
    }

    @Override
    public String retrieveStilsCommand() {
        return "tpipe";
    }
}
