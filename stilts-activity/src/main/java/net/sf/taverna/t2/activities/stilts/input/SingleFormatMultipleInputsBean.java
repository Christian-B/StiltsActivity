package net.sf.taverna.t2.activities.stilts.input;

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
    private StiltsInputFormat formatOfInputs;
    private int numberOfInputs;
    
    public SingleFormatMultipleInputsBean(){}
    
    public SingleFormatMultipleInputsBean(List<StiltsInputType> typesOfInputsEnum, StiltsInputFormat inputsFormatEnum){
        super(typesOfInputsEnum);
        this.formatOfInputs = inputsFormatEnum;
        numberOfInputs = typesOfInputsEnum.size();
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
        if (formatOfInputs == null){
            throw new ActivityConfigurationException("Inputs format not set.");
        }
        super.checkValid();
    }

    /**
     * @return the formatOfInputs
     */
    public StiltsInputFormat getFormatOfInputs() {
        return formatOfInputs;
    }

    /**
     * @param formatOfInputs the formatOfInputs to set
     */
    public void setFormatOfInputs(StiltsInputFormat formatOfInputs) {
        this.formatOfInputs = formatOfInputs;
    }

    /**
     * Resests the number of inputs, adjusting the length of the type and info arrays.
     * 
     * @throws NullPOinterException if called on a bean before all values have bee set at least once.
     * 
     * @param numberOfInputs 
     */
    public void resetNumberOfInputs(int numberOfInputs){
        this.numberOfInputs = numberOfInputs;
        super.resetNumberOfInputs(numberOfInputs);
    }     

}
