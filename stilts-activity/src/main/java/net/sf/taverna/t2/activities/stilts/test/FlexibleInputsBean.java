package net.sf.taverna.t2.activities.stilts.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Stilts activity configuration bean.
 * 
 */
public class FlexibleInputsBean extends MultipleFormatsBean 
        implements Serializable {
    private int numberOfInputs;

    public FlexibleInputsBean(){}
    
    public FlexibleInputsBean(List<StiltsInputType> typesOfInputsEnum, List<StiltsInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum, formatsOfInputEnums );
        this.numberOfInputs = formatsOfInputEnums.size();
    }

    /**
     * @return the numberOfInputs
     */
    public final int getNumberOfInputs() {
        return numberOfInputs;
    }

    /**
     * @param numberOfInputs the numberOfInputs to set
     */
    public final void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }
      
    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (numberOfInputs < 2){
            throw new ActivityConfigurationException("Number of inputs must be 2 or greater.");
        }
        super.checkValid();
    }
    
    @Override
    public final int retreiveNumberOfInputs() {
        return getNumberOfInputs();
    }

      
}
