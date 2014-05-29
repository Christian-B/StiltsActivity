package net.sf.taverna.t2.activities.stilts.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christian
 */
public class AllConfigurations {
    private List<ConfigurationGroup> groups;
    
    public AllConfigurations(){
        groups = new ArrayList<ConfigurationGroup>();
    }
    
    public void addGroup(ConfigurationGroup group){
        groups.add(group);
    }
    
    public ConfigurationGroup getGroup(String category){
        for (ConfigurationGroup group:groups){
            if (group.getCategory().equals(category)){
                return group;
            }
        }
        return null;
    }
    
    public List<ConfigurationGroup> getGroups(){
        return groups;
    }

    public int size() {
        int size = 0;
        for (ConfigurationGroup group:groups){
            size+= group.getConfigurations().size();
        }
        return size;
    }
    
    @Override
    public boolean equals(Object other){
        if (other instanceof AllConfigurations){
            AllConfigurations allOther = (AllConfigurations)other;
            return groups.equals(allOther.groups);
        } else {
            return false;
        }
    }
}
