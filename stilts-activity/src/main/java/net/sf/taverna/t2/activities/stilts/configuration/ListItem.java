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

    public List<Object> getList(int index) {
        return allValues.get(index);
    }
    
    @Override
    public boolean equals(Object other){
        if (other instanceof ListItem){
            ListItem otherItem = (ListItem)other;
            if (count != otherItem.count){
                return false;
            }
            for (int i = 0; i < count; i++){
                if (!allValues.get(i).equals(otherItem.allValues.get(i))){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int getCount() {
        return count;
    }

}
