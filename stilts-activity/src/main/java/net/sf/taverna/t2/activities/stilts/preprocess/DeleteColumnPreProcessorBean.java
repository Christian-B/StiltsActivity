package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class DeleteColumnPreProcessorBean extends ColumnListPreProcessorBean{
    
    public DeleteColumnPreProcessorBean(){  
    }

    public DeleteColumnPreProcessorBean(String columnList){  
        super(columnList);
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=delcols \"" + this.getColumnList() + "\"";
    }

      
}
