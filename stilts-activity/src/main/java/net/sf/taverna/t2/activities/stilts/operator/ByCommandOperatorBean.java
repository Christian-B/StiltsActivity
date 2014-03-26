package net.sf.taverna.t2.activities.stilts.operator;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Note: This was was an idea to split Operator from Preprocessor but is not currently being used.
 * 
 * @author christian
 */
public class ByCommandOperatorBean implements StiltsOperatorBean{
    
    private String command;

    public ByCommandOperatorBean(){  
    }

    public ByCommandOperatorBean(String command){ 
        super();
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
        return "\"" + command + "\"";
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
