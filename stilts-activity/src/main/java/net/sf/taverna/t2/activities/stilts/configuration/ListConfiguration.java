/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.configuration;

import java.util.ArrayList;
import java.util.List;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 *
 * @author christian
 */
public class ListConfiguration extends StiltsConfiguration{
    //super.name holds the Add Remove name
    private final boolean adjustableCount;
    private final ArrayList<List<StiltsConfiguration>> lists;
    private final ArrayList<String> listNames;
    
    public ListConfiguration(String countName, String listName, List objects, boolean adjustableCount){
        super(countName, objects.size(), true);
        lists = new ArrayList<List<StiltsConfiguration>>();
        this.adjustableCount = adjustableCount;
        listNames = new ArrayList<String>();
        addList(listName, objects);
     }

    public final void addList(String listName, List<Object> objects) {
        List<StiltsConfiguration> list = new ArrayList<StiltsConfiguration>();
        for (int i = 0; i < objects.size(); i++){
            StiltsConfiguration configuration = new StiltsConfiguration(listName + (i +1), objects.get(i), true);
            list.add(configuration);
        }
        lists.add(list);
        listNames.add(listName);
    }
    
    public int numberOfLists(){
        return lists.size();
    }
    
    public List<StiltsConfiguration> getConfigurations() {
        List<StiltsConfiguration> allList = new ArrayList<StiltsConfiguration>();
        for (List<StiltsConfiguration> next:lists){
            allList.addAll(next);
        }
        return allList;
    }

    public void addToLists(){
        for (int listNum = 0; listNum < numberOfLists(); listNum++){
            List<StiltsConfiguration> list = lists.get(listNum);
            StiltsConfiguration config = new StiltsConfiguration(listNames.get(listNum) + (list.size()+1), list.get(0).getItem(), true);
            list.add(config);
            this.setItem(list.size());
        }        
    }
    
    public void deleteLastFromLists(){
        for (int listNum = 0; listNum < numberOfLists(); listNum++){
            List<StiltsConfiguration> list = lists.get(listNum);
            if (list.size() <= 1){
                throw new IllegalStateException ("Illegal call to remove only item from lists");
            }
            list.remove(list.size()-1);
            this.setItem(list.size());
        }        
    }

    public boolean hasAdjustableCount() {
        return adjustableCount;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof ListConfiguration){
            ListConfiguration otherList = (ListConfiguration)other;
            if (!super.equals(other)){
                return false; //Name or size wrong
            }
            if (!lists.equals(otherList.lists)){
                return false;
            }
            if (!listNames.equals(otherList.listNames)){
                return false;
            }
            return this.adjustableCount == otherList.adjustableCount;
        } else {
            return false;
        }
    }

    void checkClasses(String listName, Class aClass) throws ActivityConfigurationException {
        boolean found = false;
        for (int i = 0; i< listNames.size(); i++){
            if (listNames.get(i).equals(listName)){
                for (StiltsConfiguration config:lists.get(i)){
                    config.checkClass(aClass);
                }
                found = true;
            }
        }
        if (!found){
            throw new ActivityConfigurationException("ListConfiguration " + getName() + " has no list " + listName);
        }
    }

         
 }
