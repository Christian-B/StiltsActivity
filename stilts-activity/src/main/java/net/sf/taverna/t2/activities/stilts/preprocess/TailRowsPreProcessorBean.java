package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class TailRowsPreProcessorBean extends RowCountPreProcessorBean{
    
    public TailRowsPreProcessorBean(){  
    }

    public TailRowsPreProcessorBean(int rowCount){  
        super(rowCount);
    }

    @Override
    public String retrieveStilsCommand() {
        return "cmd=tail " + this.getNumberOfRows();
    }

      
}
