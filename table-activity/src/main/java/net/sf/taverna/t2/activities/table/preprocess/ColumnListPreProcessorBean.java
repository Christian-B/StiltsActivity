package net.sf.taverna.t2.activities.table.preprocess;

import java.util.ArrayList;
import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Based Class for all the PreProcesses that are applied to a list of columns
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the columns to operate on.
 * <p>
 * Column list specification must be in the Stilts format as shown at;
 * {@link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#colid-list}
 * <p>
 * Super classes will define what to do with the list of columns.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public abstract class ColumnListPreProcessorBean extends AbstractPreProcessBean{
    
    /**
     * List of columns to be operated on.
     * Must be in Stilts format.
     */
    private String columnList;
    private final String COLUMNS_NAME = "Columns";

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
      
    @Override
    public List<TableParameterConfiguration> configurations() {
        ArrayList<TableParameterConfiguration> configurations = new ArrayList<TableParameterConfiguration>();
        configurations.add(new TableParameterConfiguration (COLUMNS_NAME,  columnList));
        return configurations;        
    }

}
 