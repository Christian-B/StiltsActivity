package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.input.StitlsInputsBean;
import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.process.StiltsProcessBean;
import net.sf.taverna.t2.activities.stilts.process.TCatBean;
import net.sf.taverna.t2.activities.stilts.process.TCatNBean;
import net.sf.taverna.t2.activities.stilts.process.TJoinBean;
import net.sf.taverna.t2.activities.stilts.process.TPipeBean;
import net.sf.taverna.t2.activities.stilts.ui.config.BasePanel;

/**
 * Base class of all the Process Configuration Panels
 * <p>
 * This allow the specification of details such as checkValues(), getConfiguration(), noteConfiguration() and refreshConfiguration 
 * to be delegated to the process specific panels.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> a specific StiltsProcessBean
 */
@SuppressWarnings("serial")
public abstract class StiltsProcessConfigurationPanel <BoundedBean extends StiltsProcessBean> extends BasePanel{
 
    BoundedBean processBean;

    StiltsProcessConfigurationPanel(BoundedBean processBean){
        this.processBean = processBean;
    }
    
    public abstract boolean isConfigurable();
     /**
      * Check that user values in UI are valid
      */
    public boolean checkValues() {
         // All valid, return true
        return true;
    }

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
      */
    public BoundedBean getConfiguration() {
        // Should already have been made by noteConfiguration()
        return processBean;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    public boolean isConfigurationChanged() {
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    public void noteConfiguration(StitlsInputsBean inputBean) {
        processBean.setInputs(inputBean);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(BoundedBean processBean) {
        this.processBean = processBean;
    }
    
    public static StiltsProcessConfigurationPanel factory(StiltsProcessBean bean){
        StiltsProcessConfigurationPanel panel = null;
        if (bean instanceof ExactMatchBean){
            panel = new ExactMatchConfigurationPanel((ExactMatchBean) bean);
        } else if (bean instanceof TCatBean){
            panel = new TCatConfigurationPanel((TCatBean) bean);
        } else if (bean instanceof TCatNBean){
            panel = new TCatNConfigurationPanel((TCatNBean) bean);
        } else if (bean instanceof TJoinBean){
            panel = new TJoinConfigurationPanel((TJoinBean) bean);
        } else if (bean instanceof TPipeBean){
            panel = new TPipeConfigurationPanel((TPipeBean) bean);
        } else {
            throw new UnsupportedOperationException(bean.getClass() + " not supported");
        }
        return panel;
    }
    
}
