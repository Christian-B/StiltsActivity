package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.test.StiltsProcessBean;

@SuppressWarnings("serial")
public class StiltsProcessConfigurationPanel <BoundedBean extends StiltsProcessBean> extends JPanel{
 
    BoundedBean processBean;
    StiltsInputConfigurationPanel inputPanel;
    protected final JPanel processPanel;

    StiltsProcessConfigurationPanel(BoundedBean processBean, StiltsInputConfigurationPanel inputPanel){
        this.processBean = processBean;
        this.inputPanel = inputPanel;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        processPanel = new JPanel(new GridLayout(0, 1));
        processPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Process"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        add(processPanel, c);        
        c.gridy = getInputRow();
        add(inputPanel, c);        
    }
    
    int getInputRow(){
        return 1;
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
        return inputPanel.isConfigurationChanged();
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
}
