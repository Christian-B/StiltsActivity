package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
import net.sf.taverna.t2.activities.stilts.utils.ConfigurationUtils;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Full configurable Add Column PreProcessor.
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the name of the new colum, and where to place it. 
 * and will define what to put in the new column.
 * <p>
 * The rule of what to add is totally configurable and as such the responsibility of the user
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class AddColumnByCommandPreProcessorBean extends AddColumnPreProcessorBean{
    
    /**
     * Stilts command to create the new columns.
     * 
     * Not including cmd=addcol -after refColun and new columnName
     */
    private String command;
    private String COMMAND_NAME = "Add Column Command";

    /**
     * Serialization constructor
     */
    public AddColumnByCommandPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param command
     * @param newColName 
     */
    public AddColumnByCommandPreProcessorBean(String command, String newColName){ 
        super(newColName);
        this.command = command;
    }

    public AddColumnByCommandPreProcessorBean(String command, String newColName, StiltsLocationType newColumnLocation,  String locationColumn){ 
        super(newColName, newColumnLocation, locationColumn);
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
        return super.retrieveStilsCommand() + "\"" + command + "\"";
    }
            
    @Override    
    public void checkValid() throws ActivityConfigurationException {
        super.checkValid();
        if (command == null){
            throw new ActivityConfigurationException("Command not specified");
        }
        if (command.trim().isEmpty()){
            throw new ActivityConfigurationException("Command is empty");
        }
    }

    @Override
    public String title() {
        return "Add column using a Stilts command";
    }

    @Override
    public List<StiltsConfiguration> configurations() {
        List<StiltsConfiguration> configurations = super.configurations();
        configurations.add(new StiltsConfiguration (COMMAND_NAME,  command, true));
        return configurations;        
    }
    
    public void checkConfiguration(List<StiltsConfiguration> newConfigurations) throws ActivityConfigurationException{ 
        super.checkConfiguration(newConfigurations);
        ConfigurationUtils.checkString(newConfigurations, COMMAND_NAME);
    }

}
