package net.sf.taverna.t2.activities.table.preprocess;

import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Preprocess that sorts rows based on one or more columns
 * <p>
 * Semantic Sugar for {@link UserSpecifiedPreProcessorBean UserSpecifiedPreProcessorBean} 
 * as it helps the user specify the column(s) to sort by.
 * <p>
 * Based on
 *{@link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#sort}
 * 
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class SortPreProcessorBean extends ColumnListPreProcessorBean{
    
    /**
     * Specify if the data should be sorted in ascending order
     */
    private boolean ascending = false;
    private final String ASCENDING_NAME = "Sort Ascending";
    /** 
     * Determines that nulls should be placed at the start of the table.
     */
    private boolean nullsAtBegining = false;
    private final String NULLS_AT_BEGINING_NAME = "Sort Nulls at the beginning";
    
    /**
     * Serialization constructor
     */
    public SortPreProcessorBean(){  
    }

    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param columnList
     * @param ascending
     * @param nullsAtBegining 
     */
    public SortPreProcessorBean(String columnList, boolean ascending, boolean nullsAtBegining){  
        super(columnList);
        this.ascending = ascending;
        this.nullsAtBegining = nullsAtBegining;
    }

    @Override
    public String retrieveStilsCommand() {
        String command = "cmd=sort";
        if (isAscending()){
            command+= " -down";
        }
        if (!isNullsAtBegining()){
            //Note Stilts code is wrong as adding -nullFirst places then at the end
            command+= " -nullsfirst";
        }
        return  command + " \"" + this.getColumnList() + "\"";
    }

     /**
     * @return the nullsAtEnd
     */
    public boolean isNullsAtBegining() {
        return nullsAtBegining;
    }

    /**
     * @param nullsAtEnd the nullsAtEnd to set
     */
    public void setNullsAtBegining(boolean nullsAtBegining) {
        this.nullsAtBegining = nullsAtBegining;
    }

    /**
     * @return the ascending
     */
    public boolean isAscending() {
        return ascending;
    }

    /**
     * @param ascending the ascending to set
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public String title() {
        return "Sortt rows";
    }
 
    @Override
    public List<TableParameterConfiguration> configurations() {
        List<TableParameterConfiguration> configurations = super.configurations();
        configurations.add(new TableParameterConfiguration (ASCENDING_NAME,  ascending));
        configurations.add(new TableParameterConfiguration (NULLS_AT_BEGINING_NAME,  nullsAtBegining));
        return configurations;        
    }
    
}
 