package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public abstract class ColumnListPreProcessorBean extends StiltsPreProcessBean{
    
    private String columnList;

    /**
     * Serialization constructor
     */
    ColumnListPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param columnList 
     */
    ColumnListPreProcessorBean(String columnList){  
        this.columnList = columnList;
    }

     @Override
    public void checkValid() throws ActivityConfigurationException {
        if (getColumnList() == null){
            throw new ActivityConfigurationException("Column not specified");
        }
        if (getColumnList().trim().isEmpty()){
            throw new ActivityConfigurationException("Column is empty");
        }
    }

    /**
    * @return the columnList
    */
    public String getColumnList() {
        return columnList;
    }

    /**
     * @param columnList the columnList to set
     */
    public void setColumnList(String columnList) {
        this.columnList = columnList;
    }
      
}
