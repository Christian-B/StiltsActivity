package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import net.sf.taverna.t2.activities.stilts.*;
import net.sf.taverna.t2.activities.stilts.configuration.AllConfigurations;
import net.sf.taverna.t2.activities.stilts.configuration.ConfigurationGroup;
import net.sf.taverna.t2.activities.stilts.configuration.ListConfiguration;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
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
    private static final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    private AllConfigurations configurations;
    private HashMap<StiltsConfiguration, Component> selectors;
    private int row;
    
    public StiltsConfigurationPanel(StiltsActivity activity) {
        this.activity = activity;
        configurations = activity.configurations();
        refreshConfiguration();
    }
    
    private Object getTheValue(Component component) {
        if (component instanceof JCheckBox){
            JCheckBox jCheckBox = (JCheckBox)component;
            return jCheckBox.isSelected();
        }
        if (component instanceof JComboBox){
            JComboBox jComboBox = (JComboBox)component;
            return jComboBox.getSelectedItem();
        }
        if (component instanceof JTextField){
            JTextField jTextField = (JTextField)component;
            return jTextField.getText();
        }
        //Fall back which should never happen
        return component.toString();
    }

    private void updateLocalConfiguration() {
       for (StiltsConfiguration configuration:selectors.keySet()){
           Object newValue = getTheValue(selectors.get(configuration));
           configuration.setItem(newValue);
       }
    }
 
    @Override
    public boolean isConfigurationChanged() {
        updateLocalConfiguration();
        return activity.configurations().equals(configurations);
//       for (StiltsConfiguration configuration:selectors.keySet()){
//           Object newValue = getTheValue(selectors.get(configuration));
//           if (!configuration.getItem().equals(newValue)){
//               return true;
//           }
//        }
//       return false;
    }

    @Override
    public StiltsBean getConfiguration() {
        return activity.getConfiguration();
    }

    @Override
    public void noteConfiguration() {
        //for (int i = 0; i < configurations.size(); i++){
        //}
    }
    
    static Component getSelector(Object item) {
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
    private void addAConfiguration(StiltsConfiguration configuration) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        JLabel label = new JLabel(configuration.getName());
        add(label, c);
        c.gridx = 1;
        Component selector = getSelector(configuration.getItem());
        selectors.put(configuration, selector);
        add(selector, c);
        row++;
    }

    private void addAdjustButtons(final ListConfiguration configuration) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        JButton add = new JButton("Add " + configuration.getName());
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                configuration.addToLists();
                updateLocalConfiguration();
                refreshConfiguration();
            }
        });      
        add(add, c);
        c.gridx = 1;
        final JButton remove = new JButton("Remove last " + configuration.getName());
        Component selector = getSelector(configuration.getItem());
        add(remove, c);
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                configuration.deleteLastFromLists();
                updateLocalConfiguration();
                refreshConfiguration();
            }
        });    
        Integer size = (Integer)configuration.getItem();
        if (size <= 1){
            remove.setEnabled(false);
        } else {
            remove.setEnabled(true);            
        }
        row++;
   }

    private void addConfiguration(StiltsConfiguration configuration) {
        if (configuration instanceof ListConfiguration){
            ListConfiguration listConfiguration = (ListConfiguration)configuration;
            for (StiltsConfiguration inner:listConfiguration.getConfigurations()){
                addAConfiguration(inner);
            }
            if (listConfiguration.hasAdjustableCount()){
                addAdjustButtons(listConfiguration);
            }
        } else {
            addAConfiguration(configuration);
        }
    }
   
    private void addTitle(String title) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel label = new JLabel(title, JLabel.CENTER);
        add(label, c);
   }

    @Override
    public final void refreshConfiguration() {
        removeAll();
        selectors = new HashMap<StiltsConfiguration, Component>();
        setLayout(new GridBagLayout());
        row = 0;
        for (ConfigurationGroup group:configurations.getGroups()){
            addTitle(group.getTitle());
            row++;
            for (StiltsConfiguration configuration:group.getConfigurations()){
                addConfiguration(configuration);
            }
        }
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null){
            window.pack();
            System.out.println("window pack");
        } else {
            repaint();
            System.out.println("me repaint");
        }
        //if (this.getGraphics() != null){
        //    this.paintChildren(this.getGraphics());
        //}
    }

    @Override
    public boolean checkValues() {
        //TODO
        return true;
    }


  
}
