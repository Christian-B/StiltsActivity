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
    
    public ColumnIdTextField(String originalValue){
        super(originalValue);
    }
    
    @Override
    public String getColumn(){
        String asString = getText();
        asString = asString.trim();
        if (asString.isEmpty()){
            return null;
        }        
        if (asString.startsWith("$")){
            if (asString.matches("\\$\\d+")){
                return asString;            
            } else {
                return null;
            }
        }
        if (asString.matches("\\d+")){
            return "$" + asString;    
        }
        if (asString.contains(" ")){
            return null;
        }        
        if (asString.contains("-")){
            return null;
        }        
        return asString;
    }
    
}
