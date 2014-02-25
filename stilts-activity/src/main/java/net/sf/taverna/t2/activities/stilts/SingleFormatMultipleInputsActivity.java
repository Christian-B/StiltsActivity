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

public class SingleFormatMultipleInputsActivity<InputsType extends SingleFormatMultipleInputsBean> 
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
        
        if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(
                configBean.getFormatOfInputs())) {
            throw new ActivityConfigurationException(
                    "Output format \"" + configBean.getFormatOfInputs() + 
                    "\" not valid. Must be one of " + 
                    StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST);
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
        parameters.add("ifmt="+ configBean.getFormatOfInputs());
        return parameters;
    }

    @Override
    public InputsType getConfiguration() {
        return this.configBean;
    }

}
