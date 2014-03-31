package net.sf.taverna.t2.activities.stilts.input;

import java.io.Serializable;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;

/**
 * Bean that defines exactly two input tables which can be of different types and formats.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
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

    /*
     * Should never be called, but just incase ignores any call with the value 2.
     * 
     * @param numberOfInputs Must be 2
     * @throws UnsupportedOperationException if called with anything but 2
     */
    public void resetNumberOfInputs(int numberOfInputs){
        if (numberOfInputs != 2){
            throw new UnsupportedOperationException("Illegal call!");   
        }
    }
      
}
