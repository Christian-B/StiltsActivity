/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;

/**
 * Type of the input table.
 * @author christian
 */
public enum StiltsInputType  implements DescribableInterface {

    FILE("File","Path to a local file. "),
    STRING("String","A String representing the table. Will be written to a temp file"),
    URL("URL","Full Uniform resource locator, including schema");
    
    private final String userName;
    private final String description;

    StiltsInputType(String userName, String description){
        this.userName = userName;  
        this.description = description;
    }

    /**
     * @return the stiltsName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    public static StiltsInputType byUserName(String userName){
        for (StiltsInputType format:StiltsInputType.values()){
            if (format.userName.equals(userName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsInputType known for " + userName);
    }

}
