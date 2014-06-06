/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.ui.textfield;

/**
 *
 * @author christian
 */
public class ColumnIdTextField extends CheckerTextField{
    
    public static final boolean ALLOW_ID = true;
    public static final boolean NAME_ONLY = false;
    
    private final boolean allowId;
    
    public ColumnIdTextField(String originalValue, boolean allowId){
        super();
        this.allowId = allowId;
        setText(originalValue);
    }
    
    @Override
    public String getColumn(){
        String asString = getText();
        asString = asString.trim();
        if (asString.isEmpty()){
            return null;
        }        
        if (asString.startsWith("$")){
            if (allowId && asString.matches("\\$\\d+")){
                return asString;            
            } else {
                return null;
            }
        }
        if (allowId){
            if (asString.matches("\\d+")){
                return "$" + asString;            
            }
        }
        if (asString.contains(" ")){
            return null;
        }        
        if (asString.startsWith("-")){
            return null;
        }        
        try {
            Double value = Double.parseDouble(asString);
            //Oops could be a number and is not a positive integer 
            return null; 
        } catch (NumberFormatException ex){
            //Ok not a number
            return asString;    
        }         
    }
    
}
