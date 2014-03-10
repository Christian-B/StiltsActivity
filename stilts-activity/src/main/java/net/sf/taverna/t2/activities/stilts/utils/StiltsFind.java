package net.sf.taverna.t2.activities.stilts.utils;

/**
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/tmatch2-usage.html
 * @author christian
 */
public enum StiltsFind implements DescribableInterface {

    ALL("all","All matches. Every match between the two tables is included in the result. "
            + "Rows from both of the input tables may appear multiple times in the result."),
    BEST("best","Best match, symmetric. The best pairs are selected in a way which treats the two tables symmetrically. "
            + "Any input row which appears in one result pair is disqualified from appearing in any other result pair, "
            + "so each row from both input tables will appear in at most one row in the result."),
    BEST1("best1","Best match for each Table 1 row. For each row in table 1, "
            + "only the best match from table 2 will appear in the result. "
            + "Each row from table 1 will appear a maximum of once in the result, "
            + "but rows from table 2 may appear multiple times."),
    BEST2("best2","Best match for each Table 2 row. For each row in table 2, "
            + "only the best match from table 1 will appear in the result. "
            + "Each row from table 2 will appear a maximum of once in the result, "
            + "but rows from table 1 may appear multiple times.");

    private final String stiltsName;
    private final String description;

    StiltsFind(String stiltsName, String description){
        this.stiltsName = stiltsName;  
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
    
    public static StiltsFind byStiltsName(String stiltsName){
        for (StiltsFind format:StiltsFind.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsInputFormat known for " + stiltsName);
    }
}
