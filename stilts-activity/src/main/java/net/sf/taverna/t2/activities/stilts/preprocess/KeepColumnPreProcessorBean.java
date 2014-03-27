package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class KeepColumnPreProcessorBean extends ColumnListPreProcessorBean{
    
    public KeepColumnPreProcessorBean(){  
    }

    public KeepColumnPreProcessorBean(String columnList){  
        super(columnList);
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=keepcols \"" + this.getColumnList() + "\"";
    }

      
}
