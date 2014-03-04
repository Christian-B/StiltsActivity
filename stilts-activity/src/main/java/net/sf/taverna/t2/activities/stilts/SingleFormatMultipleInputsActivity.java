package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class SingleFormatMultipleInputsActivity<BoundedBean extends SingleFormatMultipleInputsBean> 
        extends MultipleInputsActivity<BoundedBean>{

    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String INPUT_PARAMETER_NAME = "Input";
	
    @Override
    protected void checkBean(BoundedBean configBean)
            throws ActivityConfigurationException {
        super.checkBean(configBean);
        
        if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(
                configBean.getFormatOfInputs())) {
            throw new ActivityConfigurationException(
                    "Output format \"" + configBean.getFormatOfInputs() + 
                    "\" not valid. Must be one of " + 
                    StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST);
        }
    }

    protected void configurePorts() {
        super.configurePorts();
    }
	
    @Override
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        List<String> parameters = super.prepareParameters(inputs, callback, outputFile);
        if (parameters == null){  // super.prepareParameters failed
            return null; //callback.fail(.. aready called
        }
        for (int inputsNumber = 1; inputsNumber <= configBean.getNumberOfInputs(); inputsNumber++){
            String inputPath = getInputFilePath(inputs, callback, inputsNumber);
            if (inputPath == null){
                return null;
            }
            parameters.add("in=" + inputPath);
        }
        parameters.add("ifmt="+ configBean.getFormatOfInputs());
        return parameters;
    }

    @Override
    public BoundedBean getConfiguration() {
        return this.configBean;
    }

}
