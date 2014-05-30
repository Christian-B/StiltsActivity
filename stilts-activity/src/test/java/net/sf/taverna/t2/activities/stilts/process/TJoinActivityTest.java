package net.sf.taverna.t2.activities.stilts.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.configuration.AllConfigurations;
import net.sf.taverna.t2.activities.stilts.configuration.ConfigurationGroup;
import net.sf.taverna.t2.activities.stilts.configuration.ListConfiguration;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.input.FlexibleInputsBean;
import net.sf.taverna.t2.activities.stilts.input.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TJoinActivityTest {

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
        TJoinBean processBean = new TJoinBean(inputBean);
        configBean = new StiltsBean(processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);       
   }

@Ignore
    @Test
    public void executeAsynch() throws Exception {
        System.out.println("Running TJoin");
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

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456"));
    }

@Ignore
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
        assertTrue("Wrong output : Header line missing. ", result.contains("id_1,name_1,number_1,id_2,name_2,number_2,id_3,name_3,number_3"));
        assertTrue("Wrong output : John line missing. ", result.contains("1,John,1234,45,Peter,1433,4,Micheal,23234"));
        assertTrue("Wrong output : Christian line missing. ", result.contains("2,Christian,4567,22,Jack,456,6,Jack,3453"));
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 3 found " + lines.length, lines.length == 3);
    }

@Ignore
    @Test
    public void configurationsAdd() throws Exception {
        activity.configure(configBean);
        AllConfigurations all = activity.configurations();
        ConfigurationGroup group = all.getGroups().get(0);
        System.out.println(group.getTitle());    
        ListConfiguration listConfig = (ListConfiguration)group.getConfigurations().get(0);
        StiltsConfiguration config = listConfig.getConfigurations().get(1);
        assertTrue(config.getItem() instanceof StiltsInputType);
        assertTrue(config.getName().endsWith("2"));
        listConfig.addToLists();
        config = listConfig.getConfigurations().get(2);
        assertTrue(config.getItem() instanceof StiltsInputType);        
        assertTrue(config.getName().endsWith("3"));
    }
@Ignore

    @Test
    public void configurationsEquals() throws Exception {
        activity.configure(configBean);
        AllConfigurations all1 = activity.configurations();
        AllConfigurations all2 = activity.configurations();
        assertTrue(all1.equals(all2));
        ConfigurationGroup group = all2.getGroups().get(0);
        ListConfiguration listConfig = (ListConfiguration)group.getConfigurations().get(0);
        listConfig.deleteLastFromLists();
        assertTrue(!all1.equals(all2));
        AllConfigurations all3 = activity.configurations();
        //ystem.out.println(3);
        group = all3.getGroups().get(0);
        listConfig = (ListConfiguration)group.getConfigurations().get(0);
        listConfig.getConfigurations().get(1).setItem(null);
        assertTrue(!all1.equals(all3));
        listConfig.deleteLastFromLists();
        assertTrue(all2.equals(all3));
    }

    @Test
    public void configurationsCheck() throws Exception {
        activity.configure(configBean);
        AllConfigurations all1 = activity.configurations();
        activity.checkConfiguration(all1);
    }

    @Test (expected = ActivityConfigurationException.class)
    public void configurationsCheckNull() throws Exception {
        activity.configure(configBean);
        AllConfigurations all1 = activity.configurations();
        ConfigurationGroup group = all1.getGroups().get(0);
        ListConfiguration listConfig = (ListConfiguration)group.getConfigurations().get(0);
        listConfig.getConfigurations().get(1).setItem(null);
        activity.checkConfiguration(all1);
    }

    @Test (expected = ActivityConfigurationException.class)
    public void configurationsCheckBad() throws Exception {
        activity.configure(configBean);
        AllConfigurations all1 = activity.configurations();
        ConfigurationGroup group = all1.getGroups().get(0);
        ListConfiguration listConfig = (ListConfiguration)group.getConfigurations().get(0);
        listConfig.getConfigurations().get(1).setItem(StiltsInputFormat.IPAC);
        activity.checkConfiguration(all1);
    }
}

