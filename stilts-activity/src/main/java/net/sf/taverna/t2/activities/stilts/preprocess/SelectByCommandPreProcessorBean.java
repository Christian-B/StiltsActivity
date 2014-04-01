package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class SelectByCommandPreProcessorBean extends StiltsPreProcessBean{
    
    private String command;

    /**
     * Serialization constructor
     */
    public SelectByCommandPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param command 
     */
    public SelectByCommandPreProcessorBean(String command){ 
        this.command = command;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String retrieveStilsCommand(){
        return "cmd=select \"" + command + "\"";
    }
            
    @Override    
    public void checkValid() throws ActivityConfigurationException {
        if (command == null){
            throw new ActivityConfigurationException("Command not specified");
        }
        if (command.trim().isEmpty()){
            throw new ActivityConfigurationException("Command is empty");
        }
    }

}
