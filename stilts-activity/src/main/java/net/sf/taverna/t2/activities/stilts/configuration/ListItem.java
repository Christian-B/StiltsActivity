package net.sf.taverna.t2.activities.stilts.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christian
 */
public class ListItem {
    private int count;
    private List<List<Object>> allValues;
    
    public ListItem(List<Object> values){
        this.count = values.size();
        this.allValues = new ArrayList<List<Object>>();
        this.allValues.add(values);
    }

    void addList(List<Object> list) {
        if (list.size() != count){
            throw new IllegalStateException("Count " + count + " does not match list size " + list.size());
        }
        allValues.add(list);
    }

    List<Object> getList(int index) {
        return allValues.get(index);
    }
    
}
