package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class DeleteColumnPreProcessorBean extends StiltsPreProcessBean{
    
    private String column;

    public DeleteColumnPreProcessorBean(){  
    }

    public DeleteColumnPreProcessorBean(String column){  
        this.column = column;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (column == null){
            throw new ActivityConfigurationException("Column not specified");
        }
        if (column.trim().isEmpty()){
            throw new ActivityConfigurationException("Column is empty");
        }
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=delcols \"" + column + "\"";
    }

      
}
