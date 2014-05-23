package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.ConfigurationUtils;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Preprocess that selects columns based on the user command, deleting any others.
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the select expression.
 * <p>
 * Based on
 *{@link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#select}
 * <p>
 * The command must be a valid Stilts command returning a boolean
 * {@linkhttp://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#jel}
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class SelectByCommandPreProcessorBean extends StiltsPreProcessBean{
    
    /**
     * A valid boolean Stilts command
     */
    private String command;
    private String COMMAND_NAME = "Add Column Command";

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
    
    @Override
    public String title() {
        return "Select rows based on a Stils command";
    }

    @Override
    public List<StiltsConfiguration> configurations() {
        ArrayList<StiltsConfiguration> configurations = new ArrayList<StiltsConfiguration>();
        configurations.add(new StiltsConfiguration (COMMAND_NAME,  command, true));
        return configurations;        
    }
    
    public void checkConfiguration(List<StiltsConfiguration> newConfigurations) throws ActivityConfigurationException{ 
        ConfigurationUtils.checkString(newConfigurations, COMMAND_NAME);
    }

}
