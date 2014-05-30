package net.sf.taverna.t2.activities.stilts.configuration;

import java.util.List;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
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

    private StiltsConfiguration getConfig(String key) throws ActivityConfigurationException{
        for (StiltsConfiguration configuration:configurations){
            if (configuration.getName().equals(key)){
                return configuration;
            }
        }
        throw new ActivityConfigurationException("No configuration for " + key + " found.");
    }
    
    public Object getItem(String key) throws ActivityConfigurationException{
        StiltsConfiguration configuration = getConfig(key);
        if (configuration.getItem() == null){
            throw new ActivityConfigurationException("Null configuration for " + key + " found.");
        }
        return configuration.getItem();
    }

    public List<StiltsConfiguration> getList(String countName, String listName) throws ActivityConfigurationException {
        StiltsConfiguration configuration = getConfig(countName);
        if (configuration instanceof ListConfiguration){
            ListConfiguration listConfiguration = (ListConfiguration)configuration;
            return listConfiguration.getList(listName);
        } else{
            throw new ActivityConfigurationException (configuration.getName() + " is not the expected type");
        }
    }

    public void checkClass(String key, Class aClass) throws ActivityConfigurationException {
        StiltsConfiguration configuration = getConfig(key);
        configuration.checkClass(aClass);
    }
    
    public void checkClasses(String countName, String listName, Class aClass) throws ActivityConfigurationException {
        StiltsConfiguration configuration = getConfig(countName);
        if (configuration instanceof ListConfiguration){
            ListConfiguration listConfiguration = (ListConfiguration)configuration;
            listConfiguration.checkClasses(listName, aClass);
        } else{
            throw new ActivityConfigurationException (configuration.getName() + " is not the expected type");
        }
    }

    public void checkString(String key) throws ActivityConfigurationException {
        StiltsConfiguration configuration = getConfig(key);
        configuration.checkString();
    }

    public void checkPositiveInteger(String key) throws ActivityConfigurationException {
        StiltsConfiguration configuration = getConfig(key);
        configuration.checkPositiveInteger();
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
