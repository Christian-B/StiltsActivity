package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import net.sf.taverna.t2.activities.stilts.utils.MyUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static net.sf.taverna.t2.activities.stilts.InputTypeActivity.INPUT_PARAMETER_NAME;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class InputsTypeActivity<InputsType extends InputsTypeBean> extends OutputTypeActivity<InputsTypeBean>
		implements AsynchronousActivity<InputsTypeBean>,  Activity<InputsTypeBean>{

    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String INPUT_PARAMETER_NAME = "Input";
	
    @Override
    public void configure(InputsTypeBean configBean)
            throws ActivityConfigurationException {

        // Store for getConfiguration(), but you could also make
        // getConfiguration() return a new bean from other sources
        this.configBean = configBean;

        // REQUIRED: (Re)create inputPath/output ports depending on configuration
        configurePorts();
    }

    protected void configurePorts() {
        super.configurePorts();
        for (int inputsNumber = 1; inputsNumber <= configBean.getNumberOfInputs(); inputsNumber++){
            addInput(INPUT_PARAMETER_NAME + inputsNumber, 0, true, null, String.class);
        }
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
    public InputsTypeBean getConfiguration() {
        return this.configBean;
    }

}
