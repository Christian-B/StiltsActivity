package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public abstract class RowCountPreProcessorBean extends StiltsPreProcessBean{
    
    private int numberOfRows;

    RowCountPreProcessorBean(){  
    }

    RowCountPreProcessorBean(int rowCount){  
        this.numberOfRows = rowCount;
    }

     @Override
    public void checkValid() throws ActivityConfigurationException {
        if (getNumberOfRows() <= 0){
            throw new ActivityConfigurationException("Number of Rows must be positive.");
        }
    }

    /**
     * @return the numberOfRows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @param numberOfRows the numberOfRows to set
     */
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

      
}
