package net.sf.taverna.t2.activities.table.utils;

/**
 * Determines how input columns are renamed before use in the output table
 * 
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/tmatch2-usage.html
 * @author christian
 */
public enum TableFixcols implements DescribableInterface{

    NONE("none","columns are never renamed","columns are not renamed"),
    DUPS("dups","rename duplicates only",
            "columns which would otherwise have duplicate names in the output will be renamed to indicate which table they came from"),
    ALL("all","rename all columns",
            "all columns will be renamed to indicate which table they came from");

    private final String stiltsName;
    private final String userName;
    private final String description;

    TableFixcols(String stiltsName, String userName, String description){
        this.stiltsName = stiltsName;  
        this.userName = userName;
        this.description = description;
    }

    /**
     * @return the stiltsName
     */
    public String getStiltsName() {
        return stiltsName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    public static TableFixcols byStiltsName(String stiltsName){
        for (TableFixcols format:TableFixcols.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No TableInputFormat known for " + stiltsName);
    }
    
    @Override
    public String toString(){
        return userName;
    }    
}
