package net.sf.taverna.t2.activities.stilts.utils;

/**
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/sec9.4.html
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/uk.ac.starlink.ttools.func.Arithmetic.html
 * @author christian
 */
public enum StiltsTwoVariableOperator implements DescribableInterface {

    PLUS("+","Adds two numerical columns",StiltsOperatorType.OPERATOR),
    MINUS("-","Subtracts two numerical columns", StiltsOperatorType.OPERATOR),
    MULTIPLY("*","Multiply two numerical columns", StiltsOperatorType.OPERATOR),
    DIVIDE("/","Divide two numerical columns", StiltsOperatorType.OPERATOR),
    MODULUS("&","Modulus two numerical columns", StiltsOperatorType.OPERATOR),
    AND("&&","Boolean and", StiltsOperatorType.OPERATOR),
    OR("||","Boolean or", StiltsOperatorType.OPERATOR),
    XOR("^","Boolean XOR(exclusive-or)", StiltsOperatorType.OPERATOR),
    EQUALS("==","numeric identity", StiltsOperatorType.OPERATOR),
    NOT_EQUALS("!=","numeric non-identity", StiltsOperatorType.OPERATOR),
    LESS_THAN("<","less than", StiltsOperatorType.OPERATOR),
    GREATHER_THAN(">","greather than", StiltsOperatorType.OPERATOR),
    LESS_THAN_EQUALS("<=","less than or equals", StiltsOperatorType.OPERATOR),
    GREATHER_THAN_EQUALS(">=","greather than or equals", StiltsOperatorType.OPERATOR),
    //PLUS and STRING_CONATENATION share the same 
    STRING_CONATENATION("+","string concatenation",StiltsOperatorType.OPERATOR),
    MAX("max",
            "Returns the greater of two numerical values. If the arguments have the same value, the result is that same value..",
            StiltsOperatorType.FUNCTION),
    MAXREAL("maxReal",
            "Returns the greater of two floating point values, ignoring blanks. If the arguments have the same value, the result is that same value. If one argument is blank, the result is the other one. If both arguments are blank, the result is blank.",
            StiltsOperatorType.FUNCTION),
    MIN("min",
            "Returns the smaller of two integer values. If the arguments have the same value, the result is that same value.",
            StiltsOperatorType.FUNCTION),
    MINREAL("minReal",
            "Returns the smaller of two floating point values, ignoring blanks. If the arguments have the same value, the result is that same value. If one argument is blank, the result is the other one. If both arguments are blank, the result is blank.",
            StiltsOperatorType.FUNCTION);

    private final String stiltsSymbol;
    private final String description;
    private final StiltsOperatorType operatorType;

    StiltsTwoVariableOperator(String stiltsSymbol, String description, StiltsOperatorType operatorType){
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
        return 2;
    }
    
    public String getCommand(String variable1, String variable2){
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
    
    public String getExample(){
        return getCommand("a", "b");
    }

}
