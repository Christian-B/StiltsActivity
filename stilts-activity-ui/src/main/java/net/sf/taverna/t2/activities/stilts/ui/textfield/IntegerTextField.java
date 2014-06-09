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
    public Integer getValue(){
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

    @Override
    public String helpText() {
        return "This can be any positive integer.\n"
                + "So it can only be the digit 0 to 9\n"
                + "With an optional + in the front.";
    }
    
    public void setValue(int value){
        if (value > 0){
            setText(value+"");
        }
    }
}
