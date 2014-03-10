package net.sf.taverna.t2.activities.stilts.test;

/**
 *
 * @author christian
 */
public class TPipeBean extends StiltsProcessBean {
 
    public TPipeBean(){
        
    }
    
    public TPipeBean(SingleInputBean inputBean){
        setInputs(inputBean);
    }
}
