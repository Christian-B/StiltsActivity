package net.sf.taverna.t2.activities.table.ui.config.preprocess;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.table.preprocess.AddColumnPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.DeleteColumnPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.HeadRowsPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.KeepColumnPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.SelectByCommandPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.SortPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.AbstractPreProcessBean;
import net.sf.taverna.t2.activities.table.preprocess.TailRowsPreProcessorBean;
import net.sf.taverna.t2.activities.table.preprocess.UserSpecifiedPreProcessorBean;
import net.sf.taverna.t2.activities.table.ui.config.BasePanel;

/**
 * Base class of all the PreProcess Configuration Panels
 * <p>
 * This allow the specification of details such as checkValues(), getConfiguration(), noteConfiguration() and refreshConfiguration 
 * to be delegated to the preprocess specific panels.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> a specific AbstractPreProcessBean
 */
@SuppressWarnings("serial")
public abstract class TablePreProcessConfigurationPanel <BoundedBean extends AbstractPreProcessBean> extends BasePanel{
 
    BoundedBean preprocessBean;

    TablePreProcessConfigurationPanel(BoundedBean preprocessBean){
        this.preprocessBean = preprocessBean;
    }
    
    /**
      * Check that user values in UI are valid
      */
    public abstract boolean checkValues();

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
      */
    public BoundedBean getConfiguration() {
        // Should already have been made by noteConfiguration()
        return preprocessBean;
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
    public void refreshConfiguration(BoundedBean preprocessBean) {
        this.preprocessBean = preprocessBean;
    }

    public static TablePreProcessConfigurationPanel factory(AbstractPreProcessBean preprocessBean){
        if (preprocessBean == null){
            return null;
        }
        if (preprocessBean instanceof AddColumnPreProcessorBean){
            return new AddColumnPreProcessConfigurationPanel((AddColumnPreProcessorBean)preprocessBean);
        }
        if (preprocessBean instanceof DeleteColumnPreProcessorBean){
            return new DeleteColumnPreProcessorConfigurationPanel((DeleteColumnPreProcessorBean)preprocessBean);
        }
        if (preprocessBean instanceof HeadRowsPreProcessorBean){
            return new HeadRowsPreProcessorConfigurationPanel((HeadRowsPreProcessorBean)preprocessBean);
        }
        if (preprocessBean instanceof KeepColumnPreProcessorBean){
            return new KeepColumnPreProcessorConfigurationPanel((KeepColumnPreProcessorBean)preprocessBean);
        }
        if (preprocessBean instanceof SelectByCommandPreProcessorBean){
            return new SelectByCommandPreProcessorConfigurationPanel((SelectByCommandPreProcessorBean)preprocessBean);
        }
        if (preprocessBean instanceof SortPreProcessorBean){
            return new SortPreProcessorConfigurationPanel((SortPreProcessorBean)preprocessBean);
        }
        if (preprocessBean instanceof TailRowsPreProcessorBean){
            return new TailRowsPreProcessorConfigurationPanel((TailRowsPreProcessorBean)preprocessBean);
        }
        if (preprocessBean instanceof UserSpecifiedPreProcessorBean){
            return new UserSpecifiedPreProcessorConfigurationPanel((UserSpecifiedPreProcessorBean)preprocessBean);
        }
        throw new UnsupportedOperationException(preprocessBean.getClass() + " not supported");
    }
}
