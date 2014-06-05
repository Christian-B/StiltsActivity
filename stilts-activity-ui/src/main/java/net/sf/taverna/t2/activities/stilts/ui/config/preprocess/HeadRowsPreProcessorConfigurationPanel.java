package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import net.sf.taverna.t2.activities.stilts.preprocess.HeadRowsPreProcessorBean;

/**
 * Head PreProcess Configuration Panels
 * <p>
 * Specification of the number of rows is handled by the super class.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class HeadRowsPreProcessorConfigurationPanel extends RowCountPreProcessorConfigurationPanel<HeadRowsPreProcessorBean>{
 
    public HeadRowsPreProcessorConfigurationPanel(HeadRowsPreProcessorBean preprocessBean){
        super(preprocessBean);
    }
    
    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean = new HeadRowsPreProcessorBean();
        super.noteConfiguration();
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(HeadRowsPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
    }

    @Override
    String getAction() {
        return "Head";
    }
}
