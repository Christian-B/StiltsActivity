package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.preprocess.StiltsPreProcessBean;

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

    StiltsPreProcessConfigurationPanel(BoundedBean preprocessBean, boolean editable){
        this.preprocessBean = preprocessBean;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("PreProcess"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        if (editable){
            addEditable(preprocessBean);
        } else {
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            JLabel commandLabel = new JLabel (preprocessBean.retrieveStilsCommand());
            add(commandLabel, c);
        }
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

}
