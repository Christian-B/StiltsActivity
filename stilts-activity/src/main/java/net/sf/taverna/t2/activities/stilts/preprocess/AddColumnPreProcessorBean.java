package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */public class AddColumnPreProcessorBean extends StiltsPreProcessBean{
    
    private StiltsLocationType newColumnLocation;
    private String locationColumn;
    //New Name must be specified.
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

}
