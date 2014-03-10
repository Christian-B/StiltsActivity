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
public abstract class MultipleFormatsBean1 extends MultipleInputsBean 
        implements Serializable {
    private List<StiltsInputFormat> formatsOfInputEnums;

    public MultipleFormatsBean1(){}
    
    public MultipleFormatsBean1(List<StiltsInputType> typesOfInputsEnum, List<StiltsInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum);
        this.formatsOfInputEnums = formatsOfInputEnums;
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
    
    public void resetFormatsOfInputs(List<StiltsInputFormat> formatsOfInputs) {
        formatsOfInputEnums = formatsOfInputs;
    }


    public List<StiltsInputFormat> retreiveStiltsInputsFormat() {
        return formatsOfInputEnums;
    }

    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (retreiveNumberOfInputs() < 2){
            throw new ActivityConfigurationException("Number of inputs must be 2 or greater.");
        }
        if (formatsOfInputEnums == null){
             throw new ActivityConfigurationException("Inputs formats not set.");
        }
        if (formatsOfInputEnums.size() != retreiveNumberOfInputs()){
             throw new ActivityConfigurationException("Length of Inputs formats: " + formatsOfInputEnums.size() 
                    + " does not match number of inputs: " + retreiveNumberOfInputs());
        }   
        super.checkValid();
    }
      
}
