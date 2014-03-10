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
public abstract class MultipleFormatsBean extends MultipleInputsBean 
        implements Serializable {
    private List<StiltsInputFormat> formatsOfInputs;

    public MultipleFormatsBean(){}
    
    public MultipleFormatsBean(List<StiltsInputType> typesOfInputsEnum, List<StiltsInputFormat> formatsOfInputEnums){
        super(typesOfInputsEnum);
        this.formatsOfInputs = formatsOfInputEnums;
    }

    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (retreiveNumberOfInputs() < 2){
            throw new ActivityConfigurationException("Number of inputs must be 2 or greater.");
        }
        if (formatsOfInputs == null){
             throw new ActivityConfigurationException("Inputs formats not set.");
        }
        if (formatsOfInputs.size() != retreiveNumberOfInputs()){
             throw new ActivityConfigurationException("Length of Inputs formats: " + formatsOfInputs.size() 
                    + " does not match number of inputs: " + retreiveNumberOfInputs());
        }   
        super.checkValid();
    }

    /**
     * @return the formatsOfInputs
     */
    public List<StiltsInputFormat> getFormatsOfInputs() {
        return formatsOfInputs;
    }

    /**
     * @param formatsOfInputs the formatsOfInputs to set
     */
    public void setFormatsOfInputs(List<StiltsInputFormat> formatsOfInputs) {
        this.formatsOfInputs = formatsOfInputs;
    }

 }
