/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.preprocess;

import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

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
    
    @Override
    public void checkValid() throws ActivityConfigurationException {
        if (preProcessCommand == null){
            throw new ActivityConfigurationException("PreProcess Command not specified");
        }
        if (preProcessCommand.trim().isEmpty()){
            throw new ActivityConfigurationException("PreProcess Command is empty");
        }
    }

    @Override
    public String retrieveStilsCommand() {
        return  "cmd=" + preProcessCommand;
    }

}
