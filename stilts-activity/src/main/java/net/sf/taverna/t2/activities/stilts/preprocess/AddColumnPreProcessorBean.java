package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;
import net.sf.taverna.t2.activities.stilts.configuration.ConfigurationUtils;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Based Class for all the Add Column PreProcesses.
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the name of the new colum, and where to place it.
 * <p>
 * Super classes will define what to put in the new column.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class AddColumnPreProcessorBean extends StiltsPreProcessBean{
    
    /**
     * Defines where in comparison to the location Column the new column will be placed.
     */ 
    private StiltsLocationType newColumnLocation;
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
     * Serialization constructor
     */
    AddColumnPreProcessorBean(){  
        newColumnLocation = StiltsLocationType.END;
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
    AddColumnPreProcessorBean(String newColName){  
        this.newColName = newColName;
        newColumnLocation = StiltsLocationType.END;
        locationColumn = null;
    }

    AddColumnPreProcessorBean(String newColName, StiltsLocationType newColumnLocation,  String locationColumn){  
        this.newColumnLocation = newColumnLocation;
        this.locationColumn = locationColumn;        
        this.newColName = newColName;
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

    @Override
    public String retrieveStilsCommand(){
        String command  = "cmd=addcol ";
        switch (getNewColumnLocation()){
            case AFTER:
               return "cmd=addcol -after " + getLocationColumn() + " " + getNewColName() + " ";
            case BEFORE:
               return "cmd=addcol -before " + getLocationColumn() + " " + getNewColName() + " ";
            case END:
               return "cmd=addcol " + getNewColName() + " ";
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
        if (newColumnLocation == StiltsLocationType.END){
            if (locationColumn != null){
                throw new ActivityConfigurationException("Location Column specified but location for new column is " + newColumnLocation); 
            }    
        } else {
           if (getNewColName() == null){
                throw new ActivityConfigurationException("New column location is " + newColumnLocation + " But no location column specified");
            }
            if (getNewColName().trim().isEmpty()){
                throw new ActivityConfigurationException("New column location is " + newColumnLocation + " But location column name is empty");
            }
        }
    }

    /**
     * @return the newColumnLocation
     */
    public StiltsLocationType getNewColumnLocation() {
        return newColumnLocation;
    }

    /**
     * @param newColumnLocation the newColumnLocation to set
     */
    public void setNewColumnLocation(StiltsLocationType newColumnLocation) {
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
    public List<StiltsConfiguration> configurations() {
        ArrayList<StiltsConfiguration> configurations = new ArrayList<StiltsConfiguration>();
        configurations.add(new StiltsConfiguration (COLUMN_NAME,  locationColumn, true));
        configurations.add(new StiltsConfiguration (LOCATION_NAME,  newColumnLocation, true));
        return configurations;        
    }
    
    public void checkConfiguration(List<StiltsConfiguration> newConfigurations) throws ActivityConfigurationException{ 
        ConfigurationUtils.checkString(newConfigurations, COLUMN_NAME);
        ConfigurationUtils.checkClass(newConfigurations, LOCATION_NAME, StiltsLocationType.class);
    }

}