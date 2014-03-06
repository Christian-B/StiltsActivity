package net.sf.taverna.t2.activities.stilts.test;

/**
 *
 * @author christian
 */
public class TPipeBean extends StilsProcessBean {
 
    public TPipeBean(){
        
    }
    
    public TPipeBean(SingleInputBean inputBean){
        setInputs(inputBean);
    }
}
