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
    public String getValue(){
        String asString = getText();
        asString = asString.trim();
        if (asString.isEmpty()){
            return null;
        }        
        if (asString.contains(" ")){
            return null;
        }        
        return toColumn(asString);
    }
    
    protected final String toColumn(String column){
        if (column.startsWith("$")){
            if (allowId && column.matches("\\$\\d+")){
                return column;            
            } else {
                return null;
            }
        }
        if (allowId){
            if (column.matches("\\d+")){
                return "$" + column;            
            }
        }
        if (column.startsWith("-")){
            return null;
        }        
        try {
            Double value = Double.parseDouble(column);
            //Oops could be a number and is not a positive integer 
            return null; 
        } catch (NumberFormatException ex){
            //Ok not a number
            return column;    
        }         
    }
    
    @Override
    public String helpText(){
        StringBuilder helpText = new StringBuilder();
        if (allowId){
            helpText.append("This can be the Column Name or Index.\n");
        } else {
            helpText.append("This must be the Column Name.\n");            
        }
        helpText.append("The name of the column must not contain spaces \n");
        helpText.append("and must not start with a minus character ('-'). \n");
        if (allowId){
            helpText.append("It is usually matched case insensitively. \n");
            helpText.append("If multiple columns have the same name, the first one that matches is selected. \n");
            helpText.append("Column Index is a useful fallback if the column name isn't suitable for some reason.\n");
            helpText.append("The first column is '$1', the second is '$2' and so on.");
        }
        return helpText.toString();
    }
}
