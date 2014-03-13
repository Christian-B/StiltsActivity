package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.preprocess.StiltsPreProcessBean;

@SuppressWarnings("serial")
public class StiltsPreProcessConfigurationPanel <BoundedBean extends StiltsPreProcessBean> extends JPanel{
 
    BoundedBean preprocessBean;

    StiltsPreProcessConfigurationPanel(BoundedBean preprocessBean){
        this.preprocessBean = preprocessBean;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("PreProcess"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
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
        return preprocessBean;
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
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(BoundedBean preprocessBean) {
        this.preprocessBean = preprocessBean;
    }
}
