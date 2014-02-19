package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import net.sf.taverna.t2.activities.stilts.utils.MyUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class InputTypeActivity<InputType extends InputTypeBean> extends OutputTypeActivity<InputTypeBean>
		implements AsynchronousActivity<InputTypeBean>,  Activity<InputTypeBean>{

    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String INPUT_PARAMETER_NAME = "Input";
	
    @Override
    public void configure(InputTypeBean configBean)
            throws ActivityConfigurationException {

        // Store for getConfiguration(), but you could also make
        // getConfiguration() return a new bean from other sources
        this.configBean = configBean;

        // REQUIRED: (Re)create inputPath/output ports depending on configuration
        configurePorts();
    }

    protected void configurePorts() {
        super.configurePorts();
        // Hard coded inputPath port, expecting a single String
        addInput(INPUT_PARAMETER_NAME, 0, true, null, String.class);
    }
	
    private String getInputFilePath(final Map<String, T2Reference> inputs, 
            InvocationContext context, ReferenceService referenceService) throws IOException{
       String input = (String) referenceService.renderIdentifier
                        (inputs.get(INPUT_PARAMETER_NAME), String.class, context); 
       String type = configBean.getTypeOfInput();
       int length = type.length();
       if (type.equals(StiltsConfigurationConstants.FILE_PATH_TYPE)){
            return input;
       }
       return MyUtils.writeStringAsTmpFile(input).getAbsolutePath();
    }
    
    protected boolean missingParameter(final Map<String, T2Reference> inputs,  final AsynchronousActivityCallback callback) {
        if (super.missingParameter(callback)){
            return true;
        }
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        String input = (String) referenceService.renderIdentifier
                        (inputs.get(INPUT_PARAMETER_NAME), String.class, context); 
        if (input == null){
            callback.fail("Error missing " + INPUT_PARAMETER_NAME + " parameter.");
            return true;
        }
        if (input.isEmpty()){
            callback.fail("Error empty " + INPUT_PARAMETER_NAME + " parameter.");
            return true;
        }
        return false;
    }
    
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        List<String> parameters = super.prepareParameters(inputs, callback, outputFile);
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        String inputPath;
        try {
            inputPath = getInputFilePath(inputs, context, referenceService);
        } catch (IOException ex) {
            callback.fail("Error preparing input", ex);
            return null;
        }
        parameters.add("ifmt="+ configBean.getFormatOfInput());
        parameters.add("in=" + inputPath);
        
        return parameters;
    }

    @Override
    public InputTypeBean getConfiguration() {
        return this.configBean;
    }

}
