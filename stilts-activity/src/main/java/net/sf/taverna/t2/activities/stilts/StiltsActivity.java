/*
 * This is a Taverna plugin to expose the functionality of the 
 * <a href="http://www.star.bristol.ac.uk/~mbt/stilts/sun256/index.html">Stilts tool</a> within a Taverna Plugin.
 * 
 * This particular plugin is designed to expose the parts of 
 * <a href="http://www.star.bristol.ac.uk/~mbt/stilts/sun256/index.html">Stilts</a>
 * that are not specific to astronomical data.
 * 
 * For astronomical data we recommend the 
 * <a href="https://github.com/wf4ever/astrotaverna">Astrotaverna Taverna Plugin</a>
 * written by Julian Garrido, which served as the inspiration for this code
 * 
 * This  plugin is offered under the same <a href="http://www.taverna.org.uk/about/legal-stuff/taverna-licence">license </a> as Taverna
 * At the time of writing this was the <a href="http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html">Lesser General Public License (LGPL) Version 2.1</a> 
 * 
 * Stilts is offered under its own <a href="http://www.star.bristol.ac.uk/~mbt/stilts/sun256/abstract.html"">license </a>.
 * At the time of writing this was the "GNU General Public Licence." 
 */
package net.sf.taverna.t2.activities.stilts;

import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
import java.io.BufferedReader;
import net.sf.taverna.t2.activities.stilts.utils.MyUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.taverna.t2.activities.stilts.configuration.AllConfigurations;
import net.sf.taverna.t2.activities.stilts.input.*;
import net.sf.taverna.t2.activities.stilts.preprocess.*;
import net.sf.taverna.t2.activities.stilts.process.*;
import net.sf.taverna.t2.activities.stilts.utils.*;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.*;
/**
 * This is the base class for all Stilts activities, providing much of the functionality shared between all Stilts activities.
 * <p>
 * The functions of this class include, handling port configuration, 
 * executing the Stilts run, handling the output and providing various utility methods.
 * The main parts of a Stilts Activity are the process, preprocess and output options.
 * These are defined together in a StiltsBean, 
 * which includes a superclass of a StiltsProcessBean and can include a superclass of StiltsPreProcessBean.
 * <p>
 * Configuration is handled by the configure method 
 * which calls configureProcessPorts(StiltsProcessBean processBean),
 * where the type of bean determines the specific ports to add.  
 * <p>
 * Execution is handled by the executeAsynch method and is described there. 
 * This includes setting up the Stilts parameters as described in prepareParameters.
 * <p>
 * The Stilts tool has two types of outputs, it prints information to Standard output and writes to a file.
 * <p>
 * This activity creates a temporary file to which is asks Stilts to write its output to.
 * If the activity is set up to pass this file on the absolute file path is passed output.
 * This is best option if this activity will be connected to another Taverna tool/activity that expects file intput, such as all Stilts activities.
 * Alternatively the activity can be setup to provide String output.
 * In this case the activity will read the contents of the temp file into a String and return this.
 * <p>
 * Currently the plugin does not make use of Stilts' writing to Standard output. 
 * The only exception to this is in the case of Stilts returning with an irregular output,
 * in which case the activity will return the information captured on Standard Error while Stills was running in an Exception.
 * <p>
 * Optionally the Activity may be set up in debug mode, where two additional outputs are added.
 * The "Stilts Parameters" port will show the parameters as passed to Stilts.
 * The "Stilts Errors" will reflect everything captured on Standard Error while Stills was running.
 * In this case no Exception is thrown is Stilts exits with an irregular output.
 * <p>
 * Having just a Single complex AbstractAsynchronousActivity class allows a single implementation of each of 
 * ActivityConfigurationPanel, ConfigurationAction, ContextualViewFactory, StiltsContextualView and ServiceDescription
 * avoiding many similar UI binding classes.
*/
public class StiltsActivity extends AbstractAsynchronousActivity<StiltsBean> { 

    //Output Port names
    public static final String OUTPUT_TABLE_PARAMETER_NAME = "Output Table";
    public static final String STILTS_PARAMETER_NAME = "Stilts Parameters";
    public static final String ERROR_PARAMETER_NAME = "Stilts Errors";
    
    //Input Port Name
    public static final String INPUT_TABLE_PARAMETER_NAME = "Input Table";

    //These booleans are used in methods call to flag if the parameter must be present
    static final boolean REQUIRED_PARAMETER = true;
    static final boolean OPTIONAL_PARAMETER = true;
    
    //These boolean are used to flag if a method was successful or failed
    static final boolean FAILED = false;
    static final boolean OK = true;
    
    //messages
    public static final String FAILED_MESSAGE = "Run failed! check " + ERROR_PARAMETER_NAME + " for details.";
    
    private StiltsBean configBean;
   
    /**
     * This methods adds the input and output ports depending on the type of StiltsBean, and process and preprocess in includes.
     * 
     * <p>
     * The StiltsBean is checked for validity and then recorded so it can be accessed during execution.
     * <p>
     * All existing input and output ports are removed.
     * <p>
     * The output table port is added and if required the debug output ports are included.
     * <p>
     * As the type of input ports depends on the type of Stilts process and the inputs it expects.
     * @param configBean
     * @throws ActivityConfigurationException 
     */
    @Override
    public final void configure(StiltsBean configBean) throws ActivityConfigurationException {
        configBean.checkValid();
        
        // Store for getConfiguration(), but you could also make
        // getConfiguration() return a new bean from other sources
        this.configBean = configBean;

        // create inputPath/output ports depending on configuration
        removeInputs();
        removeOutputs();

        // Single value outputFile port (depth 0)
        addOutput(OUTPUT_TABLE_PARAMETER_NAME, 0);
        if (configBean.isDebugMode()){
            addOutput(STILTS_PARAMETER_NAME, 1);
            addOutput(ERROR_PARAMETER_NAME, 0);
        }
        
        //Obtain the specific process being used and add any relative input ports
        StiltsProcessBean processBean = configBean.getProcess();
        if (processBean instanceof ExactMatchBean){
            configureTMatch2Ports((TMatch2Bean)processBean);
        }
        //Currently not other implemented process requires addition input ports
        
        //Obtian the type of inputs and add the associated ports
        StitlsInputsBean inputBean = processBean.getInputs();
        configureInputPorts(inputBean);
    }
    
    @Override
    public StiltsBean getConfiguration() {
        return configBean;
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
                    return; //callback.fail already called
                }
                //Prepare the Stils parameters by combining information form the stored bean and the inputs
                List<String> parameterList = prepareParameters(inputs, callback, outputFile);
                if (parameterList == null){           
                    return; //callback.fail already called
                }           
                String[] parameters = parameterList.toArray(new String[0]);
                
                //Prepare the outputs and if required record the parameters
                Map<String, T2Reference> outputs = new HashMap<String, T2Reference>();
                if (configBean.isDebugMode()){
                    InvocationContext context = callback.getContext();
                    ReferenceService referenceService = context.getReferenceService();
                    T2Reference parametersRef = referenceService.register(parameters, 1, true, context);
                    outputs.put(STILTS_PARAMETER_NAME, parametersRef);  
                }
                
                boolean runSuccessfull = doRun(callback, parameters, outputs);

                addResults(callback, outputs, outputFile, runSuccessfull);

            }

          });
    }	
    
    /**
     * Stilts is called by passing it a list of parameters. This methods prepares that list.
     * 
     * This method will add the parameters based on the Stilts process being used,
     * The preprocess (if any) and then add the output parameters.
     * 
     * @param inputs
     * @param callback
     * @param outputFile
     * @return 
     */
    private List<String> prepareParameters(final Map<String, T2Reference> inputs, 
            final AsynchronousActivityCallback callback, File outputFile){
        //The first stilts parameter must be the name the process
        //So add this and any process specific parameters including inputs
        List<String> parameters = createProcessParameters(configBean.getProcess(), inputs, callback);
        if (parameters == null){ //createProcessParameters failed.
            return null;//callback.fail already done
        }
        //Obtains the preprocessor
        StiltsPreProcessBean preProcessor = configBean.getPreprocess();
        if (preProcessor != null){
            //Prepocessor add a singe paramteres based in their settings
            parameters.add(preProcessor.retrieveStilsCommand());
        }
        //Add the output format and output file
        parameters.add("ofmt=" + configBean.getOutputFormat().getStiltsName());
        parameters.add("out=" + outputFile);
        if (parameters == null){
            return null;
        }
        return parameters;
    }

    /**
     * Does the actual Stilts execution based on the provided parameters.
     * 
     * Captures the standard error stream.
     * Records the information captured in Standard error to the error port, or an exception if applicable. 
     * @param callback
     * @param parameters
     * @param outputs
     * @return True if and only if the Stilts run ended in the expected way.
     */
    private boolean doRun(final AsynchronousActivityCallback callback, String[] parameters, Map<String, T2Reference> outputs) {
        //Writes the paramteres to Standard out.
        toSystemOut(parameters);
        
        //Reoute Stadard.err to be caputred
        StreamRerouter rerouter;
        try {
            rerouter = new StreamRerouter();
        } catch (IOException ex) {
            callback.fail("Error rerouting system streams  ", ex);
            return false;
        }       
        Thread rerouterThread = new Thread(rerouter);
        rerouterThread.start();
        
        //Runs Stilts in a different thread
        //This may not be necessary as executeAsynch but it works
        StiltsRunner runner = new StiltsRunner(rerouter, parameters);
        Thread runnerThread = new Thread(runner);
        runnerThread.start();
        try {
            runnerThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        if (configBean.isDebugMode()){
            //Records the information captured from Standard.err
            writeOutput(callback, outputs, rerouter.getSavedErr(), ERROR_PARAMETER_NAME);
        }
        if (rerouter.getRunStatus() != RunStatus.SUCCESS){
            if (!configBean.isDebugMode()){
                callback.fail("Error running Stilts " + rerouter.getSavedErr());
            }
            //Unable to call callback.fail as thaat would stop the workflow from reporting the parameteres and  Standard.err port
            return false;
        }
        System.out.println("Run done "+ rerouter.getRunStatus());
        return true;
    }

    /**
     * Adds the output table either as a file path or a String
     * @param callback
     * @param outputs
     * @param outputFile File Stilts may have written to 
     * @param runSuccessfull Record of the status of the Stilts run
     */
    private void addResults(final AsynchronousActivityCallback callback, 
            Map<String, T2Reference> outputs, File outputFile, boolean runSuccessfull) {
        if (runSuccessfull){
            String outputValue = null;
            switch (configBean.getOutputType()){
                case FILE:
                   outputValue = outputFile.getAbsolutePath();
                    break;
                case STRING:
                    outputValue = readFile(callback, outputFile);
                    break;
                default:
                    callback.fail("Unexpected Output type: " + configBean.getOutputType());
            }
            if (outputValue == null){
                return;
            }
            writeOutput(callback, outputs, outputValue, OUTPUT_TABLE_PARAMETER_NAME);						
            callback.receiveResult(outputs, new int[0]);
        } else if (configBean.isDebugMode()){
            //We still need to put something in output
            writeOutput(callback, outputs, FAILED_MESSAGE, OUTPUT_TABLE_PARAMETER_NAME);						            
            //We still pass the result as the service is not failing
            callback.receiveResult(outputs, new int[0]);
        }
        //else do nothing as callback fail already called.
    }

    /**
     * Creates the process and input parameters based on the bean.
     * 
     * The first parameter is the Stilts name of the chosen process.
     * Then any process specific specific parameters are added.
     * This is followed by adding the input parameters.
     * @param processBean
     * @param inputs
     * @param callback
     * @return A List of parameters or null if Callback fail was called
     */
    private List<String> createProcessParameters(StiltsProcessBean processBean,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback){
        List<String> parameters = new ArrayList<String>();
        parameters.add(processBean.retrieveStilsCommand());
        if (processBean instanceof TMatch2Bean){
            if (!addTMatch2Parameters((TMatch2Bean)processBean, parameters, inputs, callback)){
                return null; //Callback fail already called
            }
        }
        //Add here if any new process added specific paramters other than inputs
        
        //Obtain the input type and add the appropriate paramters
        StitlsInputsBean inputBean = processBean.getInputs();
        boolean ok = addInputParameters(inputBean, parameters, inputs, callback);
        if (!ok) {
            return null; //callback.fail already called
        } else {
            return parameters;
        }
    }

    //InputPorts methods
    
    /**
     * Adds input ports for each table expected as input
     * @param inputBean 
     */
    private void configureInputPorts(StitlsInputsBean inputBean) {
        if (inputBean instanceof SingleInputBean){
            addInput(INPUT_TABLE_PARAMETER_NAME, 0, true, null, String.class);
        } else if (inputBean instanceof MultipleInputsBean){
            for (int i = 1; i <= ((MultipleInputsBean)inputBean).retreiveNumberOfInputs(); i++){
                addInput(inputTableParameter(i), 0, true, null, String.class);
            }
        } else{
            //Not expected but just to be sure.
            throw new UnsupportedOperationException("Unexpected input bean class: " + inputBean.getClass());
        }
    }

    /**
     * Adds the input table parameters based on types of input expected.
     * 
     * @param inputBean
     * @param parameters
     * @param inputs
     * @param callback
     * @return flag to say the adding of parameters went ok or failed.
     */
    private boolean addInputParameters(StitlsInputsBean inputBean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        if (inputBean instanceof SingleInputBean){
            return addSingleInputParameters((SingleInputBean)inputBean, parameters, inputs, callback);
        } else if (inputBean instanceof SingleFormatMultipleInputsBean){
            return addSingleFormatMultipleInputsParameters((SingleFormatMultipleInputsBean)inputBean, parameters, inputs, callback);
        } else if (inputBean instanceof MultipleFormatsBean){
            return addMultipleFormatsParameters((MultipleFormatsBean)inputBean, parameters, inputs, callback);
        } else {
            callback.fail("Unexpected Bean class + " + inputBean.getClass());
            return FAILED;
        }
    }
    
    /**
     * Adds the path and type of the input tables to the parameters
     * 
     * @param inputBean
     * @param parameters
     * @param inputs
     * @param callback
     * @return flag to say the adding of parameters went ok or failed.
     */
    private boolean addSingleInputParameters(SingleInputBean inputBean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        String input = this.getValueFromInputs(inputs, callback, INPUT_TABLE_PARAMETER_NAME, REQUIRED_PARAMETER ); 
        StiltsInputType type = inputBean.retreiveStiltsInputType();
        String inputPath = getInputFilePath(callback, type, input);
        if (inputPath == null){
            return FAILED;
        }
        parameters.add("ifmt="+ inputBean.getFormatOfInput());
        parameters.add("in=" + inputPath);
        
        return OK;
    }
    
    //Multiple Input Beand 

    /**
     * Adds the paths for each input table and the format for all input tables to the parameters
     * @param inputBean
     * @param parameters
     * @param inputs
     * @param callback
     * @return flag to say the adding of parameters went ok or failed.
     */
    private boolean addSingleFormatMultipleInputsParameters(SingleFormatMultipleInputsBean inputBean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        for (int i = 1; i <= inputBean.retreiveNumberOfInputs(); i++){
            String input = this.getValueFromInputs(inputs, callback, inputTableParameter(i), REQUIRED_PARAMETER ); 
            String inputPath = getInputFilePath(callback, types.get(i-1), input);
            if (inputPath == null){
                return FAILED;
            }
            parameters.add("in=" + inputPath);
        }
        parameters.add("ifmt="+ inputBean.getFormatOfInputs());      
        return OK;
    }
         
    /**
     * Adds the path and format for each input table to the list of parameters
     * @param inputBean
     * @param parameters
     * @param inputs
     * @param callback
     * @return flag to say the adding of parameters went ok or failed.
     */
    private boolean addMultipleFormatsParameters(MultipleFormatsBean inputBean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        if (inputBean instanceof FlexibleInputsBean){
            parameters.add("nin=" + inputBean.retreiveNumberOfInputs());
        } else if (inputBean instanceof TwoInputsBean){
            //not required
        } else {
            callback.fail("Unexpected Bean class + " + inputBean.getClass());
            return FAILED;           
        }
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        List<StiltsInputFormat> formats = inputBean.getFormatsOfInputs();
        for (int i = 1; i <= inputBean.retreiveNumberOfInputs(); i++){
            String input = this.getValueFromInputs(inputs, callback, inputTableParameter(i), REQUIRED_PARAMETER ); 
            String inputPath = getInputFilePath(callback, types.get(i-1), input);
            if (inputPath == null){
                return FAILED;
            }
            parameters.add("in" + i + "=" + inputPath);
            parameters.add("ifmt" + i + "="+ formats.get(i-1).getStiltsName());      
        }
        return OK;
    }

    //TMatch
    
    /**
     * Checks the type of matching being done and adds the required ports.
     * 
     * Currently only the Exact Match options has been added here 
     * as most of the other matching types are specific to astronomical data.
     * 
     * if other matching types are added this method will need extending.
     * @param tMatch2Bean 
     */
    private void configureTMatch2Ports(TMatch2Bean tMatch2Bean) {
        if (tMatch2Bean instanceof ExactMatchBean){
            configureExactMatchPorts((ExactMatchBean)tMatch2Bean);
        } else {
            //Oops another matching type added.
            throw new UnsupportedOperationException("Unexpected tMatch2 bean class: " + tMatch2Bean);
        }
    }
    
    /**
     * Adds the parameters for a Tmatch
     * @param tMatch2Bean
     * @param parameters
     * @param inputs
     * @param callback
     * @return flag to say the adding of parameters went ok or failed.
     */
    private boolean addTMatch2Parameters(TMatch2Bean tMatch2Bean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        parameters.add("find=" + tMatch2Bean.getFindValue().getStiltsName());
        parameters.add("fixcols=" + tMatch2Bean.getFixcolsValue().getStiltsName());
        parameters.add("join=" + tMatch2Bean.getJoinValue().getStiltsName());
        //As Stilts support astronomical data matching as well
        if (tMatch2Bean instanceof ExactMatchBean){
            return addExactMatchParameters((ExactMatchBean)tMatch2Bean, parameters, inputs, callback);
        } else {
            //Oops need to add code for the new matching type
            callback.fail("Unexpected Bean class + " + tMatch2Bean.getClass());
            return FAILED;
        }
    }

    /**
     * Adds input ports for name of the columns to match on.
     * 
     * @param exactMatchBean 
     */
    private void configureExactMatchPorts(ExactMatchBean exactMatchBean) {
        for (int table = 1; table <= 2; table++){
            for (int column = 1; column <= exactMatchBean.getNumbertOfColumnsToMatch(); column++){
                addInput(getMatchColumnName(table, column), 0, true, null, String.class);
            }
        }
    }

    /**
     * Adds the exact match parameters which includes the columns to match on.
     * 
     * @param exactMatchBean
     * @param parameters
     * @param inputs
     * @param callback
     * @return flag to say the adding of parameters went ok or failed.
     */
    private boolean addExactMatchParameters(ExactMatchBean exactMatchBean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        String matcher = "matcher=exact";
        //Add an extra +exact for each eaxtra column (more than 1)
        for (int column = 1; column <  exactMatchBean.getNumbertOfColumnsToMatch(); column++){
            matcher+= "+exact";
        }
        parameters.add(matcher);
        for (int table = 1; table <= 2; table++){
            String values = "values" + table + "=";
            for (int column = 1; column <= exactMatchBean.getNumbertOfColumnsToMatch(); column++){
                String columnName = getValueFromInputs(inputs, callback, getMatchColumnName(table, column), REQUIRED_PARAMETER);
                if (columnName == null){ //getFailed
                    return FAILED; //callback.fail(.. aready called            
                }
                values+=columnName + " ";
            }
            parameters.add(values);
        } 
        return OK;
    }
    
   //Support methods
    /**
     * Creates a temporary file or calls callback.fail if creation fails.
     * @param callback
     * @return A file unless callback.fail called.
     */
    private File createOutputFile(final AsynchronousActivityCallback callback) {
        try {
            return File.createTempFile("Stilts", ".txt");
        } catch (IOException ex) {
            callback.fail("Error preparing outputFile: ", ex);
            return null;
        }
    }

    /**
     * Obtains a String parameter from an input port or calls callback, fail if there is a problem.
     * 
     * The purpose of this method is to provide better feedback if the correct input port is not found
     * or if that port does not have a value.
     * @param inputs
     * @param callback
     * @param parameterName name of the input port to obtain the value from
     * @param required Specifies if this input port must contain a value.
     * @return The value obtained or null if the parameter was not required or if callback.fail was called
     */
    private final String getValueFromInputs(final Map<String, T2Reference> inputs, 
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
    
    /**
     * Converts a String to a T2Reference and adds it to the outputs.
     * 
     * Calls callback.fail if there is a problem, specifically the required port not being found.
     * @param callback
     * @param outputs
     * @param value Value to convert and add
     * @param parameterName Name of the output port
     * @return True if and only if the putout was added. Otherwise callback.fail will have been called
     */
    private boolean writeOutput(final AsynchronousActivityCallback callback, 
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
        T2Reference outputRef = referenceService.register(value, 0, true, context);
        outputs.put(parameterName, outputRef);                        
        return true;
    }
    
    /**
     * Check the input and if requires converts it to a file.
     * 
     * If the input is expected to be a File it check the file exists and returns the absolute path, 
     * even if the original String was a relative path.
     * 
     * If the input is expected to be a String, it creates a temporary file, 
     * fills it with the String and returns the absolute path.
     * 
     * If the input is expected to be a URI it checks if the URL is well format by creating a java.net.URI.
     * While currently not done this would be the place to add checking the URI resolves.
     * 
     * Calls callback.fail if an error was detected.
     * 
     * @param callback
     * @param type The type of input Expected
     * @param input String representation of that input
     * @return Check String or null if callback.fail was called.
     */
    private final String getInputFilePath(final AsynchronousActivityCallback callback,
            StiltsInputType type, String input) {
        if (type == null){
            callback.fail("Unexpected type null.");
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
        switch (type){
            case FILE:
                File test = new File(input);
                    if (test.exists()){
                        return test.getAbsolutePath();
                    } else {
                        callback.fail("Unable to locate file at " + test.getAbsolutePath());
                        return null;
                    }
            case STRING:
                try {
                    return MyUtils.writeStringAsTmpFile(input).getAbsolutePath();
                } catch (IOException ex) {
                    callback.fail("Error writing input to tempFile.", ex);
                    return null;           
                }
            case URL:
                try {
                    URI exampleUri = new URI(input);
                    return input;
                } catch (URISyntaxException e) {
                    callback.fail("Invalid URL: "+ input,e);
                    return null;
                }
            default:
                callback.fail("Unexpected input type: " + type);
                return null;
       }
    }
 
    /**
     * Helper method to print an arraay of String to Standard.out
     * @param parameters 
     */
    private void toSystemOut(String[] parameters){
        for (int i = 0; i< parameters.length; i++){
            System.out.println(parameters[i]);
        }
    }

    /**
     * Reads the data from a file.
     * 
     * Calls callback.fail if an error was detected.
     * 
     * @param callback
     * @param file Expected location of the data
     * @return The contents of the File or null if callback.fail was called
     */
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

    //Naming methods 
    /**
     * Simple method to keep the parameter name the same everywhere.
     * @param table number of the table
     * @return parameter name for this table
     */
    public static String inputTableParameter(int table){
        return "Input Table " + table;
    }

    /**
     * Simple method to keep the parameter name the same everywhere.
     * @param table number of the table
     * @param matchPosition number of the column in that table
     * @return parameter name for this column
     */
        public static String getMatchColumnName(int table, int matchPosition){     
        return "Name of " + MyUtils.ordinal(matchPosition) + " column to match in " + MyUtils.ordinal(table) + " Table "; 
    }

    public String getTitle() {
        try{
            return configBean.title();
        } catch (Exception ex){
            return ex.getMessage();
        }
    }
    
    public AllConfigurations configurations() {
        return configBean.configurations();
    }

    public void checkConfiguration(AllConfigurations newConfiguration) throws ActivityConfigurationException{ 
        configBean.checkConfiguration(newConfiguration);
    }
}
