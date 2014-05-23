/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.utils;

import java.util.List;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class ConfigurationUtils {
    
    public static Object getItem(List<StiltsConfiguration> configurations, String key) throws ActivityConfigurationException{
        for (StiltsConfiguration configuration:configurations){
            if (configuration.getName().equals(key)){
                if (configuration.getItem() == null){
                    throw new ActivityConfigurationException("Null configuration for " + key + " found.");
                }
                return configuration.getItem();
            }
        }
        throw new ActivityConfigurationException("No configuration for " + key + " found.");
    }

    public static void checkClass(List<StiltsConfiguration> configurations, String key, Class aClass) throws ActivityConfigurationException {
        Object item = getItem(configurations, key);
        if (!item.getClass().equals(aClass)){
            throw new ActivityConfigurationException(key + " expected to be a " + aClass + " but found " + item.getClass());
        }
    }
    
    public static void checkString(List<StiltsConfiguration> configurations, String key) throws ActivityConfigurationException {
        Object item = getItem(configurations, key);
        if (item instanceof String){
            if (item.toString().isEmpty()){
                throw new ActivityConfigurationException(key + " can not be empty");
            }
        } else {
            throw new ActivityConfigurationException(key + " expected to be a String.");
        }
    }

    public static void checkPositiveInteger(List<StiltsConfiguration> configurations, String key) throws ActivityConfigurationException {
        Object item = getItem(configurations, key);
        Integer integer;
        if (item instanceof Integer){
            integer = (Integer)item;
        } else {
            try {
                integer = Integer.parseInt(item.toString());
            } catch (Exception ex){
                throw new ActivityConfigurationException(key + " expected to be a positive Integer");
            }
        }
        if (integer != 0){
            throw new ActivityConfigurationException(key + " expected to be greater than zero");
        }
    }
}
