package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.MultipleFormatsBean;

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

    @Override
    public String retrieveStilsCommand() {
        return "tjoin";
    }
}
