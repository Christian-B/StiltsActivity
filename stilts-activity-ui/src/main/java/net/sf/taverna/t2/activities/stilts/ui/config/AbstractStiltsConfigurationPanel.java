package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.AbstractStiltsBean;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.AbstractStilsActivity;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

@SuppressWarnings("serial")
public class AbstractStiltsConfigurationPanel<StiltsActivityType extends AbstractStilsActivity, BoundedBean extends AbstractStiltsBean>  extends
        ActivityConfigurationPanel<StiltsActivityType, BoundedBean> {

    protected final StiltsActivityType activity;
    protected BoundedBean configBean;
	
    private JComboBox outputFormatSelector;
    private JComboBox outputTypeSelector;
    private JCheckBox debugSelector;
            
    private static final String OUTPUT_FORMAT_LABEL = "Output Format";
    private static final String OUTPUT_TYPE_LABEL = "Output Type";
    private static final String DEBUG_LABEL = "Debug mode";
            
    public AbstractStiltsConfigurationPanel(StiltsActivityType activity) {
        this.activity = activity;
        configBean = (BoundedBean)activity.getConfiguration();
        initGui();
    }

    protected void initGui() {
        removeAll();
        setLayout(new GridLayout(0, 2));

        JLabel labelOutputFormat = new JLabel(OUTPUT_FORMAT_LABEL + ": ");
        add(labelOutputFormat);
        outputFormatSelector = new JComboBox(StiltsConfigurationConstants.VALID_OUTPUT_FORMATS_ARRAY);
        add(outputFormatSelector);
        labelOutputFormat.setLabelFor(outputFormatSelector);

        JLabel labelOutputType = new JLabel(OUTPUT_TYPE_LABEL + ": ");
        add(labelOutputType);
        outputTypeSelector = new JComboBox(StiltsConfigurationConstants.VALID_OUTPUT_TYPE_ARRAY);
        add(outputTypeSelector);
        labelOutputType.setLabelFor(outputTypeSelector);

        JLabel labelDebug = new JLabel(DEBUG_LABEL + ": ");
        add(labelDebug);
        debugSelector = new JCheckBox();
        add(debugSelector);
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
