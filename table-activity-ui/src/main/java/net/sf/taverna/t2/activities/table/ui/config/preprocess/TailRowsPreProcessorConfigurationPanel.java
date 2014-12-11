package net.sf.taverna.t2.activities.table.ui.config.preprocess;

import net.sf.taverna.t2.activities.table.preprocess.TailRowsPreProcessorBean;


/**
 * Tail PreProcess Configuration Panels
 * <p>
 * Specification of the number of rows is handled by the super class.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */@SuppressWarnings("serial")
public class TailRowsPreProcessorConfigurationPanel extends RowCountPreProcessorConfigurationPanel<TailRowsPreProcessorBean>{
 
    public TailRowsPreProcessorConfigurationPanel(TailRowsPreProcessorBean preprocessBean){
        super(preprocessBean);
    }
    
    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean = new TailRowsPreProcessorBean();
        super.noteConfiguration();
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(TailRowsPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
    }

    @Override
    String getAction() {
        return "Tail";
    }
}
