package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.preprocess.KeepColumnPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;

import org.junit.Before;
import org.junit.Test;

public class KeepColumnPreProcessorActivityTest {

    private StiltsBean configBean;

    private final StiltsActivity activity = new StiltsActivity();

    private static final String CSV_STRING = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator();

    @Before
    public void makeConfigBean() throws Exception {
        SingleInputBean inputBean = new SingleInputBean(StiltsInputFormat.TST, StiltsInputType.FILE);
        KeepColumnPreProcessorBean preProcessBean = new KeepColumnPreProcessorBean("1");
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new StiltsBean(preProcessBean, processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);
    }

    //@Test(expected = ActivityConfigurationException.class)
    //public void invalidConfiguration() throws ActivityConfigurationException {
    //    configBean.setFormatOfOutput("invalidExample");
    //    // Should throw ActivityConfigurationException
    //    activity.configure(configBean);
    //}

    @Test
    public void delCol12() throws Exception {
        System.out.println("delCol12");
        KeepColumnPreProcessorBean preProcessorBean = (KeepColumnPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setColumnList("3");
        String expected = "number" + System.lineSeparator() +
            "1234" + System.lineSeparator() +
            "4567" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

    @Test
    public void delCol3() throws Exception {
        System.out.println("delCol3");
        KeepColumnPreProcessorBean preProcessorBean = (KeepColumnPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setColumnList("1 2");
        String expected = "id,name" + System.lineSeparator() +
            "1,John" + System.lineSeparator() +
            "2,Christian" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

    @Test
    public void delColName() throws Exception {
        System.out.println("delColName");
        KeepColumnPreProcessorBean preProcessorBean = (KeepColumnPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setColumnList("number");
        String expected = "number" + System.lineSeparator() +
            "1234" + System.lineSeparator() +
            "4567" + System.lineSeparator();
        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }
}
