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
import net.sf.taverna.t2.activities.table.input.FlexibleInputsBean;
import net.sf.taverna.t2.activities.table.input.MultipleFormatsBean;
import net.sf.taverna.t2.activities.table.process.TCatNBean;
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

public class TCatNActivityTest {

    private TableActivityConfigurationBean configBean;

    private final TableActivity activity = new TableActivity();

    @Before
    public void makeConfigBean() throws Exception {
        List<TableInputType> typesOfInputsEnums = new ArrayList<TableInputType>();
        typesOfInputsEnums.add(TableInputType.FILE);
        typesOfInputsEnums.add(TableInputType.STRING);
        List<TableInputFormat> formatsOfInputsEnums = new ArrayList<TableInputFormat>();
        formatsOfInputsEnums.add(TableInputFormat.TST);
        formatsOfInputsEnums.add(TableInputFormat.CSV);
        MultipleFormatsBean inputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        TCatNBean processBean = new TCatNBean(inputBean);
        configBean = new TableActivityConfigurationBean(processBean, TableOutputFormat.ASCII, TableOutputType.STRING, false);
   }

    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TCatN");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.inputTableParameter(1), "src/test/resources/test.tst");
        inputs.put(TableActivity.inputTableParameter(2), 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(TableActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        System.out.println(activity);
        System.out.println(inputs);
        System.out.println(expectedOutputTypes);
        
        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(TableActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("# id name      number"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("Christian"));
        assertTrue("Wrong output : Peter line missing. ", result.contains("Peter"));
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 5 found " + lines.length, lines.length == 5);
    }

    @Test
    public void executeAsynch2() throws Exception {
        System.out.println("Running TCat 3 inputs");
        List<TableInputType> typesOfInputsEnums = new ArrayList<TableInputType>();
        typesOfInputsEnums.add(TableInputType.FILE);
        typesOfInputsEnums.add(TableInputType.STRING);
        typesOfInputsEnums.add(TableInputType.FILE);
        List<TableInputFormat> formatsOfInputsEnums = new ArrayList<TableInputFormat>();
        formatsOfInputsEnums.add(TableInputFormat.TST);
        formatsOfInputsEnums.add(TableInputFormat.CSV);
        formatsOfInputsEnums.add(TableInputFormat.CSV);
        FlexibleInputsBean inputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        configBean.getProcess().setInputs(inputBean);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.inputTableParameter(1), "src/test/resources/test.tst");
        inputs.put(TableActivity.inputTableParameter(2), 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");
        inputs.put(TableActivity.inputTableParameter(3), "src/test/resources/test2.csv");

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
        assertTrue("Wrong output : Header line missing. ", result.contains("# id name      number"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("Christian"));
        assertTrue("Wrong output : Peter line missing. ", result.contains("Peter"));
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 7 found " + lines.length, lines.length == 7);
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
        ArrayList<TableInputFormat> formatsOfInputs = new ArrayList<TableInputFormat>();
        formatsOfInputs.add(TableInputFormat.TST);
        formatsOfInputs.add(TableInputFormat.CSV);
        formatsOfInputs.add(TableInputFormat.CSV);
        formatsOfInputs.add(TableInputFormat.CSV);
        FlexibleInputsBean inputBean = (FlexibleInputsBean)configBean.getProcess().getInputs();
        inputBean.setFormatsOfInputs(formatsOfInputs);
        ArrayList<TableInputType> typesOfInputs = new ArrayList<TableInputType>();
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.STRING);
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.FILE);
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
        ArrayList<TableInputFormat> formatsOfInputs = new ArrayList<TableInputFormat>();
        formatsOfInputs.add(TableInputFormat.TST);
        formatsOfInputs.add(TableInputFormat.CSV);
        formatsOfInputs.add(TableInputFormat.CSV);
        formatsOfInputs.add(TableInputFormat.CSV);
        FlexibleInputsBean inputBean = (FlexibleInputsBean)configBean.getProcess().getInputs();
        inputBean.setFormatsOfInputs(formatsOfInputs);
        ArrayList<TableInputType> typesOfInputs = new ArrayList<TableInputType>();
        typesOfInputs.add(TableInputType.FILE);
        typesOfInputs.add(TableInputType.STRING);
        typesOfInputs.add(TableInputType.FILE);
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
