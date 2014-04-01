package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Bean to allow any Stilts preprocess/ cmd to be applied.
 * <p>
 * The workflow creator/ pluging configuration script will have to provide the full Stilts command.
 * 
 * @author christian
 */
public class UserSpecifiedPreProcessorBean extends StiltsPreProcessBean{
   
    /**
     * The whole Stilts parameter except for the "cmd=" part.
     * 
     * Must be in valid Stilts Syntax. 
     * @see http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#filterSteps
     * <p>
     * In theory this is the only PreProcessorBean required but it does require the user to know Stilts.
     */
    private String preProcessCommand;

    /**
     * Serialization constructor
     */
    public UserSpecifiedPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param preProcessCommand 
     */
    public UserSpecifiedPreProcessorBean(String preProcessCommand){  
        this.preProcessCommand = preProcessCommand;
    }

    /**
     * @return the preProcessCommand
     */
    public String getPreProcessCommand() {
        return preProcessCommand;
    }

    /**
     * @param preProcessCommand the preProcessCommand to set
     */
    public void setPreProcessCommand(String preProcessCommand) {
        this.preProcessCommand = preProcessCommand;
    }
    
    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (preProcessCommand == null){
            throw new ActivityConfigurationException("PreProcess Command not specified");
        }
        if (preProcessCommand.trim().isEmpty()){
            throw new ActivityConfigurationException("PreProcess Command is empty");
        }
    }

    @Override
    public String retrieveStilsCommand() {
        return  "cmd=" + preProcessCommand;
    }

}
