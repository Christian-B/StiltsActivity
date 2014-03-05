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

public class SingleInputActivity<BoundedBean extends SingleInputBean> extends AbstractStilsActivity<SingleInputBean>
		implements AsynchronousActivity<SingleInputBean>,  Activity<SingleInputBean>{

    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String INPUT_PARAMETER_NAME = "Input";
	
    @Override
    protected void checkBean(SingleInputBean configBean)
            throws ActivityConfigurationException {
        super.checkBean(configBean);
        if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(
                configBean.getFormatOfInput())) {
            throw new ActivityConfigurationException(
                    "Output format \"" + configBean.getFormatOfInput() + 
                    "\" not valid. Must be one of " + 
                    StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST);
        }
        if (!StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST.contains(
                configBean.getTypeOfInput())) {
            throw new ActivityConfigurationException(
                    "Output format \"" + configBean.getTypeOfInput() + 
                    "\" not valid. Must be one of " + 
                    StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST);
        }
    }

    protected void configurePorts() {
        super.configurePorts();
        // Hard coded inputPath port, expecting a single String
        addInput(INPUT_PARAMETER_NAME, 0, true, null, String.class);
    }
	
    private String getInputFilePath(final Map<String, T2Reference> inputs, 
            final AsynchronousActivityCallback callback) {
        String input = this.getStringParameter(inputs, callback, INPUT_PARAMETER_NAME, REQUIRED_PARAMETER ); 
        String type = configBean.getTypeOfInput();
        return this.getInputFilePath(callback, type, input);
    }
        
    @Override
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        List<String> parameters = super.prepareParameters(inputs, callback, outputFile);
        if (parameters == null){  // super.prepareParameters failed
            return null; //callback.fail(.. aready called
        }
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        String inputPath = getInputFilePath(inputs, callback);
        if (inputPath == null){
            return null;
        }
        parameters.add("ifmt="+ configBean.getFormatOfInput());
        parameters.add("in=" + inputPath);
        
        return parameters;
    }

 }
