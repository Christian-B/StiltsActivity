package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import net.sf.taverna.t2.activities.stilts.preprocess.DeleteColumnPreProcessorBean;

/**
 * Delete Colum PreProcess Configuration Panels
 * <p>
 * Specification of the columns list is handled by the super class.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DeleteColumnPreProcessorConfigurationPanel extends ColumnListPreProcessorConfigurationPanel<DeleteColumnPreProcessorBean>{
 
    public DeleteColumnPreProcessorConfigurationPanel(DeleteColumnPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
    }
    
    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean = new DeleteColumnPreProcessorBean();
        super.noteConfiguration();
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(DeleteColumnPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
    }

    @Override
    String getAction() {
        return "Delete";
    }
}
