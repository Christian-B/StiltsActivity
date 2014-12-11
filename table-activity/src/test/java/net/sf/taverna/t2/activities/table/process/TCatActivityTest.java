package net.sf.taverna.t2.activities.table.process;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.input.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.table.process.TCatBean;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;
import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityInputPort;

import org.junit.Before;
import org.junit.Test;

public class TCatActivityTest {

    private TableActivityConfigurationBean configBean;

    private final TableActivity activity = new TableActivity();

    @Before
    public void makeConfigBean() throws Exception {
        List<TableInputType> typesOfInputsEnums = new ArrayList<TableInputType>();
        typesOfInputsEnums.add(TableInputType.FILE);
        typesOfInputsEnums.add(TableInputType.STRING);
        SingleFormatMultipleInputsBean inputBean = new SingleFormatMultipleInputsBean(typesOfInputsEnums, TableInputFormat.CSV);
        TCatBean processBean = new TCatBean(inputBean);
  
        configBean = new TableActivityConfigurationBean(processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);
    }

    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TCat");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.inputTableParameter(1), "src/test/resources/test.csv");
        inputs.put(TableActivity.inputTableParameter(2), 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(TableActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(TableActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("id,name,number"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("Christian"));
        assertTrue("Wrong output : Peter line missing. ", result.contains("Peter"));
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 5 found " + lines.length, lines.length == 5);
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
        SingleFormatMultipleInputsBean inputBean = (SingleFormatMultipleInputsBean)configBean.getProcess().getInputs();
        ArrayList<TableInputType> typesOfInputs = new ArrayList<TableInputType>();
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.STRING);
        inputBean.setTypesOfInputs(typesOfInputs);
        inputBean.setNumberOfInputs(4);
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
        SingleFormatMultipleInputsBean inputBean = (SingleFormatMultipleInputsBean)configBean.getProcess().getInputs();
        ArrayList<TableInputType> typesOfInputs = new ArrayList<TableInputType>();
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.STRING);
        inputBean.setTypesOfInputs(typesOfInputs);
        inputBean.setNumberOfInputs(4);
        activity.configure(configBean);
    }

    @Test
    public void configureActivity() throws Exception {
        Set<String> expectedInputs = new HashSet<String>();
        expectedInputs.add(TableActivity.inputTableParameter(1));
        expectedInputs.add(TableActivity.inputTableParameter(2));

        Set<String> expectedOutputs = new HashSet<String>();
        expectedOutputs.add(TableActivity.OUTPUT_TABLE_PARAMETER_NAME);
        if (configBean.isDebugMode()){
            expectedOutputs.add(TableActivity.ERROR_PARAMETER_NAME);
            expectedOutputs.add(TableActivity.STILTS_PARAMETER_NAME);
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
