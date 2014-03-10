package net.sf.taverna.t2.activities.stilts.test;

import net.sf.taverna.t2.activities.stilts.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.sf.taverna.t2.activities.stilts.ExactMatchActivity.getMatchColumnName;
import net.sf.taverna.t2.activities.stilts.utils.RunStatus;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsRunner;
import net.sf.taverna.t2.activities.stilts.utils.StreamRerouter;

import net.sf.taverna.t2.invocation.InvocationContext;
import net.sf.taverna.t2.reference.ReferenceService;
import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.AbstractAsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class StiltsActivity extends AbstractAsynchronousActivity<StiltsBean> { 

    //Output Port names
    static final String OUTPUT_TABLE_PARAMETER_NAME = "Output Table";
    static final String STILTS_PARAMETER_NAME = "Stilts Parameters";
    static final String ERROR_PARAMETER_NAME = "Stilts Errors";
    
    //Input Port Name
    static final String INPUT_TABLE_PARAMETER_NAME = "Input Table";

    //These booleans are used in methods call to flag if the parameter must be present
    static final boolean REQUIRED_PARAMETER = true;
    static final boolean OPTIONAL_PARAMETER = true;
    static final boolean FAILED = false;
    static final boolean OK = true;
    
    //messages
    public static final String FAILED_MESSAGE = "Run failed! check " + ERROR_PARAMETER_NAME + " for details.";
    
    protected StiltsBean configBean;

    @Override
    public final void configure(StiltsBean configBean) throws ActivityConfigurationException {
        configBean.checkValid();
        
        // Store for getConfiguration(), but you could also make
        // getConfiguration() return a new bean from other sources
        this.configBean = configBean;

        // REQUIRED: (Re)create inputPath/output ports depending on configuration
        configurePorts();
    }
    
    @Override
    public StiltsBean getConfiguration() {
        System.out.println(configBean);
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
    
    private void configurePorts() {
        // In case we are being reconfigured - remove existing ports first
        // to avoid duplicates
        removeInputs();
        removeOutputs();

        // Single value outputFile port (depth 0)
        addOutput(OUTPUT_TABLE_PARAMETER_NAME, 0);
        
        if (configBean.isDebugMode()){
            addOutput(STILTS_PARAMETER_NAME, 1);
            addOutput(ERROR_PARAMETER_NAME, 0);
        }
        configureProcessPorts(configBean.getProcess());
        //Add pre and post processing here later
    }
    
    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile){
        List<String> parameters = createProcessParameters(configBean.getProcess(), inputs, callback);
        if (parameters == null){ //createProcessParameters failed.
            return null;//callback.fail already done
        }
        parameters.add("ofmt=" + configBean.getOutputFormat().getStiltsName());
        parameters.add("out=" + outputFile);
        if (parameters == null){
            return null;
        }
        //Add pre and post processing here later
        return parameters;
    }

    private Map<String, T2Reference> prepareOutputs(final AsynchronousActivityCallback callback, String[] parameters) {
        Map<String, T2Reference> outputs = new HashMap<String, T2Reference>();
        if (configBean.isDebugMode()){
            InvocationContext context = callback.getContext();
            ReferenceService referenceService = context.getReferenceService();
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
            writeStringParameter(callback, outputs, outputValue, OUTPUT_TABLE_PARAMETER_NAME);						
            callback.receiveResult(outputs, new int[0]);
        } else if (configBean.isDebugMode()){
            //We still need to put something in output
            writeStringParameter(callback, outputs, FAILED_MESSAGE, OUTPUT_TABLE_PARAMETER_NAME);						            
            //We still pass the result as the service is not failing
            callback.receiveResult(outputs, new int[0]);
        }
        //else do nothing as callback fail already called.
    }

    private void configureProcessPorts(StiltsProcessBean processBean){
        if (processBean instanceof ExactMatchBean){
            configureTMatch2Ports((TMatch2Bean)processBean);
        }
        StitlsInputsBean inputBean = processBean.getInputs();
        configureInputPorts(inputBean);
    }

    private List<String> createProcessParameters(StiltsProcessBean processBean,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback){
        List<String> parameters = new ArrayList<String>();
        if (processBean instanceof TPipeBean){
            parameters.add("tpipe");
        } else if (processBean instanceof TCatBean){
            parameters.add("tcat");
        } else if (processBean instanceof TCatNBean){
            parameters.add("tcatn");
        } else if (processBean instanceof TJoinBean){
            parameters.add("tjoin");
        } else if (processBean instanceof TMatch2Bean){
            if (!addTMatch2Parameters((TMatch2Bean)processBean, parameters, inputs, callback)){
                return null; //Callback fail already called
            }
        } else {
            callback.fail("Unexpected process " + processBean.getClass());
            return null;
        }
        StitlsInputsBean inputBean = processBean.getInputs();
        boolean ok = addInputParameters(inputBean, parameters, inputs, callback);
        if (!ok) {
            return null;
        } else {
            return parameters;
        }
    }

    //InputPorts methods
    private void configureInputPorts(StitlsInputsBean inputBean) {
        if (inputBean instanceof SingleInputBean){
            configureSingleInputPorts((SingleInputBean)inputBean);
        } else if (inputBean instanceof MultipleInputsBean){
            configureMultipleInputsPorts((MultipleInputsBean)inputBean);
        } else{
            throw new UnsupportedOperationException("Unexpected input bean class: " + inputBean.getClass());
        }
    }

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
    
    
    //SingleInput Methods
    private void configureSingleInputPorts(SingleInputBean inputBean) {
        addInput(INPUT_TABLE_PARAMETER_NAME, 0, true, null, String.class);
    }

    private boolean addSingleInputParameters(SingleInputBean inputBean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        String input = this.getStringParameter(inputs, callback, INPUT_TABLE_PARAMETER_NAME, REQUIRED_PARAMETER ); 
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
    private void configureMultipleInputsPorts(MultipleInputsBean inputBean) {
        for (int i = 1; i <= inputBean.retreiveNumberOfInputs(); i++){
            addInput(inputTableParameter(i), 0, true, null, String.class);
        }
    }

    private boolean addSingleFormatMultipleInputsParameters(SingleFormatMultipleInputsBean inputBean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        for (int i = 1; i <= inputBean.retreiveNumberOfInputs(); i++){
            String input = this.getStringParameter(inputs, callback, inputTableParameter(i), REQUIRED_PARAMETER ); 
            String inputPath = getInputFilePath(callback, types.get(i-1), input);
            if (inputPath == null){
                return FAILED;
            }
            parameters.add("in=" + inputPath);
        }
        parameters.add("ifmt="+ inputBean.getFormatOfInputs());      
        return OK;
    }
           
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
            String input = this.getStringParameter(inputs, callback, inputTableParameter(i), REQUIRED_PARAMETER ); 
            String inputPath = getInputFilePath(callback, types.get(i-1), input);
            if (inputPath == null){
                return FAILED;
            }
            parameters.add("in" + i + "=" + inputPath);
            parameters.add("ifmt" + i + "="+ formats.get(i-1).getStiltsName());      
        }
        return OK;
    }

    //Merge
    private void configureTMatch2Ports(TMatch2Bean tMatch2Bean) {
        if (tMatch2Bean instanceof ExactMatchBean){
            configureExactMatchPorts((ExactMatchBean)tMatch2Bean);
        } else {
            throw new UnsupportedOperationException("Unexpected input bean class: " + tMatch2Bean);
        }
    }
    
    private boolean addTMatch2Parameters(TMatch2Bean tMatch2Bean, List<String> parameters,
            final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback) {
        parameters.add("tmatch2");
        parameters.add("find=" + tMatch2Bean.getFindValue().getStiltsName());
        parameters.add("fixcols=" + tMatch2Bean.getFixcolsValue().getStiltsName());
        parameters.add("join=" + tMatch2Bean.getJoinValue().getStiltsName());
        if (tMatch2Bean instanceof ExactMatchBean){
            return addExactMatchParameters((ExactMatchBean)tMatch2Bean, parameters, inputs, callback);
        } else {
            callback.fail("Unexpected Bean class + " + tMatch2Bean.getClass());
            return FAILED;
        }
    }

    private void configureExactMatchPorts(ExactMatchBean exactMatchBean) {
        for (int table = 1; table <= 2; table++){
            for (int column = 1; column <= exactMatchBean.getNumbertOfColumnsToMatch(); column++){
                addInput(getMatchColumnName(table, column), 0, true, null, String.class);
            }
        }
    }

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
                String columnName = getStringParameter(inputs, callback, getMatchColumnName(table, column), REQUIRED_PARAMETER);
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
    private File createOutputFile(final AsynchronousActivityCallback callback) {
        try {
            return File.createTempFile("Stilts", ".txt");
        } catch (IOException ex) {
            callback.fail("Error preparing outputFile: ", ex);
            return null;
        }
    }

    private final String getStringParameter(final Map<String, T2Reference> inputs, 
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
  
    private void toSystemOut(String[] parameters){
        for (int i = 0; i< parameters.length; i++){
            System.out.println(parameters[i]);
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

    //Naming methods 
    public static String inputTableParameter(int table){
        return "Input Table " + table;
    }

    public static String getMatchColumnName(int table, int matchPosition){     
        return "Name of " + MyUtils.ordinal(matchPosition) + " column to match in " + MyUtils.ordinal(table) + " Table "; 
    }
    


}
