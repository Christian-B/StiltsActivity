package net.sf.taverna.t2.activities.stilts.ui.config.test;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.input.StitlsInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

@SuppressWarnings("serial")
public abstract class StiltsInputConfigurationPanel<BoundedBean extends StitlsInputsBean> extends JPanel{
 
    BoundedBean inputBean;
    final boolean editable;
    
    public final String FORMAT_LABEL = "Format";
    public final String TYPE_LABEL = "Type";
    protected final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();

    
    StiltsInputConfigurationPanel(BoundedBean inputBean, boolean editable){
        this.inputBean = inputBean;
        this.editable = editable;
    }
    
     /**
      * Check that user values in UI are valid
     * @return 
      */
    public abstract boolean checkValues();

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
     * @return Return configuration bean generated from user interface
      */
    public BoundedBean getConfiguration(){
        return inputBean;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    public abstract boolean isConfigurationChanged();

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    public abstract void noteConfiguration();

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(BoundedBean inputBean){
        this.inputBean = inputBean;
    }
}
