package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.utils.NoExitSecurityManager;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import net.sf.taverna.t2.activities.stilts.utils.MyUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.AbstractAsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;
import uk.ac.starlink.ttools.Stilts;

public class OutputTypeActivity<OutputType extends OutputTypeBean> extends
		AbstractAsynchronousActivity<OutputType>
		implements AsynchronousActivity<OutputType> 
{

    @Override
    public void configure(OutputType configBean)
            throws ActivityConfigurationException {
    }
    
    /*
     * Best practice: Keep port names as constants to avoid misspelling. This
     * would not apply if port names are looked up dynamically from the servic
     * operation, like done for WSDL services.
     */
    static final String RESULT_PARAMETER_NAME = "Output";
    static final String SUCCESS_MESSAGE = "Stilts Success!";
    static final String STILTS_PARAMETER_NAME = "Stilts Parameters";
    static final String ERROR_PARAMETER_NAME = "Stilts Errors";
	
    protected OutputType configBean;

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
	
    private String prepareOutput(File outputFile) throws IOException{
       if (configBean.getTypeOfOutput().equals(StiltsConfigurationConstants.FILE_PATH_TYPE)){
            return outputFile.getAbsolutePath();
       }
       return MyUtils.readFileAsString(outputFile.getAbsolutePath());
    }
    
    private void close(ByteArrayOutputStream errorStream){
        try {
            errorStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
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
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        System.setProperty("votable.strict", "false");

        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        SecurityException scurityException = null;
        
        SecurityManager securityBackup = System.getSecurityManager();
        System.setSecurityManager(new NoExitSecurityManager());
                 
        PrintStream oldError = System.err;
        System.setErr(new PrintStream(errorStream));
                
        try{
            Stilts.main(parameters);
        }catch(SecurityException ex){
            scurityException = ex;
        } finally{
            System.setSecurityManager(securityBackup);
            System.setErr(oldError);
        }
        if (!errorStream.toString().isEmpty()){
            System.err.println(errorStream.toString());
        }

        T2Reference errorRef = referenceService.register(errorStream.toString(), 0, true, context);
        outputs.put(ERROR_PARAMETER_NAME, errorRef);                        
        if (scurityException != null && ! configBean.isDebugMode()){
            callback.fail("Error running Stilts " + errorStream.toString());
            close(errorStream);
            return false;
        }
        close(errorStream);
        return true;
    }

    protected void addResults(final AsynchronousActivityCallback callback, Map<String, T2Reference> outputs, File outputFile) {
        InvocationContext context = callback.getContext();
        ReferenceService referenceService = context.getReferenceService();
        
        String outputValue;
        try {
            outputValue = prepareOutput(outputFile);
        } catch (IOException ex) {
            callback.fail("Error preparing output", ex);
            return;
        }
        T2Reference simpleRef = referenceService.register(outputValue, 0, true, context);
        outputs.put(RESULT_PARAMETER_NAME, simpleRef);
						
        callback.receiveResult(outputs, new int[0]);
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
                if (!doRun(callback, parameters, outputs)){
                    return;
                }
                addResults(callback, outputs, outputFile);
            }
            
          });
    }	
    
    @Override
    public OutputType getConfiguration() {
        return this.configBean;
    }

}
