/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author christian
 */
public class ListConfiguration extends StiltsConfiguration{
    //super.name holds the countName
    //super.item holds the ListItem
    private final List<String> listNames;
    private final boolean adjustableCount;
    
    public ListConfiguration(String countName, String listName, List<Object> values, boolean adjustableCount){
        super(countName, new ListItem(values), true);
        this.listNames = new ArrayList<String>();
        listNames.add(listName);
        this.adjustableCount = adjustableCount;
    }

    public void addList(String listName, List<Object> list) {
        listNames.add(listName);
        ListItem items = (ListItem)getItem();
        items.addList(list);
    }
    
    public int numberOfLists(){
        return listNames.size();
    }
    
    public String getName(int index){
        return listNames.get(index);
    }
    
    public List<Object> getItem(int index){
        ListItem items = (ListItem)getItem();
        return items.getList(index);
    }
            
}
