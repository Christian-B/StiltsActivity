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
public class MultipleFormatsBean extends MultipleInputsBean 
        implements Serializable {
    private List<StiltsInputFormat> formatsOfInputEnums;
    private int numberOfInputs;

    public MultipleFormatsBean(){}
    
    public MultipleFormatsBean(List<StiltsInputType> typesOfInputsEnum, List<StiltsInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum);
        this.formatsOfInputEnums = formatsOfInputEnums;
        this.numberOfInputs = formatsOfInputEnums.size();
    }

    public final List<String> getFormatsOfInputs() {
        ArrayList<String> formatsOfInputs = new ArrayList<String>();
        for (StiltsInputFormat type:formatsOfInputEnums){
            formatsOfInputs.add(type.getStiltsName());
        }
        return formatsOfInputs;
    }

    public final void setFormatsOfInputs(List<String> formatsOfInput) {
        formatsOfInputEnums = new ArrayList<StiltsInputFormat>();
        for (String format:formatsOfInput){
            formatsOfInputEnums.add(StiltsInputFormat.byStiltsName(format));
        }
    }
    
    List<StiltsInputFormat> retreiveStiltsInputsFormat() {
        return formatsOfInputEnums;
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
        if (formatsOfInputEnums == null){
             throw new ActivityConfigurationException("Inputs formats not set.");
        }
        if (formatsOfInputEnums.size() != retreiveNumberOfInputs()){
             throw new ActivityConfigurationException("Length of Inputs formats: " + formatsOfInputEnums.size() 
                    + " does not match number of inputs: " + getNumberOfInputs());
        }   
        super.checkValid();
    }
    
    @Override
    public final int retreiveNumberOfInputs() {
        return getNumberOfInputs();
    }

      
}
