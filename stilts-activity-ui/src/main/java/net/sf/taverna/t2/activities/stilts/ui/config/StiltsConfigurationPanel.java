package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import net.sf.taverna.t2.activities.stilts.*;
import net.sf.taverna.t2.activities.stilts.input.*;
import net.sf.taverna.t2.activities.stilts.preprocess.*;
import net.sf.taverna.t2.activities.stilts.process.*;
import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.ui.config.input.*;
import net.sf.taverna.t2.activities.stilts.ui.config.preprocess.*;
import net.sf.taverna.t2.activities.stilts.ui.config.process.*;
import net.sf.taverna.t2.activities.stilts.utils.*;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;

/**
 * Main/Root Configuration panel for the Stilts Process.
 * <p>
 * Adds four panels to the gui.
 * <p>
 * processPanel which contains the information for the specific process being used.
 * This will contain a superclass of StiltsProcessConfigurationPanel depending on the process being done.
 * <p>
 * preprocessPanel Added if required to obtain settings for the preprocessor
 * This will contain a super class of StiltsPreProcessConfigurationPanel depending on the actual preprocessing being done.
 * <p>
 * OutputPanel: To get the output type and format
 * <p>
 * miscellaneousPanel: Any other information such as the debug state.
 * <p>
 * This class includes factory methods createProcessPanel and createPreprocessPanel to create the processPanel and preprocessPanel panels.
 * The specific detail of the methods checkValues(), getConfiguration(), isConfigurationChanged(), noteConfiguration() and refreshConfiguration()
 * is delegated to these panels.
 * <p>
 * This allows a single ActivityConfigurationAction, ContextualViewFactory, ContextualView implementation to be used for all Processes and preprocesses, 
 * rather than having to have specific implementations for all combination of these.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class StiltsConfigurationPanel extends
        ActivityConfigurationPanel<StiltsActivity, StiltsBean> {

    private final StiltsActivity activity;
    
    private final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
     
    public StiltsConfigurationPanel(StiltsActivity activity) {
        try {
            this.activity = activity;
            refreshConfiguration();
        } catch (RuntimeException ex){
            ex.printStackTrace();
            throw ex;
        }     
    }
    
    @Override
    public boolean isConfigurationChanged() {
        //TODO
        return false;
    }

    @Override
    public StiltsBean getConfiguration() {
        return activity.getConfiguration();
    }

    @Override
    public void noteConfiguration() {
        //TODO
    }
    
    private Component getSelector(Object item) {
        if (item instanceof Enum){
            Class c = item.getClass();
            JComboBox selector = new JComboBox(c.getEnumConstants());
            if (item instanceof DescribableInterface){
                selector.setRenderer(listCellRenderer);
            }
            selector.setSelectedItem(item);
            return selector;
        }        if (item instanceof String){
            return new JTextField(item.toString());
        }
        if (item instanceof String){
            return new JTextField(item.toString());            
        }
        if (item instanceof Integer){
            return new JTextField(item.toString());            
        }
        if (item instanceof Boolean){
            JCheckBox selector = new JCheckBox();            
            selector.setSelected(((Boolean)item).booleanValue());
            return selector;
        }
        return new JTextField(item.getClass() + "" + item.toString());
    }

//new JTextField(C.toString());
    private void addConfiguration(StiltsConfiguration configuration, int row) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel label = new JLabel(configuration.getName());
        add(label, c);
        c.gridx = 1;
        Component selector = getSelector(configuration.getItem());
        add(selector, c);
    }
   
    @Override
    public void refreshConfiguration() {
        removeAll();
        setLayout(new GridBagLayout());
        List<StiltsConfiguration> configurations = activity.configurations();
        for (int i = 0; i < configurations.size(); i++){
            addConfiguration(configurations.get(i), i);
        }
    }

    @Override
    public boolean checkValues() {
        //TODO
        return true;
    }

  
}
