package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.preprocess.SortPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.UserSpecifiedPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SortPreProcessorActivityTest {

    private StiltsBean configBean;

    private final StiltsActivity activity = new StiltsActivity();

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
        SingleInputBean inputBean = new SingleInputBean(StiltsInputFormat.CSV, StiltsInputType.STRING);
        SortPreProcessorBean preProcessBean = new SortPreProcessorBean("$1", false, false);
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new StiltsBean(preProcessBean, processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);
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
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, input);

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
    public void addSortDown() throws Exception {
        System.out.println("sortDown");
        SingleInputBean inputBean = new SingleInputBean(StiltsInputFormat.CSV, StiltsInputType.STRING);
        SortPreProcessorBean preProcessBean = new SortPreProcessorBean("id", true, true);
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new StiltsBean(preProcessBean, processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);
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
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, input);

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
