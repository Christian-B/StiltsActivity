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
    MAX("max",
            "Returns the greater of two integer values. If the arguments have the same value, the result is that same value..",
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

}
