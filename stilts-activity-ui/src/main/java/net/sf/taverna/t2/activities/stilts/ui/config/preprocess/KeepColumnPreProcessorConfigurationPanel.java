package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import net.sf.taverna.t2.activities.stilts.preprocess.KeepColumnPreProcessorBean;


@SuppressWarnings("serial")
public class KeepColumnPreProcessorConfigurationPanel extends ColumnListPreProcessorConfigurationPanel<KeepColumnPreProcessorBean>{
 
    public KeepColumnPreProcessorConfigurationPanel(KeepColumnPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
    }
    
    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean = new KeepColumnPreProcessorBean();
        super.noteConfiguration();
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(KeepColumnPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
    }

    @Override
    String getAction() {
        return "Keep";
    }
}