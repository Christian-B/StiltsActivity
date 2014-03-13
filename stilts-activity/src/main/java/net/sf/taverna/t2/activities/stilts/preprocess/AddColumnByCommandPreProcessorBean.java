package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class AddColumnByCommandPreProcessorBean extends AddColumnPreProcessorBean{
    
    private String command;

    public AddColumnByCommandPreProcessorBean(){  
    }

    public AddColumnByCommandPreProcessorBean(String command, String beforeColumn, String afterColumn, String newColName){ 
        super(beforeColumn, afterColumn, newColName);
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
    public void checkValid() throws ActivityConfigurationException {
        if (command == null){
            throw new ActivityConfigurationException("Command not specified");
        }
        if (command.trim().isEmpty()){
            throw new ActivityConfigurationException("Command is empty");
        }
    }

}
