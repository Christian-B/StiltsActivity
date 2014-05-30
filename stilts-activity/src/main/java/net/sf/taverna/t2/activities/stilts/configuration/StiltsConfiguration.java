package net.sf.taverna.t2.activities.stilts.configuration;

import net.sf.taverna.t2.activities.stilts.configuration.*;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class StiltsConfiguration {
    
    private final String name;
    private Object item;
    private final boolean configurable;
    
    public StiltsConfiguration(String name, Object item, boolean configurable){
        this.name = name;
        this.item = item;
        this.configurable = configurable;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the item
     */
    public Object getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Object item) {
        this.item = item;
    }

    /**
     * @return the configurable
     */
    public boolean isConfigurable() {
        return configurable;
    }
    
    @Override
    public boolean equals(Object other){
        if (other instanceof StiltsConfiguration){
            StiltsConfiguration otherConfig = (StiltsConfiguration)other;
            if (!getName().equals(otherConfig.getName())) {
                //ystem.out.println ("name " + name + " != " + otherConfig.name);
                return false;
            }
            //if (!getItem().equals(otherConfig.getItem()){
                //ystem.out.println ("item " + item + " != " + otherConfig.item);                
            //}
            return getItem().equals(otherConfig.getItem());
        } else {
            return false;
        }
    }

    public void checkClass(Class aClass) throws ActivityConfigurationException {
        //ystem.out.println("checking " + name + " -> " + item + "  " + aClass);
        if (item == null){
            throw new ActivityConfigurationException(name + " value was null ");
        }
        if (!item.getClass().equals(aClass)){
            throw new ActivityConfigurationException(name + " expected to be a " + aClass + " but found " + item.getClass());
        }
    }
    
    public void checkString() throws ActivityConfigurationException {
        if (item instanceof String){
            if (item.toString().isEmpty()){
                throw new ActivityConfigurationException(name + " can not be empty");
            }
        } else {
            throw new ActivityConfigurationException(name + " expected to be a String.");
        }
    }

    public void checkPositiveInteger() throws ActivityConfigurationException {
        Integer integer;
        if (item instanceof Integer){
            integer = (Integer)item;
        } else {
            try {
                integer = Integer.parseInt(item.toString());
            } catch (Exception ex){
                throw new ActivityConfigurationException(name + " expected to be a positive Integer");
            }
        }
        if (integer < 1){
            throw new ActivityConfigurationException(name + " expected to be greater than zero but found " + item);
        }
    }


}
