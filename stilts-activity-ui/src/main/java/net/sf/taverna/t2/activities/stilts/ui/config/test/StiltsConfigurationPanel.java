package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.test.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.test.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.test.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.test.FlexibleInputsBean;
import net.sf.taverna.t2.activities.stilts.test.StitlsInputsBean;
import net.sf.taverna.t2.activities.stilts.test.StiltsProcessBean;
import net.sf.taverna.t2.activities.stilts.test.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.test.StiltsBean;
import net.sf.taverna.t2.activities.stilts.test.TCatBean;
import net.sf.taverna.t2.activities.stilts.test.TCatNBean;
import net.sf.taverna.t2.activities.stilts.test.TJoinBean;
import net.sf.taverna.t2.activities.stilts.test.TPipeBean;
import net.sf.taverna.t2.activities.stilts.test.TwoInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

@SuppressWarnings("serial")
public class StiltsConfigurationPanel extends
        ActivityConfigurationPanel<StiltsActivity, StiltsBean> {

    private final StiltsActivity activity;
    private StiltsBean configBean;
    private final boolean editable;
    
    private final StiltsProcessConfigurationPanel processPanel;
    private final JPanel outputPanel;
    private final JPanel miscellaneousPanel;
    
    private JComboBox<StiltsOutputFormat> outputFormatSelector;
    private JComboBox<StiltsOutputType> outputTypeSelector;
    private JCheckBox debugSelector;
    private final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();        
    
    public static final String OUTPUT_FORMAT_LABEL = "Output Format";
    public static final String OUTPUT_TYPE_LABEL = "Output Type";
    public static final String DEBUG_LABEL = "Debug mode";
            
    public static final boolean NOT_EDITABLE = false;
    public static final boolean EDITABLE = true;
    
    public StiltsConfigurationPanel(StiltsActivity activity) {
        this(activity, EDITABLE);
    }
    
    public StiltsConfigurationPanel(StiltsActivity activity, boolean editable) {
        this.editable = editable;
        this.activity = activity;
        configBean = activity.getConfiguration();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        processPanel = createProcessPanel(configBean.getProcess());
        add(processPanel, c);        
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

     private void initGui() {
        outputPanel.removeAll();
        miscellaneousPanel.removeAll();

        JLabel outputFormatLabel = new JLabel(OUTPUT_FORMAT_LABEL + ": ");
        outputPanel.add(outputFormatLabel);
        StiltsOutputFormat format = configBean.getOutputFormat();
        if (editable){
            outputFormatSelector = new JComboBox<StiltsOutputFormat>(StiltsOutputFormat.values());
            outputFormatSelector.setSelectedItem(format);
            outputFormatSelector.setRenderer(listCellRenderer);
            outputPanel.add(outputFormatSelector);
            outputFormatLabel.setLabelFor(outputFormatSelector);
        } else {
            JLabel outputFormatInfo = new JLabel(format.toString());
            outputFormatInfo.setToolTipText(format.getDescription());
            outputPanel.add(outputFormatInfo);
        }

        JLabel outputTypeLabel = new JLabel(OUTPUT_TYPE_LABEL + ": ");
        outputPanel.add(outputTypeLabel);
        StiltsOutputType type = configBean.getOutputType();
        if (editable){
            outputTypeSelector = new JComboBox<StiltsOutputType>(StiltsOutputType.values());
            outputTypeSelector.setSelectedItem(type);
            outputTypeSelector.setRenderer(listCellRenderer);
            outputPanel.add(outputTypeSelector);
            outputTypeLabel.setLabelFor(outputTypeSelector);
        } else {
            JLabel outputTypeInfo = new JLabel(type.getUserName());
            outputTypeInfo.setToolTipText(type.getDescription());
            outputPanel.add(outputTypeInfo);
        }
 
        JLabel debugLabel = new JLabel(DEBUG_LABEL + ": ");
        miscellaneousPanel.add(debugLabel);
        if (editable){
            debugSelector = new JCheckBox();
            debugSelector.setSelected(configBean.isDebugMode());
            miscellaneousPanel.add(debugSelector);
            debugLabel.setLabelFor(debugSelector);
        } else {
            JLabel debugInfo;
            if (configBean.isDebugMode()){
                debugInfo = new JLabel(" will be provided.");
            } else {
                debugInfo = new JLabel(" off.");                
            }
            miscellaneousPanel.add(debugInfo);
        }
    }
 
    /**
      * Check that user values in UI are valid
      */
    @Override
    public boolean checkValues() {
        if (!editable){
            String message = "Weird checkValues() should never have been called!";
            JOptionPane.showMessageDialog(this, message, "Weird", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        processPanel.checkValues();
         // All valid, return true
        return true;
    }

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
      */
    @Override
    public StiltsBean getConfiguration() {
        // Should already have been made by noteConfiguration()
         return configBean;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (!editable){
            return false; //not editable so never changed!
        }
        if (!configBean.getOutputFormat().equals(outputFormatSelector.getSelectedItem())){
            return true;
        }
        if (!configBean.getOutputType().equals(outputTypeSelector.getSelectedItem())){
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
        if (!editable){
            return; //Should never be called but just in case do nothing
        }
        configBean = new StiltsBean();    	
        configBean.setOutputFormat((StiltsOutputFormat)outputFormatSelector.getSelectedItem());
        configBean.setOutputType((StiltsOutputType)outputTypeSelector.getSelectedItem());
        configBean.setDebugMode(debugSelector.isSelected());
        processPanel.noteConfiguration();
        configBean.setProcess(processPanel.getConfiguration());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        if (!editable){
            throw new UnsupportedOperationException("refreshConfiguration() on none editable panel");
        }
        configBean = activity.getConfiguration();
		
        outputFormatSelector.setSelectedItem(configBean.getOutputFormat());
        outputTypeSelector.setSelectedItem(configBean.getOutputType());
        debugSelector.setSelected(configBean.isDebugMode());
        
        processPanel.refreshConfiguration(configBean.getProcess());
    }

    private StiltsProcessConfigurationPanel createProcessPanel(StiltsProcessBean processBean) {
        StiltsInputConfigurationPanel inputPanel;
        StitlsInputsBean inputBean = processBean.getInputs();
        if (inputBean instanceof FlexibleInputsBean){
            inputPanel = new FlexibleInputsConfigurationPanel((FlexibleInputsBean)inputBean, editable);
        } else if (inputBean instanceof SingleInputBean){
            inputPanel = new SingleInputConfigurationPanel((SingleInputBean)inputBean, editable);
        } else if (inputBean instanceof SingleFormatMultipleInputsBean){
            inputPanel = new SingleFormatMultipleInputsConfigurationPanel((SingleFormatMultipleInputsBean)inputBean, editable);
        } else if (inputBean instanceof TwoInputsBean){
            inputPanel = new TwoInputsConfigurationPanel((TwoInputsBean)inputBean, editable);
        } else {
             System.err.println("Unexpected input bean class: " + inputBean.getClass());
             throw new UnsupportedOperationException("Unexpected input bean class: " + inputBean.getClass());
        }   
        System.out.println(processBean + " -> " + inputBean);
        if (processBean instanceof TPipeBean){
            return new TPipeConfigurationPanel((TPipeBean)processBean, (SingleInputConfigurationPanel)inputPanel);
        } else if (processBean instanceof TCatBean){
            return new TCatConfigurationPanel((TCatBean)processBean, (SingleFormatMultipleInputsConfigurationPanel)inputPanel);
        } else if (processBean instanceof TCatNBean){
            return new TCatNConfigurationPanel((TCatNBean)processBean, (MultipleFormatsConfigurationPanel)inputPanel);
        } else if (processBean instanceof TJoinBean){
            return new TJoinConfigurationPanel((TJoinBean)processBean, (MultipleFormatsConfigurationPanel)inputPanel);
        } else if (processBean instanceof ExactMatchBean){
            return new ExactMatchConfigurationPanel((ExactMatchBean)processBean, (TwoInputsConfigurationPanel)inputPanel, editable);
        } else{
             System.err.println("Unexpected process bean class: " + inputBean.getClass());
             throw new UnsupportedOperationException("Unexpected process bean class: " + processBean.getClass());
        }   
    }
}
