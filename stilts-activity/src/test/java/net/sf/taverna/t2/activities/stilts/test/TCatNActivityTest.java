package net.sf.taverna.t2.activities.stilts.test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityInputPort;

import org.junit.Before;
import org.junit.Test;

public class TCatNActivityTest {

    private StiltsBean configBean;

    private final StiltsActivity activity = new StiltsActivity();

    @Before
    public void makeConfigBean() throws Exception {
        List<StiltsInputType> typesOfInputsEnums = new ArrayList<StiltsInputType>();
        typesOfInputsEnums.add(StiltsInputType.FILE);
        typesOfInputsEnums.add(StiltsInputType.STRING);
        List<StiltsInputFormat> formatsOfInputsEnums = new ArrayList<StiltsInputFormat>();
        formatsOfInputsEnums.add(StiltsInputFormat.TST);
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);
        MultipleFormatsBean inputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        TCatNBean processBean = new TCatNBean(inputBean);
        configBean = new StiltsBean(processBean, StiltsOutputFormat.ASCII, StiltsOutputType.STRING, false);
   }

    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TCatN");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.inputTableParameter(1), "src/test/resources/test.tst");
        inputs.put(StiltsActivity.inputTableParameter(2), 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        System.out.println(activity);
        System.out.println(inputs);
        System.out.println(expectedOutputTypes);
        
        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
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
        List<StiltsInputType> typesOfInputsEnums = new ArrayList<StiltsInputType>();
        typesOfInputsEnums.add(StiltsInputType.FILE);
        typesOfInputsEnums.add(StiltsInputType.STRING);
        typesOfInputsEnums.add(StiltsInputType.FILE);
        List<StiltsInputFormat> formatsOfInputsEnums = new ArrayList<StiltsInputFormat>();
        formatsOfInputsEnums.add(StiltsInputFormat.TST);
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);
        FlexibleInputsBean inputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        configBean.getProcess().setInputs(inputBean);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.inputTableParameter(1), "src/test/resources/test.tst");
        inputs.put(StiltsActivity.inputTableParameter(2), 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");
        inputs.put(StiltsActivity.inputTableParameter(3), "src/test/resources/test2.csv");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
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
        ArrayList<String> formatsOfInputs = new ArrayList<String>();
        formatsOfInputs.add(StiltsInputFormat.TST.getStiltsName());
        formatsOfInputs.add(StiltsInputFormat.CSV.getStiltsName());
        formatsOfInputs.add(StiltsInputFormat.CSV.getStiltsName());
        formatsOfInputs.add(StiltsInputFormat.CSV.getStiltsName());
        FlexibleInputsBean inputBean = (FlexibleInputsBean)configBean.getProcess().getInputs();
        inputBean.setFormatsOfInputs(formatsOfInputs);
        ArrayList<String> typesOfInputs = new ArrayList<String>();
        typesOfInputs.add(StiltsInputType.FILE.getUserName());
        typesOfInputs.add(StiltsInputType.STRING.getUserName());
        typesOfInputs.add(StiltsInputType.FILE.getUserName());
        typesOfInputs.add(StiltsInputType.FILE.getUserName());
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
        ArrayList<String> formatsOfInputs = new ArrayList<String>();
        formatsOfInputs.add(StiltsInputFormat.TST.getStiltsName());
        formatsOfInputs.add(StiltsInputFormat.CSV.getStiltsName());
        formatsOfInputs.add(StiltsInputFormat.CSV.getStiltsName());
        formatsOfInputs.add(StiltsInputFormat.CSV.getStiltsName());
        FlexibleInputsBean inputBean = (FlexibleInputsBean)configBean.getProcess().getInputs();
        inputBean.setFormatsOfInputs(formatsOfInputs);
        ArrayList<String> typesOfInputs = new ArrayList<String>();
        typesOfInputs.add(StiltsInputType.FILE.getUserName());
        typesOfInputs.add(StiltsInputType.STRING.getUserName());
        typesOfInputs.add(StiltsInputType.FILE.getUserName());
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

    @Test(expected = UnsupportedOperationException.class)
    public void reConfiguredActivityTypeError() throws Exception {
        ArrayList<String> formatsOfInputs = new ArrayList<String>();
        formatsOfInputs.add("tst");
        formatsOfInputs.add("opps");
        MultipleFormatsBean inputBean = (MultipleFormatsBean)configBean.getProcess().getInputs();
        inputBean.setFormatsOfInputs(formatsOfInputs);
    }

    @Test
    public void configureActivity() throws Exception {
        Set<String> expectedInputs = new HashSet<String>();
        expectedInputs.add(StiltsActivity.inputTableParameter(1));
        expectedInputs.add(StiltsActivity.inputTableParameter(2));

        Set<String> expectedOutputs = new HashSet<String>();
        expectedOutputs.add(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME);
        if (configBean.isDebugMode()){
            expectedOutputs.add(StiltsActivity.ERROR_PARAMETER_NAME);
            expectedOutputs.add(StiltsActivity.STILTS_PARAMETER_NAME);
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
