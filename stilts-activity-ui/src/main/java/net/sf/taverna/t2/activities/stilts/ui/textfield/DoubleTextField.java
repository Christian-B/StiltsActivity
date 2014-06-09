package net.sf.taverna.t2.activities.stilts.ui.textfield;

/**
 *
 * @author christian
 */
public class DoubleTextField extends CheckerTextField{
           
    public DoubleTextField(){
        super();
    }
    
    @Override
    public Double getValue(){
        String asString = getText();
        asString = asString.trim();
        if (asString.isEmpty()){
            return null;
        }  
        try {
            Double value = Double.parseDouble(asString);
            return value;
        } catch (NumberFormatException ex){
            return null;    
        }    
    }
    
    @Override
    public String helpText() {
        return "Any String that Java can convert to a number.\n"
                + "Decimal values are allowed\n"
                + "This can start with a - or +\n"
                + "Even Scientific notation eg 4.56E5 is supported.";
    }
}
