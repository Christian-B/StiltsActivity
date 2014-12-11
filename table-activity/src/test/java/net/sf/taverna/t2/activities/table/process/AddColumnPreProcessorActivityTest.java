package net.sf.taverna.t2.activities.table.process;

import java.util.HashMap;
import java.util.Map;

import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.input.SingleInputBean;
import net.sf.taverna.t2.activities.table.preprocess.AddColumnPreProcessorBean;
import net.sf.taverna.t2.activities.table.process.TPipeBean;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;
import net.sf.taverna.t2.activities.table.utils.TableInputType;
import net.sf.taverna.t2.activities.table.utils.TableLocationType;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;
import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class AddColumnPreProcessorActivityTest {

    private TableActivityConfigurationBean configBean;

    private final TableActivity activity = new TableActivity();

    private static final String CSV_STRING = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator();

    @Before
    public void makeConfigBean() throws Exception {
        SingleInputBean inputBean = new SingleInputBean(TableInputFormat.TST, TableInputType.FILE);
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new TableActivityConfigurationBean(processBean, TableOutputFormat.CSV, TableOutputType.STRING, false);
    }

    //@Test(expected = ActivityConfigurationException.class)
    //public void invalidConfiguration() throws ActivityConfigurationException {
    //    configBean.setFormatOfOutput("invalidExample");
    //    // Should throw ActivityConfigurationException
    //    activity.configure(configBean);
    //}

    @Test
    public void byCommand() throws Exception {
        System.out.println("byCommand");
        AddColumnPreProcessorBean preProcessBean = new AddColumnPreProcessorBean("$1 + $3", "newCol", TableLocationType.AFTER, "$2");
        configBean.setPreprocess(preProcessBean);
        String expected = "id,name,newCol,number" + System.lineSeparator() +
            "1,John,1235,1234" + System.lineSeparator() +
            "2,Christian,4569,4567" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(TableActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(TableActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        String result = outputs.get(TableActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

}
