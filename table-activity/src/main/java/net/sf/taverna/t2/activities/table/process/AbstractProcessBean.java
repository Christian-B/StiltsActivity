package net.sf.taverna.t2.activities.table.process;

import java.util.ArrayList;
import java.util.List;

import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.input.AbstractInputsBean;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Abstract base class for all ProcessBeans
 * <p>
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class AbstractProcessBean {
    private AbstractInputsBean inputs;

    /**
     * Serialization constructor
     */
    AbstractProcessBean(){}
        
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param inputs 
     */
    AbstractProcessBean(AbstractInputsBean inputs){
        this.inputs = inputs;
    }
    
    /**
     * @return the inputs
     */
    public AbstractInputsBean getInputs() {
        return inputs;
    }

    /**
     * @param inputs the inputs to set
     */
    public void setInputs(AbstractInputsBean inputs) {
        this.inputs = inputs;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        inputs.checkValid();
    }

    /**
     * Obtains the Stilts name for the Process.
     * 
     * Intentional not a get method to avoid confusing the any bean parser.
     * 
     * Most of the parameter setting is done by TableActivity.createProcessParameters 
     * so that it can mix information in the bean with information provided in the workflow.
     * @return command as expected by Stilts
     */
    public abstract String retrieveStilsCommand();

    public abstract String title();

    public List<TableParameterConfiguration> configurations(){
        return new ArrayList<TableParameterConfiguration>();
    }

 }
