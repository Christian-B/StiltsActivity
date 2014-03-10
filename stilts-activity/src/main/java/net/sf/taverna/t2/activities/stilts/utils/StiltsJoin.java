package net.sf.taverna.t2.activities.stilts.utils;

/**
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/tmatch2-usage.html
 * @author christian
 */
public enum StiltsJoin implements DescribableInterface{

    ONE_AND_TWO("1and2","An output row for each row represented in both input tables"),
    ONE_OR_TWO("1or2","An output row for each row represented in either or both of the input tables"),
    ALL_ONE("all1","An output row for each matched or unmatched row in table 1"),
    ALL_TWO("all2","An output row for each matched or unmatched row in table 2"),
    ONE_NOT_TWO("1not2","An output row only for rows which appear in the first table but are not matched in the second table"),
    TWO_NOT_ONE("2not1","An output row only for rows which appear in the second table but are not matched in the first table"),
    ONE_XOR_TWO("1xor2","An output row only for rows represented in one of the input tables but not the other one");

    private final String stiltsName;
    private final String description;

    StiltsJoin(String stiltsName, String description){
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
    
    public static StiltsJoin byStiltsName(String stiltsName){
        for (StiltsJoin format:StiltsJoin.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsInputFormat known for " + stiltsName);
    }
}
