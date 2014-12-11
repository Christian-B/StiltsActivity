package net.sf.taverna.t2.activities.table.preprocess;

import java.util.ArrayList;
import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.utils.TableLocationType;
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
public class AddColumnPreProcessorBean extends AbstractPreProcessBean{
    
    /**
     * Defines where in comparison to the location Column the new column will be placed.
     */ 
    private TableLocationType newColumnLocation;
    private final String LOCATION_NAME = "How to place new column";
    /**
     * Name or number of the column to place the new column before or after.
     * 
     * This value will be ignored if newColumnLocation = END.
     * otherwise it is required.
     */
    private String locationColumn;
    private final String COLUMN_NAME = "Where to place new column";
    
    /**
     * Name for the new column which must be specified.
     */
    private String newColName;

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
    public AddColumnPreProcessorBean(){  
        this(null, "newColumn", TableLocationType.END, null);
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param newColName 
     */
    public AddColumnPreProcessorBean(String command, String newColName){
        this(command, newColName, TableLocationType.END, null);
    }

    public AddColumnPreProcessorBean(String command, String newColName, TableLocationType newColumnLocation,  String locationColumn){  
        this.newColumnLocation = newColumnLocation;
        this.locationColumn = locationColumn;        
        this.newColName = newColName;
        this.command = command;
    }

    /**
     * @return the newColName
     */
    public String getNewColName() {
        return newColName;
    }
  
    /**
     * @param newColName the newColName to set
     */
    public void setNewColName(String newColName) {
        this.newColName = newColName;
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
        String stiltsCommand  = "cmd=addcol ";
        switch (getNewColumnLocation()){
            case AFTER:
               return "cmd=addcol -after " + getLocationColumn() + " " + getNewColName() + " \"" + command + "\"";
            case BEFORE:
               return "cmd=addcol -before " + getLocationColumn() + " " + getNewColName() + " \"" + command + "\"";
            case END:
               return "cmd=addcol " + getNewColName() + " \"" + command + "\"";
            default:    
                throw new UnsupportedOperationException(getNewColumnLocation() + " not supported");
        }
    }
    
    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (getNewColName() == null){
            throw new ActivityConfigurationException("New column name not specified");
        }
        if (getNewColName().trim().isEmpty()){
            throw new ActivityConfigurationException("New column name is empty");
        }
        if (newColumnLocation == TableLocationType.END){
            //can safely ignore any 
        } else {
           if (locationColumn == null){
                throw new ActivityConfigurationException("New column location is " + newColumnLocation + " But no location column specified");
            }
            if (locationColumn.trim().isEmpty()){
                throw new ActivityConfigurationException("New column location is " + newColumnLocation + " But location column name is empty");
            }
        }
        if (command == null){
            throw new ActivityConfigurationException("Command not specified");
        }
        if (command.trim().isEmpty()){
            throw new ActivityConfigurationException("Command is empty");
        }
    }

    @Override
    public String title() {
        return "Add column";
    }

    /**
     * @return the newColumnLocation
     */
    public TableLocationType getNewColumnLocation() {
        return newColumnLocation;
    }

    /**
     * @param newColumnLocation the newColumnLocation to set
     */
    public void setNewColumnLocation(TableLocationType newColumnLocation) {
        this.newColumnLocation = newColumnLocation;
    }

    /**
     * @return the locationColumn
     */
    public String getLocationColumn() {
        return locationColumn;
    }

    /**
     * @param locationColumn the locationColumn to set
     */
    public void setLocationColumn(String locationColumn) {
        this.locationColumn = locationColumn;
    }

    @Override
    public List<TableParameterConfiguration> configurations() {
        ArrayList<TableParameterConfiguration> configurations = new ArrayList<TableParameterConfiguration>();
        configurations.add(new TableParameterConfiguration (newColName, "New Column Name"));
        configurations.add(new TableParameterConfiguration (LOCATION_NAME,  newColumnLocation));
        if (newColumnLocation != TableLocationType.END){
            configurations.add(new TableParameterConfiguration (COLUMN_NAME,  locationColumn));
        }
        configurations.add(new TableParameterConfiguration (COMMAND_NAME,  "\"" + command + "\""));
        return configurations;        
    }
    
}