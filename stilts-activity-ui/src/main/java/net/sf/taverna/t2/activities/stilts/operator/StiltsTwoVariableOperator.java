package net.sf.taverna.t2.activities.stilts.operator;

import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 * Operators that require a two variables.
 * <p>
 * Note: This is not a complete set of all operators offered by Stilts.
 * Adding a new operator here will automatically have it picked up in the activity and the Guis.
 * <p>
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/sec9.4.html
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/uk.ac.starlink.ttools.func.Arithmetic.html
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public enum StiltsTwoVariableOperator implements DescribableInterface {

    PLUS("+","Adds two numerical columns",StiltsOperatorType.OPERATOR, false),
    MINUS("-","Subtracts two numerical columns", StiltsOperatorType.OPERATOR, false),
    MULTIPLY("*","Multiply two numerical columns", StiltsOperatorType.OPERATOR, false),
    DIVIDE("/","Divide two numerical columns", StiltsOperatorType.OPERATOR, false),
    MODULUS("&","Modulus two numerical columns", StiltsOperatorType.OPERATOR, false),
    AND("&&","Boolean and", StiltsOperatorType.OPERATOR, true),
    OR("||","Boolean or", StiltsOperatorType.OPERATOR, true),
    XOR("^","Boolean XOR(exclusive-or)", StiltsOperatorType.OPERATOR, true),
    EQUALS("==","numeric identity", StiltsOperatorType.OPERATOR, true),
    NOT_EQUALS("!=","numeric non-identity", StiltsOperatorType.OPERATOR, true),
    LESS_THAN("<","less than", StiltsOperatorType.OPERATOR, true),
    GREATHER_THAN(">","greather than", StiltsOperatorType.OPERATOR, true),
    LESS_THAN_EQUALS("<=","less than or equals", StiltsOperatorType.OPERATOR, true),
    GREATHER_THAN_EQUALS(">=","greather than or equals", StiltsOperatorType.OPERATOR, true),
    //PLUS and STRING_CONATENATION share the same 
    STRING_CONATENATION("+","string concatenation",StiltsOperatorType.OPERATOR, false),
    MAX("max",
            "Returns the greater of two numerical values.\n"
                    + " If the arguments have the same value, the result is that same value..",
            StiltsOperatorType.FUNCTION, false),
    MAXREAL("maxReal",
            "Returns the greater of two floating point values, ignoring blanks.\n"
            + "If the arguments have the same value, the result is that same value.\n"
            + " If one argument is blank, the result is the other one.\n"
            + " If both arguments are blank, the result is blank.",
            StiltsOperatorType.FUNCTION, false),
    MIN("min",
            "Returns the smaller of two integer values.\n"
            + "If the arguments have the same value, the result is that same value.",
            StiltsOperatorType.FUNCTION, false),
    MINREAL("minReal",
            "Returns the smaller of two floating point values, ignoring blanks.\n"
            + "If the arguments have the same value, the result is that same value.\n"
            + "If one argument is blank, the result is the other one.\n"
            + "If both arguments are blank, the result is blank.",
            StiltsOperatorType.FUNCTION, false);

   /**
     * Stilts representation of this operator.
     */
    private final String stiltsSymbol;
    /**
     * String to be shown in toolstips and other help locations
     */
    private final String description;
    private final StiltsOperatorType operatorType;
    /**
     * Flag to say if operator always returns a boolean value.
     * <p>
     * A true value shows that the operators is normally expected to always return a boolean.
     * A false value shows that the operator will not typically return a boolean value,
     * although it is possible that in some case with some variables the result can used as a boolean.
     */
    private final boolean isBoolean;
  
    /**
     * A list of the Operators that always returns a boolean value.
     */
    private final static StiltsTwoVariableOperator[] booleanValues;
    
    /**
     * Precompute the list of booleanValues as it never chances.
     */
    static {
        int count = 0;
        for (StiltsTwoVariableOperator operator: values()){
            if (operator.isBoolean){
                count++;
            }
        }
        booleanValues = new StiltsTwoVariableOperator[count];
        count = 0;
        for (StiltsTwoVariableOperator operator: values()){
            if (operator.isBoolean){
                booleanValues[count]= operator;
                count++;
            }
        }
    }
    
    StiltsTwoVariableOperator(String stiltsSymbol, String description, StiltsOperatorType operatorType, boolean isBoolean){
        this.stiltsSymbol = stiltsSymbol;  
        this.description = description;
        this.operatorType = operatorType;
        this.isBoolean = isBoolean;
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
        return 2;
    }
    
        /**
     * Returns a String that can be used in a Stilts Parameter.
     * <p>
     * In all case the deciding factor is how Stilts expects the function.
     * @param variable1 any valid Stilts variable which can be complex (made up of other operators and variables)
     * @param variable2 any valid Stilts variable which can be complex (made up of other operators and variables)
     * @return 
     */
    public String retrieveStilsCommand(String variable1, String variable2){
        switch (getOperatorType()){
            case CONVERSION:
                throw new UnsupportedOperationException(getOperatorType()+ " not supported with two variables");
            case FUNCTION:
                return getStiltsSymbol() + "(" + variable1 + "," + variable2 + ")";
            case OPERATOR:
                return variable1 + getStiltsSymbol() + variable2;
            default: 
                throw new UnsupportedOperationException(getOperatorType()+ " not supported with two variables");
        }        
    }
    
    public String rereiveExample(){
        return retrieveStilsCommand("a", "b");
    }

    /**
      * 
     * @return Flag to say if operator always returns a boolean value.
     */
    public boolean isBoolean(){
        return isBoolean;
    }
    
    
    /**
     * Similar to values() except that only the boolean operators are included.
     *
     * @return A list of the Operators that always returns a boolean value.
     */
    public static StiltsTwoVariableOperator[] booleanValues(){
        return booleanValues;
    }
}
