package net.sf.taverna.t2.activities.stilts.ui.config.process;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.process.StiltsProcessBean;
import net.sf.taverna.t2.activities.stilts.process.TCatBean;
import net.sf.taverna.t2.activities.stilts.process.TCatNBean;
import net.sf.taverna.t2.activities.stilts.process.TJoinBean;
import net.sf.taverna.t2.activities.stilts.process.TPipeBean;
import net.sf.taverna.t2.activities.stilts.ui.config.input.StiltsInputConfigurationPanel;

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
public class StiltsProcessConfigurationPanel <BoundedBean extends StiltsProcessBean> extends JPanel{
 
    BoundedBean processBean;
    StiltsInputConfigurationPanel inputPanel;
    protected final JPanel processPanel;

    StiltsProcessConfigurationPanel(BoundedBean processBean){
        this.processBean = processBean;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        processPanel = new JPanel(new GridLayout(0, 1));
        processPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Process"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        add(processPanel, c);        
     }
    
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
    public void noteConfiguration() {
        inputPanel.noteConfiguration();       
        processBean.setInputs(inputPanel.getConfiguration());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(BoundedBean processBean) {
        this.processBean = processBean;
        inputPanel.refreshConfiguration(processBean.getInputs());        
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
        
        panel.inputPanel = StiltsInputConfigurationPanel.factory(bean.getInputs());
        return panel;
    }
    
    public StiltsInputConfigurationPanel getInputPanel(){
        return inputPanel;
    }
}
