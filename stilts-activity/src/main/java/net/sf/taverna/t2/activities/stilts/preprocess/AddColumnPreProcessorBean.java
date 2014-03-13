package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class AddColumnPreProcessorBean extends StiltsPreProcessBean{
    
    //Note at least one of beforeColumn or afterColumn must be null
    private String beforeColumn;
    private String afterColumn;
    //New Name must be specified.
    private String newColName;

    AddColumnPreProcessorBean(){  
    }

    AddColumnPreProcessorBean(String beforeColumn, String afterColumn, String newColName){  
        this.beforeColumn = beforeColumn;
        this.afterColumn = afterColumn;
        this.newColName = newColName;
    }

    /**
     * @return the beforeColumn
     */
    public String getBeforeColumn() {
        return beforeColumn;
    }

    /**
     * @param beforeColumn the beforeColumn to set
     */
    public void setBeforeColumn(String beforeColumn) {
        this.beforeColumn = beforeColumn;
    }

    /**
     * @return the afterColumn
     */
    public String getAfterColumn() {
        return afterColumn;
    }

    /**
     * @param afterColumn the afterColumn to set
     */
    public void setAfterColumn(String afterColumn) {
        this.afterColumn = afterColumn;
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
    public void checkValid() throws ActivityConfigurationException {
        if (newColName == null){
            throw new ActivityConfigurationException("New column name not specified");
        }
        if (newColName.trim().isEmpty()){
            throw new ActivityConfigurationException("New column name is empty");
        }
        if (beforeColumn != null && afterColumn != null){
            throw new ActivityConfigurationException("Both before Column and after column specified.");            
        }
    }
 
}
