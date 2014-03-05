package net.sf.taverna.t2.activities.stilts;

import java.io.BufferedReader;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import net.sf.taverna.t2.activities.stilts.utils.MyUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.taverna.t2.activities.stilts.utils.RunStatus;
import net.sf.taverna.t2.activities.stilts.utils.StiltsRunner;
import net.sf.taverna.t2.activities.stilts.utils.StreamRerouter;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.AbstractAsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class AbstractStilsActivity<BoundedBean extends AbstractStiltsBean> extends
		AbstractAsynchronousActivity<BoundedBean>
{

    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String RESULT_PARAMETER_NAME = "Output";
    static final String STILTS_PARAMETER_NAME = "Stilts Parameters";
    static final String ERROR_PARAMETER_NAME = "Stilts Errors";
    
    //These booleans are used in methods call to flag if the parameter must be present
    static final boolean REQUIRED_PARAMETER = true;
    static final boolean OPTIONAL_PARAMETER = true;
    public static final String FAILED_MESSAGE = "Run failed! check " + ERROR_PARAMETER_NAME + " for details.";
    
    
    protected BoundedBean configBean;

    @Override
    public final void configure(BoundedBean configBean) throws ActivityConfigurationException {
        checkBean(configBean);
       // Store for getConfiguration(), but you could also make
        // getConfiguration() return a new bean from other sources
        this.configBean = configBean;

        // REQUIRED: (Re)create inputPath/output ports depending on configuration
        configurePorts();
    }
    
    protected void checkBean(BoundedBean configBean) throws ActivityConfigurationException {
        this.configBean = configBean;
        if (!StiltsConfigurationConstants.VALID_OUTPUT_FORMATS_LIST.contains(
                configBean.getFormatOfOutput())) {
            throw new ActivityConfigurationException(
                    "Output format \"" + configBean.getFormatOfOutput() + 
                    "\" not valid. Must be one of " + 
                    StiltsConfigurationConstants.VALID_OUTPUT_FORMATS_LIST);
        }
        if (!StiltsConfigurationConstants.VALID_OUTPUT_TYPE_LIST.contains(
                configBean.getTypeOfOutput())) {
            throw new ActivityConfigurationException(
                    "Output format \"" + configBean.getTypeOfOutput() + 
                    "\" not valid. Must be one of " + 
                    StiltsConfigurationConstants.VALID_OUTPUT_TYPE_LIST);
        }
    }

    protected void configurePorts() {
        // In case we are being reconfigured - remove existing ports first
        // to avoid duplicates
        removeInputs();
        removeOutputs();

        // Single value outputFile port (depth 0)
        addOutput(RESULT_PARAMETER_NAME, 0);
        
        if (configBean.isDebugMode()){
            addOutput(STILTS_PARAMETER_NAME, 1);
            addOutput(ERROR_PARAMETER_NAME, 0);
        }
     }
	
    private String readFile(final AsynchronousActivityCallback callback, File file) {
        FileReader fileReader;
        try {
            fileReader = new FileReader (file);
        } catch (FileNotFoundException ex) {
            callback.fail("Error Opening " + file.getAbsolutePath(), ex);
            return null;           
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String  line;
        try {
            line = reader.readLine();
        } catch (IOException ex) {
            callback.fail("Error reading " + file.getAbsolutePath(), ex);
            return null;                       
        }
        if (line == null){
            callback.fail("Empty output file " + file.getAbsolutePath());
            return null;                                   
        }
        StringBuilder  stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while(line != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
            try {
                line = reader.readLine();
            } catch (IOException ex) {
                callback.fail("Error reading " + file.getAbsolutePath(), ex);
                return null;                       
            }
        }
        return stringBuilder.toString();
    }   

    private String prepareOutput(final AsynchronousActivityCallback callback, File outputFile) {
        if (configBean.getTypeOfOutput().equals(StiltsConfigurationConstants.FILE_PATH_TYPE)){
            return outputFile.getAbsolutePath();
        }
        return readFile(callback, outputFile);
    }
    
    private void close(ByteArrayOutputStream errorStream){
        try {
            errorStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }
    
    protected final String getInputFilePath(final AsynchronousActivityCallback callback,
            String type, String input) {
       if (type == null){
            callback.fail("Unexpected type null.");
            return null;           
       }
       if (type.isEmpty()){
            callback.fail("Unexpected empty type.");
            return null;           
       }
       if (input == null){
            callback.fail("Unexpected input null.");
            return null;           
       }
       if (input.isEmpty()){
            callback.fail("Unexpected empoty input.");
            return null;           
       }
       if (type.equals(StiltsConfigurationConstants.FILE_PATH_TYPE)){
           File test = new File(input);
           if (test.exists()){
               return test.getAbsolutePath();
           } else {
               callback.fail("Unable to locate file at " + test.getAbsolutePath());
               return null;
           }
       }
       try {
            return MyUtils.writeStringAsTmpFile(input).getAbsolutePath();
        } catch (IOException ex) {
            callback.fail("Error writing input to tempFile.", ex);
            return null;           
        }
    }
  
    protected final String getStringParameter(final Map<String, T2Reference> inputs, 
            final AsynchronousActivityCallback callback, String parameterName, boolean required){
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        T2Reference reference = inputs.get(parameterName);
        if (reference == null){
            callback.fail("Unconnected Parameter \"" +  parameterName + "\"");
            return null;
        }
        String input = (String) referenceService.renderIdentifier
                (reference, String.class, context); 
        if (required){
            if (input == null){
                callback.fail("Missing Parameter \"" +  parameterName + "\"");
                return null;
            }
            if (input.isEmpty()){
                callback.fail("Empty Parameter \"" +  parameterName + "\"");
                return null;
            }            
        }
        return input;
    }
             
    private boolean writeStringParameter(final AsynchronousActivityCallback callback, 
            Map<String, T2Reference> outputs, String value, String parameterName){
        boolean validPort = false;
        for (OutputPort port:this.getOutputPorts()){
            if (port.getName().equals(parameterName)){
                validPort = true;
            }
        }
        if (!validPort){
            callback.fail("Unconnected Parameter \"" +  parameterName + "\"");
            return false;
        }
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        T2Reference errorRef = referenceService.register(value, 0, true, context);
        outputs.put(parameterName, errorRef);                        
         return true;
    }
    
    private void toSystemOut(String[] parameters){
        for (int i = 0; i< parameters.length; i++){
            System.out.println(parameters[i]);
        }
    }
    
    //===

    private File createOutputFile(final AsynchronousActivityCallback callback) {
        try {
            return File.createTempFile("Stilts", ".txt");
        } catch (IOException ex) {
            callback.fail("Error preparing outputFile: ", ex);
            return null;
        }
    }

    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile){
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add("ofmt=" + configBean.getFormatOfOutput());
        parameters.add("out=" + outputFile);
        return parameters;
    }

    private Map<String, T2Reference> prepareOutputs(final AsynchronousActivityCallback callback, String[] parameters) {
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        Map<String, T2Reference> outputs = new HashMap<String, T2Reference>();
        if (configBean.isDebugMode()){
            T2Reference parametersRef = referenceService.register(parameters, 1, true, context);
            outputs.put(STILTS_PARAMETER_NAME, parametersRef);  
        }
        return outputs;        
    }

    private boolean doRun(final AsynchronousActivityCallback callback, String[] parameters, Map<String, T2Reference> outputs) {
        toSystemOut(parameters);
        
        StreamRerouter rerouter;
        try {
            rerouter = new StreamRerouter();
        } catch (IOException ex) {
            callback.fail("Error rerouting system streams  ", ex);
            return false;
        }       
        Thread rerouterThread = new Thread(rerouter);
        rerouterThread.start();
        
        StiltsRunner runner = new StiltsRunner(rerouter, parameters);
        Thread runnerThread = new Thread(runner);
        runnerThread.start();
        try {
            runnerThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractStilsActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (configBean.isDebugMode()){
            writeStringParameter(callback, outputs, rerouter.getSavedErr(), ERROR_PARAMETER_NAME);
        }
        if (rerouter.getRunStatus() != RunStatus.SUCCESS){
            if (configBean.isDebugMode()){
                return false;
            } else {
                callback.fail("Error running Stilts " + rerouter.getSavedErr());
                return false;
            }
        }
        System.out.println("Run done "+ rerouter.getRunStatus());
        return true;
    }

    protected void addResults(final AsynchronousActivityCallback callback, 
            Map<String, T2Reference> outputs, File outputFile, boolean runSuccessfull) {
        if (runSuccessfull){
            String outputValue = prepareOutput(callback, outputFile);
            if (outputValue == null){
                return;
            }
            writeStringParameter(callback, outputs, outputValue, RESULT_PARAMETER_NAME);						
            callback.receiveResult(outputs, new int[0]);
        } else if (configBean.isDebugMode()){
            //We still need to put something in output
            writeStringParameter(callback, outputs, FAILED_MESSAGE, RESULT_PARAMETER_NAME);						            
            //We still pass the result as the service is not failing
            callback.receiveResult(outputs, new int[0]);
        }
        //else do nothing as callback fail already called.
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeAsynch(final Map<String, T2Reference> inputs,
            final AsynchronousActivityCallback callback) {
        // Don't execute service directly now, request to be run ask to be run
        // from thread pool and return asynchronously
        callback.requestRun(new Runnable() {
			
            public void run() {                
                File outputFile = createOutputFile(callback);
                if (outputFile == null){
                    return;
                }
                List<String> parameterList = prepareParameters(inputs, callback, outputFile);
                if (parameterList == null){           
                    return;
                }           
                String[] parameters = parameterList.toArray(new String[0]);
                
                Map<String, T2Reference> outputs = prepareOutputs(callback, parameters);
                if (outputs == null){ //Currently never happens but for completeness and consitency
                    return;
                }
                
                boolean runSuccessfull = doRun(callback, parameters, outputs);

                addResults(callback, outputs, outputFile, runSuccessfull);

            }

          });
    }	
    
    @Override
    public final BoundedBean getConfiguration() {
        return this.configBean;
    }

}
