package net.sf.taverna.t2.activities.table.process;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.input.SingleInputBean;
import net.sf.taverna.t2.activities.table.preprocess.SortPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.UserSpecifiedPreProcessorBean;
import net.sf.taverna.t2.activities.table.process.TPipeBean;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;
import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SortPreProcessorActivityTest {

    private TableActivityConfigurationBean configBean;

    private final TableActivity activity = new TableActivity();

    @Before
    public void makeConfigBean() throws Exception {
    }

    //@Test(expected = ActivityConfigurationException.class)
    //public void invalidConfiguration() throws ActivityConfigurationException {
    //    configBean.setFormatOfOutput("invalidExample");
    //    // Should throw ActivityConfigurationException
    //    activity.configure(configBean);
    //}

    @Test
    public void addSort() throws Exception {
        System.out.println("sort");
        SingleInputBean inputBean = new SingleInputBean(TableInputFormat.CSV, TableInputType.STRING);
        SortPreProcessorBean preProcessBean = new SortPreProcessorBean("$1", false, false);
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new TableActivityConfigurationBean(preProcessBean, processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);
        String input = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "3,Peter,900" + System.lineSeparator() + 
            "2,Christian,4567" + System.lineSeparator();
        String expected = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator() +
            "3,Peter,900" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.INPUT_TABLE_PARAMETER_NAME, input);

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
    public void addSort2() throws Exception {
        System.out.println("sort2");
        SingleInputBean inputBean = new SingleInputBean(TableInputFormat.CSV, TableInputType.STRING);
        SortPreProcessorBean preProcessBean = new SortPreProcessorBean("$1 number", false, false);
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new TableActivityConfigurationBean(preProcessBean, processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);
        String input = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "3,Peter,900" + System.lineSeparator() + 
            "1,John,1230" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator() +
            "3,Peter,800" + System.lineSeparator();
        String expected = "id,name,number" + System.lineSeparator() +
            "1,John,1230" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator() +
            "3,Peter,800" + System.lineSeparator() + 
            "3,Peter,900" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.INPUT_TABLE_PARAMETER_NAME, input);

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
    public void addSortDown() throws Exception {
        System.out.println("sortDown");
        SingleInputBean inputBean = new SingleInputBean(TableInputFormat.CSV, TableInputType.STRING);
        SortPreProcessorBean preProcessBean = new SortPreProcessorBean("id", true, true);
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new TableActivityConfigurationBean(preProcessBean, processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);
        String input = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "3,Peter,900" + System.lineSeparator() + 
            ",Christian,4567" + System.lineSeparator();
         String expected = "id,name,number" + System.lineSeparator() +
            ",Christian,4567" + System.lineSeparator() +
            "3,Peter,900" + System.lineSeparator() +
            "1,John,1234"+ System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.INPUT_TABLE_PARAMETER_NAME, input);

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
