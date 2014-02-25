package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class MultipleFormatsActivity<InputsType extends MultipleFormatsBean> 
        extends MultipleInputsTypeActivity<InputsType>{

    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String INPUT_PARAMETER_NAME = "Input";
	
    @Override
    public void configure(InputsType configBean)
            throws ActivityConfigurationException {
        super.configure(configBean);
        
        if (configBean.getNumberOfInputs() < 2) {
            throw new ActivityConfigurationException(
                    "Number of inputs should be at least two. Found: " + 
                    configBean.getNumberOfInputs());
        }
        List<String> formats = configBean.getFormatsOfInputs();
        if (formats.size() != configBean.getNumberOfInputs()) {
            throw new ActivityConfigurationException(
                    "List of formats of inputs should be of length: " + 
                    configBean.getNumberOfInputs() +
                    ". Provided list is of size: " + formats.size() );
        }
        for (int i = 0; i< configBean.getNumberOfInputs(); i++){
            if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(
                    formats.get(i))) {
                throw new ActivityConfigurationException(
                        "Output format \"" + formats.get(i) + 
                        "\" not valid. Must be one of " + 
                        StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST);
            }
        }
        // Store for getConfiguration(), but you could also make
        // getConfiguration() return a new bean from other sources
        this.configBean = configBean;

        // REQUIRED: (Re)create inputPath/output ports depending on configuration
        configurePorts();
    }

    protected void configurePorts() {
        super.configurePorts();
    }
	
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        List<String> parameters = super.prepareParameters(inputs, callback, outputFile);
         for (int inputsNumber = 1; inputsNumber <= configBean.getNumberOfInputs(); inputsNumber++){
            parameters.add("ifmt" + inputsNumber + "="+ configBean.getFormatsOfInputs().get(inputsNumber -1));
        }
        return parameters;
    }

    @Override
    public InputsType getConfiguration() {
        return this.configBean;
    }

}
