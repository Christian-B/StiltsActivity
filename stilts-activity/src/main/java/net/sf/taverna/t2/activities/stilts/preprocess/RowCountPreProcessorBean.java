package net.sf.taverna.t2.activities.stilts.preprocess;

import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.utils.ConfigurationUtils;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Based Class for all the PreProcesses that are applied to a number of rows
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the columns to operate on.
 * <p>
 * Super classes will define what to do with the number of rows.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class RowCountPreProcessorBean extends StiltsPreProcessBean{
    
    /**
     * Number of rows to operate on.
     */
    private int numberOfRows;
    private final String NUMBER_OF_ROWS_NAME = "Number of rows";

    /**
     * Serialization constructor
     */
    RowCountPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param rowCount 
     */
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

          @Override
    public List<StiltsConfiguration> configurations() {
        ArrayList<StiltsConfiguration> configurations = new ArrayList<StiltsConfiguration>();
        configurations.add(new StiltsConfiguration (NUMBER_OF_ROWS_NAME,  numberOfRows, true));
        return configurations;        
    }

    public void checkConfiguration(List<StiltsConfiguration> newConfigurations) throws ActivityConfigurationException{ 
        ConfigurationUtils.checkPositiveInteger(newConfigurations, NUMBER_OF_ROWS_NAME);
    }

}
