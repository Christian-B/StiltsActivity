package net.sf.taverna.t2.activities.table.process;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.input.SingleInputBean;
import net.sf.taverna.t2.activities.table.preprocess.KeepColumnPreProcessorBean;
import net.sf.taverna.t2.activities.table.process.TPipeBean;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;
import net.sf.taverna.t2.activities.testutils.ActivityInvoker;

import org.junit.Before;
import org.junit.Test;

public class KeepColumnPreProcessorActivityTest {

    private TableActivityConfigurationBean configBean;

    private final TableActivity activity = new TableActivity();

    private static final String CSV_STRING = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator();

    @Before
    public void makeConfigBean() throws Exception {
        SingleInputBean inputBean = new SingleInputBean(TableInputFormat.TST, TableInputType.FILE);
        KeepColumnPreProcessorBean preProcessBean = new KeepColumnPreProcessorBean("1");
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new TableActivityConfigurationBean(preProcessBean, processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);
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
        inputs.put(TableActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(TableActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(TableActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
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
        inputs.put(TableActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(TableActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(TableActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
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
        inputs.put(TableActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(TableActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(TableActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }
}
