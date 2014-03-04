package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.AbstractStiltsBean;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.AbstractStilsActivity;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class AbstractStiltsConfigurationPanel<BoundedActivity extends AbstractStilsActivity, 
        BoundedBean extends AbstractStiltsBean>  extends
        ActivityConfigurationPanel<BoundedActivity, BoundedBean> {

    protected final BoundedActivity activity;
    protected BoundedBean configBean;
	
    protected final JPanel inputPanel;
    protected final JPanel outputPanel;
    protected final JPanel miscellaneousPanel;

    private JComboBox outputFormatSelector;
    private JComboBox outputTypeSelector;
    private JCheckBox debugSelector;
            
    public static final String OUTPUT_FORMAT_LABEL = "Output Format";
    public static final String OUTPUT_TYPE_LABEL = "Output Type";
    public static final String DEBUG_LABEL = "Debug mode";
            
    public AbstractStiltsConfigurationPanel(BoundedActivity activity) {
        this.activity = activity;
        configBean = (BoundedBean)activity.getConfiguration();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Inputs"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        add(inputPanel, c);
        outputPanel = new JPanel(new GridLayout(0, 2));
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Outputs"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        c.gridy = 1;
        add(outputPanel, c);
        miscellaneousPanel = new JPanel(new GridLayout(0, 2));
        c.gridy = 2;
        add(miscellaneousPanel, c);
        initGui();
    }

    protected void initGui() {
        inputPanel.removeAll();
        outputPanel.removeAll();
        miscellaneousPanel.removeAll();

        JLabel labelOutputFormat = new JLabel(OUTPUT_FORMAT_LABEL + ": ");
        outputPanel.add(labelOutputFormat);
        outputFormatSelector = new JComboBox(StiltsConfigurationConstants.VALID_OUTPUT_FORMATS_ARRAY);
        outputPanel.add(outputFormatSelector);
        labelOutputFormat.setLabelFor(outputFormatSelector);

        JLabel labelOutputType = new JLabel(OUTPUT_TYPE_LABEL + ": ");
        outputPanel.add(labelOutputType);
        outputTypeSelector = new JComboBox(StiltsConfigurationConstants.VALID_OUTPUT_TYPE_ARRAY);
        outputPanel.add(outputTypeSelector);
        labelOutputType.setLabelFor(outputTypeSelector);

        JLabel labelDebug = new JLabel(DEBUG_LABEL + ": ");
        miscellaneousPanel.add(labelDebug);
        debugSelector = new JCheckBox();
        miscellaneousPanel.add(debugSelector);
        labelDebug.setLabelFor(debugSelector);
    }

    /**
      * Check that user values in UI are valid
      */
    @Override
    public boolean checkValues() {
        if (!StiltsConfigurationConstants.VALID_OUTPUT_FORMATS_LIST.contains(outputFormatSelector.getSelectedItem())){
            String message = outputFormatSelector.getSelectedItem() + 
                    " Used for " + OUTPUT_FORMAT_LABEL + 
                    " Is not a valid output format. Valid formats are: " + StiltsConfigurationConstants.VALID_OUTPUT_FORMATS_LIST;
            JOptionPane.showMessageDialog(this, "test", "Illegal format", JOptionPane.ERROR_MESSAGE);
            return false;
        }         
        if (!StiltsConfigurationConstants.VALID_OUTPUT_TYPE_LIST.contains(outputTypeSelector.getSelectedItem())){
            String message = outputFormatSelector.getSelectedItem() + 
                    " Used for " + OUTPUT_FORMAT_LABEL + 
                    " Is not a valid output type. Valid types are: " + StiltsConfigurationConstants.VALID_OUTPUT_TYPE_LIST;
            JOptionPane.showMessageDialog(this, "test", "Illegal type ", JOptionPane.ERROR_MESSAGE);
            return false;
        }
         // All valid, return true
        return true;
    }

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
      */
    @Override
    public BoundedBean getConfiguration() {
        // Should already have been made by noteConfiguration()
        return configBean;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (!configBean.getFormatOfOutput().equals(outputFormatSelector.getSelectedItem())){
            return true;
        }
        if (!configBean.getTypeOfOutput().equals(outputTypeSelector.getSelectedItem())){
            return true;
        }
       
        if (configBean.isDebugMode() != debugSelector.isSelected()){
            return true;
        }
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
     public void noteConfiguration() {
        noteConfiguration(new AbstractStiltsBean());
     }
     
     protected void noteConfiguration(AbstractStiltsBean newBean) {
        configBean = (BoundedBean)newBean;    	
        configBean.setFormatOfOutput(outputFormatSelector.getSelectedItem().toString());
        configBean.setTypeOfOutput(outputTypeSelector.getSelectedItem().toString());
        configBean.setDebugMode(debugSelector.isSelected());
        
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        configBean = (BoundedBean)activity.getConfiguration();
		
        outputFormatSelector.setSelectedItem(configBean.getFormatOfOutput());
        outputTypeSelector.setSelectedItem(configBean.getTypeOfOutput());
        debugSelector.setSelected(configBean.isDebugMode());

    }
}
