package net.sf.taverna.t2.activities.stilts.input;

import java.io.Serializable;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;

/**
 * Stilts activity configuration bean.
 * 
 */
public class TwoInputsBean extends MultipleFormatsBean 
        implements Serializable {
 
    public TwoInputsBean(){}
    
    public TwoInputsBean(List<StiltsInputType> typesOfInputsEnum, List<StiltsInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum, formatsOfInputEnums );
    }
     
    @Override
    public final int retreiveNumberOfInputs() {
        return 2;
    }

      
}
