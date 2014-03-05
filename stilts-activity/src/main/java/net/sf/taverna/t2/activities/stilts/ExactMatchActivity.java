package net.sf.taverna.t2.activities.stilts;

import java.io.File;
import java.util.List;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.utils.MyUtils;

import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class ExactMatchActivity extends AbstractMatchActivity<ExactMatchBean>{

    @Override
    protected void checkBean(ExactMatchBean configBean)
            throws ActivityConfigurationException {
        super.checkBean(configBean);
        if (configBean.getNumbertOfColumnsToMatch() < 1) {
                throw new ActivityConfigurationException(
                        "You must match on at least one column!");
        }
    }
    
    public static String getMatchColumnName(int table, int matchPosition){     
        return "Name of " + MyUtils.ordinal(matchPosition) + " column to match in " + MyUtils.ordinal(table) + " Table "; 
    }
    
    protected void configurePorts() {
        super.configurePorts();
        for (int table = 1; table <= 2; table++){
            for (int column = 1; column <= configBean.getNumbertOfColumnsToMatch(); column++){
                addInput(getMatchColumnName(table, column), 0, true, null, String.class);
            }
        }
    }
	
    @Override
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        List<String> parameters = super.prepareParameters(inputs, callback, outputFile);
        if (parameters == null){  // super.prepareParameters failed
            return null; //callback.fail(.. aready called
        }
        String matcher = "matcher=exact";
        //Add an extra +exact for each eaxtra column (more than 1)
        for (int column = 1; column <  configBean.getNumbertOfColumnsToMatch(); column++){
            matcher+= "+exact";
        }
        parameters.add(matcher);
        for (int table = 1; table <= 2; table++){
            String values = "values" + table + "=";
            for (int column = 1; column <= configBean.getNumbertOfColumnsToMatch(); column++){
                String columnName = getStringParameter(inputs, callback, getMatchColumnName(table, column), REQUIRED_PARAMETER);
                if (columnName == null){ //getFailed
                    return null; //callback.fail(.. aready called            
                }
                values+=columnName + " ";
            }
            parameters.add(values);
        }
        return parameters;
    }

}
