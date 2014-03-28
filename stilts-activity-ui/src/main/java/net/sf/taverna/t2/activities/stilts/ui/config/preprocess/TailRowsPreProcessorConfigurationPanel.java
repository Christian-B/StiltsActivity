package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import net.sf.taverna.t2.activities.stilts.preprocess.TailRowsPreProcessorBean;


@SuppressWarnings("serial")
public class TailRowsPreProcessorConfigurationPanel extends RowCountPreProcessorConfigurationPanel<TailRowsPreProcessorBean>{
 
    public TailRowsPreProcessorConfigurationPanel(TailRowsPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
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
