package net.sf.taverna.t2.activities.stilts.configuration;

import java.util.List;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class ConfigurationGroup {
    
    private final String category;
    private final String title;
    private final List<StiltsConfiguration> configurations;
    
    public ConfigurationGroup(String category, String title, List<StiltsConfiguration> configurations){
        this.category = category;
        this.title = title;
        this.configurations = configurations;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the configurations
     */
    public List<StiltsConfiguration> getConfigurations() {
        return configurations;
    }
    
    public Object getItem(String key) throws ActivityConfigurationException{
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

    public void checkClass(String key, Class aClass) throws ActivityConfigurationException {
        Object item = getItem(key);
        if (!item.getClass().equals(aClass)){
            throw new ActivityConfigurationException(key + " expected to be a " + aClass + " but found " + item.getClass());
        }
    }
    
    public void checkString(String key) throws ActivityConfigurationException {
        Object item = getItem(key);
        if (item instanceof String){
            if (item.toString().isEmpty()){
                throw new ActivityConfigurationException(key + " can not be empty");
            }
        } else {
            throw new ActivityConfigurationException(key + " expected to be a String.");
        }
    }

    public void checkPositiveInteger(String key) throws ActivityConfigurationException {
        Object item = getItem(key);
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

    @Override
    public boolean equals(Object other){
        if (other instanceof ConfigurationGroup){
            ConfigurationGroup otherGroup = (ConfigurationGroup)other;
            if (!category.equals(otherGroup.category)){
                return false;
            }
            if (!title.equals(otherGroup.title)){
                return false;
            }
            return configurations.equals(otherGroup.configurations);
        } else {
            return false;
        }
    }

}
