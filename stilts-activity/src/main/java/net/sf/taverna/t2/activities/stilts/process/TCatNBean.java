package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.input.MultipleFormatsBean;

/**
 *
 * @author christian
 */
public class TCatNBean extends StiltsProcessBean {
 
    public TCatNBean(){
        
    }
    
    public TCatNBean(MultipleFormatsBean inputBean){
        setInputs(inputBean);
    }

    @Override
    public String retrieveStilsCommand() {
        return "tcatn";
    }
}
