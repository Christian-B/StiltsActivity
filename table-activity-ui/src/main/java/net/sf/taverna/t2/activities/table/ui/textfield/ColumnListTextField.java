/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.table.ui.textfield;

/**
 *
 * @author christian
 */
public class ColumnListTextField extends ColumnIdTextField{
    
    
    public ColumnListTextField(String originalValue){
        super(originalValue, ColumnIdTextField.ALLOW_ID);
    }
    
    @Override
    public String getValue(){
        String asString = getText();
        asString = asString.trim();
        if (asString.isEmpty()){
            return null;
        }       
        String[] parts = asString.split(" ");
        String fullString = "";
        for (String part:parts){
            if (!part.trim().isEmpty()){
                String asColumn = this.toColumn(part);
                if (asColumn == null){
                    return null;
                } else {
                    fullString+= asColumn;
                }
            }
            
        }
        if (fullString.isEmpty()){
            return null;
        }        
        return fullString;
    }
        
    @Override
    public String helpText(){
        return  "A space seperated list of one or more columns.n"
                + "These can be the Column Name, Index or wildcards.\n" 
                + "The name of the column may be used if it contains no spaces \n"
                + "and doesn't start with a minus character ('-'). \n"
                + "It is usually matched case insensitively. \n"
                + "If multiple columns have the same name, the first one that matches is selected. \n"
                + "Column Index is a useful fallback if the column name isn't suitable for some reason.\n"
                + "The first column is '$1', the second is '$2' and so on.\n"
                + "You can use a simple form of wildcard expression\n"
                + " which expands to any columns in the table whose names match the pattern.\n"
                + "Currently, the only special character is an asterisk '*' \n"
                + "which matches any sequence of characters.\n"
                + "To match an unknown sequence at the start or end of the string\n"
                + " an asterisk must be given explicitly.\n"
                + "The order of the expanded list is the same as the order\n"
                + "in which the columns appear in the table.\n"
                + "Thus \"col*\" will match columns named col1, Column2 and COL_1024\n, but not decOld.\n"
                + "\"*MAG*\" will match columns named magnitude, ABS_MAG_U and JMAG.\n"
                + " \"*\" on its own expands to a list of all the columns of the table in order.\n"
                + "Specifying a list which contains a given column more than once is not recommened";
    }
}
