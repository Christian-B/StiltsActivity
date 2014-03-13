package net.sf.taverna.t2.activities.stilts.utils;

/**
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/sec9.4.html
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/uk.ac.starlink.ttools.func.Arithmetic.html
 * @author christian
 */
public enum StiltsOneVariableOperator implements DescribableInterface {

    FLOAT("float","Convert a numerical column to floats", StiltsOperatorType.CONVERSION),
    ROUNDUP("roundUp",
            "Rounds a value up to an integer value. Formally, returns the smallest (closest to negative infinity) integer value that is not less than the argument.",
            StiltsOperatorType.FUNCTION);

    private final String stiltsSymbol;
    private final String description;
    private final StiltsOperatorType operatorType;

    StiltsOneVariableOperator(String stiltsSymbol, String description, StiltsOperatorType operatorType){
        this.stiltsSymbol = stiltsSymbol;  
        this.description = description;
        this.operatorType = operatorType;
    }

    /**
     * @return the stiltsSymbol
     */
    public String getStiltsSymbol() {
        return stiltsSymbol;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the operatorType
     */
    public StiltsOperatorType getOperatorType() {
        return operatorType;
    }

    public int numberOfVariables(){
        return 1;
    }
}
