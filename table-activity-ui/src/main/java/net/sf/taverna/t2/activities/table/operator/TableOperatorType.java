package net.sf.taverna.t2.activities.table.operator;

/**
 * The enum allow operators to be specified by how they should be expressed.
 * <p>
 * CONVERSION: Any operator that is expressed as: (operator)var
 * <p>
 * FUNCTION: Any operator that is expressed as: operator(var) or operator(var1, var2)
 * <p>
 * OPERATOR: Any operator that is expressed as: var1 operatot var2
 * <p>
 * In all case the deciding factor is how Stilts expects the function.
 * <p>
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/sec9.4.html
 * @author christian
 */
public enum TableOperatorType {

    CONVERSION, FUNCTION, OPERATOR;

}
