package net.sf.taverna.t2.activities.stilts.ui.textfield;

/**
 *
 * @author christian
 */
public class IntegerTextField extends CheckerTextField{
           
    public IntegerTextField(){
        super();
    }
    
    @Override
    public Integer getColumn(){
        String asString = getText();
        asString = asString.trim();
        if (asString.isEmpty()){
            return null;
        }  
        try {
            Integer value = Integer.parseInt(asString);
            if (value < 1){
                return null;
            }
            return value;
        } catch (NumberFormatException ex){
            return null;    
        }    
    }
    
}
