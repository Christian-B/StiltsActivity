package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public abstract class ColumnListPreProcessorBean extends StiltsPreProcessBean{
    
    private String columnList;

    ColumnListPreProcessorBean(){  
    }

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
