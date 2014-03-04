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

public class MultipleInputsActivity<BoundedBean extends MultipleInputsBean> 
        extends AbstractStilsActivity<BoundedBean>{

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
        
        if (configBean.getNumberOfInputs() < 2) {
            throw new ActivityConfigurationException(
                    "Number of inputs should be at least two. Found: " + 
                    configBean.getNumberOfInputs());
        }
        List<String> types = configBean.getTypesOfInputs();
        if (types.size() != configBean.getNumberOfInputs()) {
            throw new ActivityConfigurationException(
                    "List of types of inputs should be of length: " + 
                    configBean.getNumberOfInputs() +
                    ". Provided list is of size: " + types.size() );
        }
        for (int i = 0; i< configBean.getNumberOfInputs(); i++){
            if (!StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST.contains(
                    types.get(i))) {
                throw new ActivityConfigurationException(
                        "Output format \"" + types.get(i) + 
                        "\" not valid. Must be one of " + 
                        StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST);
            }
        }
    }

    protected void configurePorts() {
        super.configurePorts();
        for (int inputsNumber = 1; inputsNumber <= configBean.getNumberOfInputs(); inputsNumber++){
            addInput(INPUT_PARAMETER_NAME + inputsNumber, 0, true, null, String.class);
        }
    }
	
   protected String getInputFilePath(final Map<String, T2Reference> inputs, 
            final AsynchronousActivityCallback callback, int inputsNumber) {
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        String input = getStringParameter(inputs, callback, INPUT_PARAMETER_NAME + inputsNumber, REQUIRED_PARAMETER ); 
        if (input == null) {
            return null;
        }
        String type = configBean.getTypesOfInputs().get(inputsNumber -1);
        return this.getInputFilePath(callback, type, input);
    }
    
    /* Parameters must be dded by super classes as
    TCat uses in, in 
    TCatN uses in1, in2 and nin
    @Override
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        List<String> parameters = super.prepareParameters(inputs, callback, outputFile);
        if (parameters == null){  // super.prepareParameters failed
            return null; //callback.fail(.. aready called
        }
        return parameters;
    }*/

    @Override
    public BoundedBean getConfiguration() {
        return this.configBean;
    }

}
