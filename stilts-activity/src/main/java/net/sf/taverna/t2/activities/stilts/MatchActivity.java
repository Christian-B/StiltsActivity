package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static net.sf.taverna.t2.activities.stilts.SingleInputActivity.INPUT_PARAMETER_NAME;

import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class MatchActivity<BoundedBean extends MatchBean> 
        extends MultipleFormatsActivity<BoundedBean>{

    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String MACTH_COLUMNS_TABLE1_PARAMETER_NAME = "Table 1 Match Column";
    static final String MACTH_COLUMNS_TABLE2_PARAMETER_NAME = "Table 2 Match Column";

    public static final String FIND_PARAMETER_NAME = "Find Option";
    public static final String JOIN_PARAMETER_NAME = "Join Option";
    public static final String FIXCOLS_PARAMETER_NAME = "Fixcols Option";
	    
    @Override
    protected void checkBean(BoundedBean configBean)
            throws ActivityConfigurationException {
        super.checkBean(configBean);
        
        if (!configBean.isFixedNumberOfInputs()) {
                throw new ActivityConfigurationException(
                        "This activity requires that Number of Columns is Fixed.");
        }
        if (configBean.getNumberOfInputs() != 2) {
                throw new ActivityConfigurationException(
                        "This activity requires exactly 2 Columns!");
        }
        if (!StiltsConfigurationConstants.VALID_FIND_VALUES_LIST.contains(
                configBean.getFindValue())) {
                throw new ActivityConfigurationException(
                        FIND_PARAMETER_NAME+ " \"" + configBean.getFindValue() + 
                        "\" not valid. Must be one of " + 
                        StiltsConfigurationConstants.VALID_FIND_VALUES_LIST);
        }
        if (!StiltsConfigurationConstants.VALID_JOIN_VALUES_LIST.contains(
                configBean.getJoinValue())) {
                throw new ActivityConfigurationException(
                        JOIN_PARAMETER_NAME + " \"" + configBean.getJoinValue() + 
                        "\" not valid. Must be one of " + 
                        StiltsConfigurationConstants.VALID_JOIN_VALUES_LIST);
        }
        if (!StiltsConfigurationConstants.VALID_FIXCOLS_VALUES_LIST.contains(
                configBean.getFixcolsValue())) {
                throw new ActivityConfigurationException(
                        FIXCOLS_PARAMETER_NAME + " \"" + configBean.getFixcolsValue() + 
                        "\" not valid. Must be one of " + 
                        StiltsConfigurationConstants.VALID_FIXCOLS_VALUES_LIST);
        }
}

    protected void configurePorts() {
        super.configurePorts();
        // Hard coded inputPath port, expecting a single String
        addInput(MACTH_COLUMNS_TABLE1_PARAMETER_NAME, 0, true, null, String.class);
        addInput(MACTH_COLUMNS_TABLE2_PARAMETER_NAME, 0, true, null, String.class);
    }
	
    @Override
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add("tmatch2");
        List<String> newParameters = super.prepareParameters(inputs, callback, outputFile);
        if (newParameters == null){  // super.prepareParameters failed
            return null; //callback.fail(.. aready called
        }
        parameters.addAll(newParameters);
        parameters.add("matcher=exact");
        String columns1 = getStringParameter(inputs, callback, MACTH_COLUMNS_TABLE1_PARAMETER_NAME, REQUIRED_PARAMETER);
        if (columns1 == null){ //getFailed
            return null; //callback.fail(.. aready called            
        }
        parameters.add("values1=" + columns1);
        String columns2 = getStringParameter(inputs, callback, MACTH_COLUMNS_TABLE2_PARAMETER_NAME, REQUIRED_PARAMETER);
        if (columns2 == null){ //getFailed
            return null; //callback.fail(.. aready called            
        }
        parameters.add("values2=" + columns2);
        return parameters;
    }

    @Override
    public BoundedBean getConfiguration() {
        return this.configBean;
    }

}
