package net.sf.taverna.t2.activities.table.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.input.FlexibleInputsBean;
import net.sf.taverna.t2.activities.table.input.MultipleFormatsBean;
import net.sf.taverna.t2.activities.table.process.TJoinBean;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;
import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TJoinActivityTest {

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
        TJoinBean processBean = new TJoinBean(inputBean);
        configBean = new TableActivityConfigurationBean(processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);       
   }

@Ignore
    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TJoin");
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

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(TableActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456"));
    }

@Ignore
    @Test
    public void executeAsynch2() throws Exception {
        System.out.println("Running Join 3 inputs");
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
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2,id_3,name_3,number_3"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433,4,Micheal,23234"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456,6,Jack,3453"));
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 3 found " + lines.length, lines.length == 3);
    }

    @Test
    public void configurations() throws Exception {
        activity.configure(configBean);
        List<TableParameterConfiguration> all = activity.configurations();
    }

}

