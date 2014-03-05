package net.sf.taverna.t2.activities.stilts;

import java.util.ArrayList;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;

import org.junit.Before;
import org.junit.Test;

public class TJoinActivityTest {

    private MultipleFormatsBean configBean;

    private final TJoinActivity activity = new TJoinActivity();

    @Before
    public void makeConfigBean() throws Exception {
        configBean = new MultipleFormatsBean();
        configBean.setNumberOfInputs(2);
        configBean.setFormatOfOutput("csv");
        ArrayList<String> formatsOfInputs = new ArrayList<String>();
        formatsOfInputs.add("tst");
        formatsOfInputs.add("csv");
        configBean.setFormatsOfInputs(formatsOfInputs);
        ArrayList<String> typesOfInputs = new ArrayList<String>();
        typesOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typesOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setTypesOfInputs(typesOfInputs);
        configBean.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setFixedNumberOfInputs(false);
   }

    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TJoin");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+1, "src/test/resources/test.tst");
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+2, 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456"));
    }

    @Test
    public void executeAsynch2() throws Exception {
        System.out.println("Running Join 3 inputs");
        ArrayList<String> formatsOfInputs = new ArrayList<String>();
        formatsOfInputs.add("tst");
        formatsOfInputs.add("csv");
        formatsOfInputs.add("csv");
        configBean.setFormatsOfInputs(formatsOfInputs);
        ArrayList<String> typesOfInputs = new ArrayList<String>();
        typesOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        typesOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        typesOfInputs.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        configBean.setTypesOfInputs(typesOfInputs);
        configBean.setNumberOfInputs(3);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+1, "src/test/resources/test.tst");
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+2, 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+3, "src/test/resources/test2.csv");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2,id_3,name_3,number_3"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433,4,Micheal,23234"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456,6,Jack,3453"));
    }

}



