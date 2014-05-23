/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.configuration;

import net.sf.taverna.t2.activities.stilts.configuration.*;

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
}
