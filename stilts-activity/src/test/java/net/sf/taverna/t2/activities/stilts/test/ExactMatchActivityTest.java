package net.sf.taverna.t2.activities.stilts.test;

import net.sf.taverna.t2.activities.stilts.*;
import java.util.ArrayList;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class ExactMatchActivityTest {

    private StiltsBean configBean;

    private final StiltsActivity activity = new StiltsActivity();

    @Before
    public void makeConfigBean() throws Exception {
        List<StiltsInputType> typesOfInputsEnums = new ArrayList<StiltsInputType>();
        typesOfInputsEnums.add(StiltsInputType.STRING);
        typesOfInputsEnums.add(StiltsInputType.STRING);
        List<StiltsInputFormat> formatsOfInputsEnums = new ArrayList<StiltsInputFormat>();
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);
        TwoInputsBean inputBean = new TwoInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        ExactMatchBean processBean = new ExactMatchBean(1, StiltsFind.ALL, StiltsFixcols.DUPS, StiltsJoin.ONE_AND_TWO, inputBean);
        configBean = new StiltsBean(processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);
    }
    
    @Test
    public void executeAsynch() throws Exception {
        System.out.println("executeAsynch");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.inputTableParameter(1), 
                "code,Last,Email,Job" + System.lineSeparator() + 
                "1,Smith,test@example.com,Programmer" + System.lineSeparator() + 
                "2,Brown,test@example.com,Boss");
        inputs.put(StiltsActivity.inputTableParameter(2), 
                "id,name,number" + System.lineSeparator() + 
                "1,Peter,1433" + System.lineSeparator() + 
                "2,Jack,456");
        inputs.put(ExactMatchActivity.getMatchColumnName(1, 1),"code"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(2, 1),"id"); 

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
        assertTrue("Wrong output : Header line missing. ", result.contains("code,Last,Email,Job,id,name,number"));
        assertTrue("Wrong output : Smith line missing. ", result.contains("1,Smith,test@example.com,Programmer,1,Peter,1433"));
        assertTrue("Wrong output : Brown line missing. ", result.contains("2,Brown,test@example.com,Boss,2,Jack,456"));
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 3 found " + lines.length, lines.length == 3);
    }

    @Test
    public void doubleMatch() throws Exception {
        System.out.println("doubleMatch");
        ExactMatchBean processBean = (ExactMatchBean)configBean.getProcess();
        processBean.setNumbertOfColumnsToMatch(2);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.inputTableParameter(1), 
                "code,first, Last,Email,Job" + System.lineSeparator() + 
                "1,Peter,Smith,test@example.com,Programmer" + System.lineSeparator() + 
                "2,Jack,Brown,test@example.com,Boss");
        inputs.put(StiltsActivity.inputTableParameter(2), 
                "id,name,number" + System.lineSeparator() + 
                "1,Peter,1433" + System.lineSeparator() + 
                "2,Peter,1433" + System.lineSeparator() + 
                "2,Jack,456");
        inputs.put(ExactMatchActivity.getMatchColumnName(1, 1),"code"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(1, 2),"first"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(2, 1),"id"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(2, 2),"name"); 

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
        assertTrue("Wrong output : Header line missing. ", result.contains("code,first,Last,Email,Job,id,name,number"));
        assertTrue("Wrong output : Smith line missing. ", result.contains("1,Peter,Smith,test@example.com,Programmer,1,Peter,1433"));
        assertTrue("Wrong output : Brown line missing. ", result.contains("2,Jack,Brown,test@example.com,Boss,2,Jack,456"));
        assertTrue("Wrong output : BAd Peter Line found. ", !result.contains("2,Peter"));        
        String[] lines = result.split(System.lineSeparator());
        assertTrue("Wrong number of lines. Expected 3 found " + lines.length, lines.length == 3);
    }

    @Test
    public void reConfiguredActivity() throws Exception {
        assertEquals("Unexpected inputs", 0, activity.getInputPorts().size());
        assertEquals("Unexpected outputs", 0, activity.getOutputPorts().size());
        ExactMatchBean processBean = (ExactMatchBean)configBean.getProcess();
        processBean.setNumbertOfColumnsToMatch(1);
        activity.configure(configBean);
        System.out.println(activity.getInputPorts());
        assertEquals("Unexpected inputs", 4, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
        processBean.setNumbertOfColumnsToMatch(2);
        activity.configure(configBean);
        assertEquals("Unexpected inputs", 6, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
    }

 }
