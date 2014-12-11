package net.sf.taverna.t2.activities.table.process;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.activities.table.*;
import net.sf.taverna.t2.activities.table.input.TwoInputsBean;
import net.sf.taverna.t2.activities.table.process.ExactMatchBean;
import net.sf.taverna.t2.activities.table.utils.TableFind;
import net.sf.taverna.t2.activities.table.utils.TableFixcols;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.activities.table.utils.TableJoin;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;
import net.sf.taverna.t2.activities.testutils.ActivityInvoker;

import org.junit.Before;
import org.junit.Test;

public class ExactMatchActivityTest {

    private TableActivityConfigurationBean configBean;

    private final TableActivity activity = new TableActivity();

    @Before
    public void makeConfigBean() throws Exception {
        List<TableInputType> typesOfInputsEnums = new ArrayList<TableInputType>();
        typesOfInputsEnums.add(TableInputType.STRING);
        typesOfInputsEnums.add(TableInputType.STRING);
        List<TableInputFormat> formatsOfInputsEnums = new ArrayList<TableInputFormat>();
        formatsOfInputsEnums.add(TableInputFormat.CSV);
        formatsOfInputsEnums.add(TableInputFormat.CSV);
        TwoInputsBean inputBean = new TwoInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        ExactMatchBean processBean = new ExactMatchBean(1, TableFind.ALL, TableFixcols.DUPS, TableJoin.ONE_AND_TWO, inputBean);
        configBean = new TableActivityConfigurationBean(processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);
    }
   
    @Test
    public void executeAsynch() throws Exception {
        System.out.println("executeAsynch");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.inputTableParameter(1), 
                "code,Last,Email,Job" + System.lineSeparator() + 
                "1,Smith,test@example.com,Programmer" + System.lineSeparator() + 
                "2,Brown,test@example.com,Boss");
        inputs.put(TableActivity.inputTableParameter(2), 
                "id,name,number" + System.lineSeparator() + 
                "1,Peter,1433" + System.lineSeparator() + 
                "2,Jack,456");
        inputs.put(TableActivity.getMatchColumnName(1, 1),"code"); 
        inputs.put(TableActivity.getMatchColumnName(2, 1),"id"); 

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
        inputs.put(TableActivity.inputTableParameter(1), 
                "code,first, Last,Email,Job" + System.lineSeparator() + 
                "1,Peter,Smith,test@example.com,Programmer" + System.lineSeparator() + 
                "2,Jack,Brown,test@example.com,Boss");
        inputs.put(TableActivity.inputTableParameter(2), 
                "id,name,number" + System.lineSeparator() + 
                "1,Peter,1433" + System.lineSeparator() + 
                "2,Peter,1433" + System.lineSeparator() + 
                "2,Jack,456");
        inputs.put(TableActivity.getMatchColumnName(1, 1),"code"); 
        inputs.put(TableActivity.getMatchColumnName(1, 2),"first"); 
        inputs.put(TableActivity.getMatchColumnName(2, 1),"id"); 
        inputs.put(TableActivity.getMatchColumnName(2, 2),"name"); 

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

    @Test
    public void configurations() throws Exception {
        ExactMatchBean processBean = (ExactMatchBean)configBean.getProcess();
        activity.configure(configBean);
        activity.configurations();
    }
 }
