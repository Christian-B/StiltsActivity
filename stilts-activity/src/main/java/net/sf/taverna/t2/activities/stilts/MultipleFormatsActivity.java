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
        List<String> formats = configBean.getFormatOfInputs();
        if (formats.size() != configBean.getNumberOfInputs()) {
            throw new ActivityConfigurationException(
                    "List of formats of inputs should be of length: " + 
                    configBean.getNumberOfInputs() +
                    ". Provided list is of size: " + formats.size() );
        }
        List<String> types = configBean.getTypeOfInputs();
        if (types.size() != configBean.getNumberOfInputs()) {
            throw new ActivityConfigurationException(
                    "List of types of inputs should be of length: " + 
                    configBean.getNumberOfInputs() +
                    ". Provided list is of size: " + types.size() );
        }
        for (int i = 0; i< configBean.getNumberOfInputs(); i++){
            if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(
                    formats.get(i))) {
                throw new ActivityConfigurationException(
                        "Output format \"" + formats.get(i) + 
                        "\" not valid. Must be one of " + 
                        StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST);
            }
            if (!StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST.contains(
                    types.get(i))) {
                throw new ActivityConfigurationException(
                        "Output format \"" + types.get(i) + 
                        "\" not valid. Must be one of " + 
                        StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST);
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
	
   private String getInputFilePath(final Map<String, T2Reference> inputs, 
            final AsynchronousActivityCallback callback, int inputsNumber) {
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        String input = getStringParameter(inputs, callback, INPUT_PARAMETER_NAME + inputsNumber, REQUIRED_PARAMETER ); 
        if (input == null) {
            return null;
        }
        String type = configBean.getTypeOfInputs().get(inputsNumber -1);
        return this.getInputFilePath(null, type, input);
    }
    
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        List<String> parameters = super.prepareParameters(inputs, callback, outputFile);
         for (int inputsNumber = 1; inputsNumber <= configBean.getNumberOfInputs(); inputsNumber++){
            String inputPath = getInputFilePath(inputs, callback, inputsNumber);
            if (inputPath == null){
                return null;
            }
            parameters.add("ifmt" + inputsNumber + "="+ configBean.getFormatOfInputs().get(inputsNumber -1));
            parameters.add("in" + inputsNumber + "=" + inputPath);
        }
        return parameters;
    }

    @Override
    public InputsType getConfiguration() {
        return this.configBean;
    }

}
