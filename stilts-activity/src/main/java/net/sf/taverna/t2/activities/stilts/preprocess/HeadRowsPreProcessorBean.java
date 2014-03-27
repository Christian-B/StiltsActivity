package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class HeadRowsPreProcessorBean extends RowCountPreProcessorBean{
    
    public HeadRowsPreProcessorBean(){  
    }

    public HeadRowsPreProcessorBean(int rowCount){  
        super(rowCount);
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=head " + this.getNumberOfRows();
    }

      
}
