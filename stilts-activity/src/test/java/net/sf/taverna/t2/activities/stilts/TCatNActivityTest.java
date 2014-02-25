package net.sf.taverna.t2.activities.stilts;

import java.util.ArrayList;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityInputPort;
import org.junit.After;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TCatNActivityTest {

    private InputsTypeBean configBean;

    private final TCatNActivity activity = new TCatNActivity();

    @Before
    public void makeConfigBean() throws Exception {
        configBean = new InputsTypeBean();
        configBean.setNumberOfInputs(2);
        configBean.setFormatOfOutput("ascii");
        ArrayList<String> formatOfInputs = new ArrayList<String>();
        formatOfInputs.add("tst");
        formatOfInputs.add("csv");
        configBean.setFormatOfInputs(formatOfInputs);
        ArrayList<String> typeOfInputs = new ArrayList<String>();
        typeOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typeOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setTypeOfInputs(typeOfInputs);
        configBean.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
    }

    @After
    public void reportDone() throws Exception {
        System.out.println("test done");
        System.err.println("Error stream ok");
    }
    
    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TCat");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(InputsTypeActivity.INPUT_PARAMETER_NAME+1, "C:\\temp\\test.tst");
        inputs.put(InputsTypeActivity.INPUT_PARAMETER_NAME+2, 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        //assertEquals("Unexpected outputs", AbstractStilsActivity.SUCCESS_MESSAGE, result);
    }

    @Test
    public void reConfiguredActivity() throws Exception {
        assertEquals("Unexpected inputs", 0, activity.getInputPorts().size());
        assertEquals("Unexpected outputs", 0, activity.getOutputPorts().size());

        activity.configure(configBean);
        assertEquals("Unexpected inputs", 2, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
        ArrayList<String> formatOfInputs = new ArrayList<String>();
        formatOfInputs.add("tst");
        formatOfInputs.add("csv");
        formatOfInputs.add("csv");
        formatOfInputs.add("csv");
        configBean.setFormatOfInputs(formatOfInputs);
        ArrayList<String> typeOfInputs = new ArrayList<String>();
        typeOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typeOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typeOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typeOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setTypeOfInputs(typeOfInputs);
        configBean.setNumberOfInputs(4);
        activity.configure(configBean);
        // Should not change on reconfigure
        assertEquals("Unexpected inputs", 4, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
    }

    @Test(expected = ActivityConfigurationException.class)
    public void reConfiguredActivityLengthError() throws Exception {
        ArrayList<String> formatOfInputs = new ArrayList<String>();
        formatOfInputs.add("tst");
        formatOfInputs.add("csv");
        formatOfInputs.add("csv");
        formatOfInputs.add("csv");
        configBean.setFormatOfInputs(formatOfInputs);
        ArrayList<String> typeOfInputs = new ArrayList<String>();
        typeOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typeOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typeOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setTypeOfInputs(typeOfInputs);
        configBean.setNumberOfInputs(4);
        activity.configure(configBean);
        // Should not change on reconfigure
        assertEquals("Unexpected inputs", 4, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
    }

    @Test(expected = ActivityConfigurationException.class)
    public void reConfiguredActivityTypeError() throws Exception {
        ArrayList<String> formatOfInputs = new ArrayList<String>();
        formatOfInputs.add("tst");
        formatOfInputs.add("opps");
        configBean.setFormatOfInputs(formatOfInputs);
        ArrayList<String> typeOfInputs = new ArrayList<String>();
        typeOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typeOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setTypeOfInputs(typeOfInputs);
        configBean.setNumberOfInputs(2);
        activity.configure(configBean);
        // Should not change on reconfigure
        assertEquals("Unexpected inputs", 4, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
    }


    @Test
    public void configureActivity() throws Exception {
        Set<String> expectedInputs = new HashSet<String>();
        expectedInputs.add(InputsTypeActivity.INPUT_PARAMETER_NAME+1);
        expectedInputs.add(InputsTypeActivity.INPUT_PARAMETER_NAME+2);

        Set<String> expectedOutputs = new HashSet<String>();
        expectedOutputs.add(AbstractStilsActivity.RESULT_PARAMETER_NAME);
        if (configBean.isDebugMode()){
            expectedOutputs.add(AbstractStilsActivity.ERROR_PARAMETER_NAME);
            expectedOutputs.add(AbstractStilsActivity.STILTS_PARAMETER_NAME);
        } 
        activity.configure(configBean);

        Set<ActivityInputPort> inputPorts = activity.getInputPorts();
        assertEquals(expectedInputs.size(), inputPorts.size());
        for (ActivityInputPort inputPort : inputPorts) {
            assertTrue("Wrong input : " + inputPort.getName(), expectedInputs.remove(inputPort.getName()));
        }

        Set<OutputPort> outputPorts = activity.getOutputPorts();
        assertEquals(expectedOutputs.size(), outputPorts.size());
        for (OutputPort outputPort : outputPorts) {
            assertTrue("Wrong output : " + outputPort.getName(),
            expectedOutputs.remove(outputPort.getName()));
        }
    }
}
