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

    private InputTypeBean configBean;

    private OutputTypeActivity activity = new TCopyActivity();

    @Before
    public void makeConfigBean() throws Exception {
        configBean = new InputTypeBean();
        configBean.setFormatOfOutput("csv");
        configBean.setFormatOfInput("tst");
        configBean.setTypeOfInput(StiltsConfigurationConstants.FILE_PATH_TYPE);
        configBean.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
    }

    @Test(expected = ActivityConfigurationException.class)
    @Ignore
    public void invalidConfiguration() throws ActivityConfigurationException {
        InputTypeBean invalidBean = new InputTypeBean();
        invalidBean.setFormatOfInput("invalidExample");
        // Should throw ActivityConfigurationException
        activity.configure(invalidBean);
    }

    @Test
    public void executeAsynch() throws Exception {
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(InputTypeActivity.INPUT_PARAMETER_NAME, "C:\\temp\\test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(OutputTypeActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(OutputTypeActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        //assertEquals("Unexpected outputs", OutputTypeActivity.SUCCESS_MESSAGE, result);
    }

    @Test
    public void reConfiguredActivity() throws Exception {
        assertEquals("Unexpected inputs", 0, activity.getInputPorts().size());
        assertEquals("Unexpected outputs", 0, activity.getOutputPorts().size());

        activity.configure(configBean);
        assertEquals("Unexpected inputs", 1, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
        activity.configure(configBean);
        // Should not change on reconfigure
        assertEquals("Unexpected inputs", 1, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
    }

    @Test
    public void configureActivity() throws Exception {
        Set<String> expectedInputs = new HashSet<String>();
        expectedInputs.add(InputTypeActivity.INPUT_PARAMETER_NAME);

        Set<String> expectedOutputs = new HashSet<String>();
        expectedOutputs.add(OutputTypeActivity.RESULT_PARAMETER_NAME);
        if (configBean.isDebugMode()){
            expectedOutputs.add(OutputTypeActivity.ERROR_PARAMETER_NAME);
            expectedOutputs.add(OutputTypeActivity.STILTS_PARAMETER_NAME);
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
