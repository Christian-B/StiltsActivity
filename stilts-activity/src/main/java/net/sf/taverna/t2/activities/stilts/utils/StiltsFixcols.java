package net.sf.taverna.t2.activities.stilts.utils;

/**
 * Determines how input columns are renamed before use in the output table
 * 
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/tmatch2-usage.html
 * @author christian
 */
public enum StiltsFixcols implements DescribableInterface{

    NONE("none","columns are never renamed","columns are not renamed"),
    DUPS("dups","rename duplicates only",
            "columns which would otherwise have duplicate names in the output will be renamed to indicate which table they came from"),
    ALL("all","rename all columns",
            "all columns will be renamed to indicate which table they came from");

    private final String stiltsName;
    private final String userName;
    private final String description;

    StiltsFixcols(String stiltsName, String userName, String description){
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
    
    public static StiltsFixcols byStiltsName(String stiltsName){
        for (StiltsFixcols format:StiltsFixcols.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsInputFormat known for " + stiltsName);
    }
    
    @Override
    public String toString(){
        return userName;
    }    
}
