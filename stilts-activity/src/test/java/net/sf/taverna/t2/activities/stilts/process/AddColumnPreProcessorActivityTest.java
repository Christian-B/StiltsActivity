package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnByCommandPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnOneVariablesPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnTwoVariablesPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOneVariableOperator;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsTwoVariableOperator;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;

import org.junit.Before;
import org.junit.Test;

public class AddColumnPreProcessorActivityTest {

    private StiltsBean configBean;

    private final StiltsActivity activity = new StiltsActivity();

    private static final String CSV_STRING = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator();

    @Before
    public void makeConfigBean() throws Exception {
        SingleInputBean inputBean = new SingleInputBean(StiltsInputFormat.TST, StiltsInputType.FILE);
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new StiltsBean(processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);
    }

    //@Test(expected = ActivityConfigurationException.class)
    //public void invalidConfiguration() throws ActivityConfigurationException {
    //    configBean.setFormatOfOutput("invalidExample");
    //    // Should throw ActivityConfigurationException
    //    activity.configure(configBean);
    //}

    @Test
    public void byCommand() throws Exception {
        System.out.println("byCommand");
        AddColumnByCommandPreProcessorBean preProcessBean = new AddColumnByCommandPreProcessorBean("$1 + $3", "newCol", StiltsLocationType.AFTER, "$2");
        configBean.setPreprocess(preProcessBean);
        String expected = "id,name,newCol,number" + System.lineSeparator() +
            "1,John,1235,1234" + System.lineSeparator() +
            "2,Christian,4569,4567" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

    @Test
    public void minus() throws Exception {
        System.out.println("minus");
        AddColumnTwoVariablesPreProcessorBean preProcessBean = 
                new AddColumnTwoVariablesPreProcessorBean(StiltsTwoVariableOperator.MINUS, "$3","id", "newCol" , StiltsLocationType.BEFORE, "name");
        configBean.setPreprocess(preProcessBean);
        String expected = "id,newCol,name,number" + System.lineSeparator() +
            "1,1233,John,1234" + System.lineSeparator() +
            "2,4565,Christian,4567" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }
    
    @Test
    public void max() throws Exception {
        System.out.println("max");
        AddColumnTwoVariablesPreProcessorBean preProcessBean = 
                new AddColumnTwoVariablesPreProcessorBean(StiltsTwoVariableOperator.MAX, "$3","id", "newCol");
        configBean.setPreprocess(preProcessBean);
        String expected = "id,name,number,newCol" + System.lineSeparator() +
            "1,John,1234,1234" + System.lineSeparator() +
            "2,Christian,4567,4567" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);

        System.out.println(activity);
        System.out.println(inputs);
        System.out.println(expectedOutputTypes);
        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }
    
    @Test
    public void convertToFloat() throws Exception {
        System.out.println("convertToFloat");
        AddColumnOneVariablesPreProcessorBean preProcessBean = 
                new AddColumnOneVariablesPreProcessorBean(StiltsOneVariableOperator.FLOAT, "$3", "newCol");
        configBean.setPreprocess(preProcessBean);
        String expected = "id,name,number,newCol" + System.lineSeparator() +
            "1,John,1234,1234.0" + System.lineSeparator() +
            "2,Christian,4567,4567.0" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }
    @Test
    public void roundup() throws Exception {
        System.out.println("roundup");
        AddColumnOneVariablesPreProcessorBean preProcessBean = 
                new AddColumnOneVariablesPreProcessorBean(StiltsOneVariableOperator.ROUNDUP, "(float)($3)/2.0", "newCol");
        configBean.setPreprocess(preProcessBean);
        String expected = "id,name,number,newCol" + System.lineSeparator() +
            "1,John,1234,617" + System.lineSeparator() +
            "2,Christian,4567,2284" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }
}
