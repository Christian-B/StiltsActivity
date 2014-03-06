/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;

/**
 * @author christian
 */
public enum StiltsOutputType {

    FILE("File","Path to a local file. "),
    STRING("String","A String representing the table. Will be read from a temp file");
    //URL currently not supported
    //URL("URL","Full Uniform resource locator, including schema");
    
    private final String userName;
    private final String description;

    StiltsOutputType(String userName, String description){
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
    
    public static StiltsOutputType byUserName(String userName){
        for (StiltsOutputType format:StiltsOutputType.values()){
            if (format.userName.equals(userName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No StiltsOutputType known for " + userName);
    }

}
