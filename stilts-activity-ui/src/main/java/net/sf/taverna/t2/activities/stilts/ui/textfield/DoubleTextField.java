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
    public Double getColumn(){
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
    
}
