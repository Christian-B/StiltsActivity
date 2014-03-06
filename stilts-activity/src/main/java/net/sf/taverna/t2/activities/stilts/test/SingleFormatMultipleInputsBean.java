package net.sf.taverna.t2.activities.stilts.test;

import java.io.Serializable;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Stilts activity configuration bean.
 * 
 */
public class SingleFormatMultipleInputsBean extends MultipleInputsBean 
        implements Serializable {
    private StiltsInputFormat inputsFormatEnum;
    private int numberOfInputs;
    
    public SingleFormatMultipleInputsBean(){}
    
    public SingleFormatMultipleInputsBean(List<StiltsInputType> typesOfInputsEnum, StiltsInputFormat inputsFormatEnum){
        super(typesOfInputsEnum);
        this.inputsFormatEnum = inputsFormatEnum;
        numberOfInputs = typesOfInputsEnum.size();
    }
    
    //@Override
    public String getFormatOfInputs() {
        return inputsFormatEnum.getStiltsName();
    }

    //@Override
    public void setFormatOfInputs(String formatOfInputs) {
        inputsFormatEnum = StiltsInputFormat.byStiltsName(formatOfInputs);
    }

    @Override
    public int retreiveNumberOfInputs() {
        return numberOfInputs;
    }

    /**
     * @return the numberOfInputs
     */
    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    /**
     * @param numberOfInputs the numberOfInputs to set
     */
    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }
      
    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (numberOfInputs < 2){
            throw new ActivityConfigurationException("Number of inputs must be 2 or greater.");
        }
        if (inputsFormatEnum == null){
            throw new ActivityConfigurationException("Inputs format not set.");
        }
        super.checkValid();
    }


}
