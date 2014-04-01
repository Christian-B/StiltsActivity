package net.sf.taverna.t2.activities.stilts.operator;

import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 * Operators that require a single variable.
 * <p>
 * Note: This is not a complete set of all operators offered by Stilts.
 * Adding a new operator here will automatically have it picked up in the activity and the Guis.
 * <p>
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/sec9.4.html
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/uk.ac.starlink.ttools.func.Arithmetic.html
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public enum StiltsOneVariableOperator implements DescribableInterface {

    BYTE("byte","Convert a numerical column to a byte", StiltsOperatorType.CONVERSION),
    SHORT("short","Convert a numerical column to a short", StiltsOperatorType.CONVERSION),
    INT("int","Convert a numerical column to an int", StiltsOperatorType.CONVERSION),
    LONG("long","Convert a numerical column to a long", StiltsOperatorType.CONVERSION),
    FLOAT("float","Convert a numerical column to a float", StiltsOperatorType.CONVERSION),
    DOUBLE("double","Convert a numerical column to a double", StiltsOperatorType.CONVERSION),
    ROUNDUP("roundUp",
            "Rounds a value up to an integer value. Formally, returns the smallest (closest to negative infinity) integer value that is not less than the argument.",
           StiltsOperatorType.FUNCTION),
    ROUNDDOWN("roundDown",
            "Rounds a value down to an integer value. Formally, returns the largest (closest to positive infinity) integer value that is not greater than the argument.",
            StiltsOperatorType.FUNCTION),
    ROUND("round",
            "Rounds a value to the nearest integer. Formally, returns the integer that is closest in value to the argument. If two integers are equally close, the result is the even one.",
            StiltsOperatorType.FUNCTION),
    ABS("abs",
            "Returns the absolute value of a numerical value. If the argument is not negative, the argument is returned. If the argument is negative, the negation of the argument is returned.",
            StiltsOperatorType.FUNCTION),
    BITWISE_AND("&",
            "Bitwise and",
            StiltsOperatorType.OPERATOR),
    BITWISE_OR("|",
            "Bitwise or",
            StiltsOperatorType.OPERATOR),
    LEFT_SHITF("<<",
            "Bitwise left shift",
            StiltsOperatorType.OPERATOR),
    RIGHT_SHIFT(">>",
            "Bitwise right shift",
            StiltsOperatorType.OPERATOR),
    LOGICAL_RIGHT_SHIFT(">>>",
            "Bitwise logical right shift",
            StiltsOperatorType.OPERATOR);
 
    /**
     * Stilts representation of this operator.
     */
    private final String stiltsSymbol;
    /**
     * String to be shown in toolstips and other help locations
     */
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
    
    /**
     * Returns a String that can be used in a Stilts Parameter.
     * <p>
     * In all case the deciding factor is how Stilts expects the function.
     * @param variable any valid Stilts variable which can be complex (made up of other operators and variables)
     * @return 
     */
    public String retrieveStilsCommand(String variable){
        switch (getOperatorType()){
            case CONVERSION:
                return "(" + getStiltsSymbol() + ")" +  variable;
            case FUNCTION:
                return getStiltsSymbol() + "(" + variable + ")";
            case OPERATOR:
                return getStiltsSymbol() + variable;
            default: 
                throw new UnsupportedOperationException(getOperatorType()+ " not supported with two variables");
        }        
    }
    
    public String retrievExample(){
        return retrieveStilsCommand("x");
    }
}
