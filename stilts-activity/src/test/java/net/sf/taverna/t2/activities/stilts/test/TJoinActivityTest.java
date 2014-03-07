package net.sf.taverna.t2.activities.stilts.test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;

import org.junit.Before;
import org.junit.Test;

public class TJoinActivityTest {

    private StiltsBean configBean;

    private final StilsActivity activity = new StilsActivity();

    @Before
    public void makeConfigBean() throws Exception {
        List<StiltsInputType> typesOfInputsEnums = new ArrayList<StiltsInputType>();
        typesOfInputsEnums.add(StiltsInputType.FILE);
        typesOfInputsEnums.add(StiltsInputType.STRING);
        List<StiltsInputFormat> formatsOfInputsEnums = new ArrayList<StiltsInputFormat>();
        formatsOfInputsEnums.add(StiltsInputFormat.TST);
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);
        MultipleFormatsBean inputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        TJoinBean processBean = new TJoinBean(inputBean);
        configBean = new StiltsBean(processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);       
   }

    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TJoin");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StilsActivity.inputTableParameter(1), "src/test/resources/test.tst");
        inputs.put(StilsActivity.inputTableParameter(2), 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StilsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StilsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456"));
    }

    @Test
    public void executeAsynch2() throws Exception {
        System.out.println("Running Join 3 inputs");
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
        inputs.put(StilsActivity.inputTableParameter(1), "src/test/resources/test.tst");
        inputs.put(StilsActivity.inputTableParameter(2), 
                "id,name,number\n" +
                "45,Peter,1433\n" +
                "22,Jack,456");
        inputs.put(StilsActivity.inputTableParameter(3), "src/test/resources/test2.csv");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StilsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StilsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2,id_3,name_3,number_3"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433,4,Micheal,23234"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456,6,Jack,3453"));
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 3 found " + lines.length, lines.length == 3);
    }

}



