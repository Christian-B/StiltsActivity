/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.configuration;

import java.util.ArrayList;
import java.util.List;

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
            StiltsConfiguration config = new StiltsConfiguration(listNames.get(listNum) + list.size(), lists.get(0), true);
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
         
 }
