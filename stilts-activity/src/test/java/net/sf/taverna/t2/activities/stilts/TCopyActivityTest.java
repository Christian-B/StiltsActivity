package net.sf.taverna.t2.activities.stilts;

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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TCopyActivityTest {

    private SingleInputBean configBean;

    private final AbstractStilsActivity activity = new TCopyActivity();

    private static final String CSV_STRING = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator();

    @Before
    public void makeConfigBean() throws Exception {
        configBean = new SingleInputBean();
        configBean.setDebugMode(false);
        configBean.setFormatOfOutput("csv");
        configBean.setFormatOfInput("tst");
        configBean.setTypeOfInput(StiltsConfigurationConstants.FILE_PATH_TYPE);
        configBean.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
    }

    @Test(expected = ActivityConfigurationException.class)
    public void invalidConfiguration() throws ActivityConfigurationException {
        SingleInputBean invalidBean = new SingleInputBean();
        invalidBean.setFormatOfOutput("invalidExample");
        // Should throw ActivityConfigurationException
        activity.configure(invalidBean);
    }

    @Test
    public void executeAsynch() throws Exception {
        System.out.println("executeAsynch");
        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(SingleInputActivity.INPUT_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", CSV_STRING, result);
    }

    @Test
    public void executeAsynchWithDebug() throws Exception {
        System.out.println("executeAsynchWithDebug");
        configBean.setDebugMode(true);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(SingleInputActivity.INPUT_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        expectedOutputTypes.put(AbstractStilsActivity.ERROR_PARAMETER_NAME, String.class);
        expectedOutputTypes.put(AbstractStilsActivity.STILTS_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 3, outputs.size());
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", CSV_STRING, result);
        String error = outputs.get(AbstractStilsActivity.ERROR_PARAMETER_NAME).toString();
        assertEquals("Unexpected Error", "", error);
    }

    @Test
    public void executeAsynchError() throws Exception {
        System.out.println("executeAsynchError");
        configBean.setDebugMode(true);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(SingleInputActivity.INPUT_PARAMETER_NAME, "src/test/resources/test.csv");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", AbstractStilsActivity.FAILED_MESSAGE, result);
    }

    @Test
    public void reConfiguredActivity() throws Exception {
        assertEquals("Unexpected inputs", 0, activity.getInputPorts().size());
        assertEquals("Unexpected outputs", 0, activity.getOutputPorts().size());

        configBean.setDebugMode(false);
        activity.configure(configBean);
        assertEquals("Unexpected inputs", 1, activity.getInputPorts().size());
        assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());

        configBean.setDebugMode(true);
        activity.configure(configBean);
        // Should not change on reconfigure
        assertEquals("Unexpected inputs", 1, activity.getInputPorts().size());
        assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
    }

    @Test
    public void configureActivity() throws Exception {
        Set<String> expectedInputs = new HashSet<String>();
        expectedInputs.add(SingleInputActivity.INPUT_PARAMETER_NAME);

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
