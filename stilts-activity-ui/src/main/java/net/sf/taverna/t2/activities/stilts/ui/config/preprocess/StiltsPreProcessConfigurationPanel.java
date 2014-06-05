package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnByCommandPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.DeleteColumnPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.HeadRowsPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.KeepColumnPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.SelectByCommandPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.SortPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.StiltsPreProcessBean;
import net.sf.taverna.t2.activities.stilts.preprocess.TailRowsPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.UserSpecifiedPreProcessorBean;

/**
 * Base class of all the PreProcess Configuration Panels
 * <p>
 * This allow the specification of details such as checkValues(), getConfiguration(), noteConfiguration() and refreshConfiguration 
 * to be delegated to the preprocess specific panels.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> a specific StiltsPreProcessBean
 */
@SuppressWarnings("serial")
public abstract class StiltsPreProcessConfigurationPanel <BoundedBean extends StiltsPreProcessBean> extends JPanel{
 
    BoundedBean preprocessBean;

    StiltsPreProcessConfigurationPanel(BoundedBean preprocessBean){
        this.preprocessBean = preprocessBean;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("PreProcess"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        addEditable(preprocessBean);
    }
    
    abstract void addEditable(BoundedBean preprocessBean);

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

    public static StiltsPreProcessConfigurationPanel factory(StiltsPreProcessBean preprocessBean){
        if (preprocessBean == null){
            return null;
        }
        if (preprocessBean instanceof AddColumnByCommandPreProcessorBean){
            return new AddColumnByCommandPreProcessorConfigurationPanel((AddColumnByCommandPreProcessorBean)preprocessBean);
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
