/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class UserSpecifiedPreProcessorBean extends StiltsPreProcessBean{
    
    private String preProcessCommand;

    public UserSpecifiedPreProcessorBean(){  
    }

    public UserSpecifiedPreProcessorBean(String preProcessCommand){  
        this.preProcessCommand = preProcessCommand;
    }

    /**
     * @return the preProcessCommand
     */
    public String getPreProcessCommand() {
        return preProcessCommand;
    }

    /**
     * @param preProcessCommand the preProcessCommand to set
     */
    public void setPreProcessCommand(String preProcessCommand) {
        this.preProcessCommand = preProcessCommand;
    }
    
}
