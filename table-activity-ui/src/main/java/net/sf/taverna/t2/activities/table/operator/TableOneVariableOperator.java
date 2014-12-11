package net.sf.taverna.t2.activities.table.operator;

import net.sf.taverna.t2.activities.table.utils.DescribableInterface;

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
public enum TableOneVariableOperator implements DescribableInterface {

    BYTE("byte","Convert a numerical column to a byte", TableOperatorType.CONVERSION),
    SHORT("short","Convert a numerical column to a short", TableOperatorType.CONVERSION),
    INT("int","Convert a numerical column to an int", TableOperatorType.CONVERSION),
    LONG("long","Convert a numerical column to a long", TableOperatorType.CONVERSION),
    FLOAT("float","Convert a numerical column to a float", TableOperatorType.CONVERSION),
    DOUBLE("double","Convert a numerical column to a double", TableOperatorType.CONVERSION),
    ROUNDUP("roundUp",
            "Rounds a value up to an integer value.\n"
            + "Formally, returns the smallest (closest to negative infinity) integer value that is not less than the argument.",
           TableOperatorType.FUNCTION),
    ROUNDDOWN("roundDown",
            "Rounds a value down to an integer value.\n"
            + " Formally, returns the largest (closest to positive infinity)\n"
            + " integer value that is not greater than the argument.",
            TableOperatorType.FUNCTION),
    ROUND("round",
            "Rounds a value to the nearest integer. Formally,\n"
            + "returns the integer that is closest in value to the argument.\n"
            + "If two integers are equally close, the result is the even one.",
            TableOperatorType.FUNCTION),
    ABS("abs",
            "Returns the absolute value of a numerical value.\n"
            + " If the argument is not negative, the argument is returned.\n"
            + " If the argument is negative, the negation of the argument is returned.",
            TableOperatorType.FUNCTION),
    BITWISE_AND("&",
            "Bitwise and",
            TableOperatorType.OPERATOR),
    BITWISE_OR("|",
            "Bitwise or",
            TableOperatorType.OPERATOR),
    LEFT_SHITF("<<",
            "Bitwise left shift",
            TableOperatorType.OPERATOR),
    RIGHT_SHIFT(">>",
            "Bitwise right shift",
            TableOperatorType.OPERATOR),
    LOGICAL_RIGHT_SHIFT(">>>",
            "Bitwise logical right shift",
            TableOperatorType.OPERATOR);
 
    /**
     * Stilts representation of this operator.
     */
    private final String stiltsSymbol;
    /**
     * String to be shown in toolstips and other help locations
     */
    private final String description;
    private final TableOperatorType operatorType;

    TableOneVariableOperator(String stiltsSymbol, String description, TableOperatorType operatorType){
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
    public TableOperatorType getOperatorType() {
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
