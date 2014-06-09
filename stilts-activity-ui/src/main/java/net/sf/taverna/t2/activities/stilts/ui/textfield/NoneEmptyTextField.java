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
public class NoneEmptyTextField extends CheckerTextField{

    @Override
    public String getValue(){
        String text = getText();
        if (text.trim().isEmpty()){
            return null;
        }
        return text;
    }
    

    @Override
    public String helpText() {
        return "Any Text as long as it is not empty or just whitespace.";
    }
    
}
