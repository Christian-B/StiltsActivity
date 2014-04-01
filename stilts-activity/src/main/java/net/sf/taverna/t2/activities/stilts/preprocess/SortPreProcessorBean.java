package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class SortPreProcessorBean extends ColumnListPreProcessorBean{
    
    private boolean ascending = false;
    private boolean nullsAtBegining = false;
    
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

      
}
