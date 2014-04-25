package net.sf.taverna.t2.activities.stilts.ui.config;

import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
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

import net.sf.taverna.t2.activities.stilts.*;
import net.sf.taverna.t2.activities.stilts.input.*;
import net.sf.taverna.t2.activities.stilts.process.*;
import net.sf.taverna.t2.activities.stilts.preprocess.*;
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
    private StiltsBean configBean;
    private final boolean editable;
    
    private final StiltsProcessConfigurationPanel processPanel;
    private final StiltsPreProcessConfigurationPanel preprocessPanel;
    private final JPanel outputPanel;
    /**
     * Last panel to add things like debug
     */
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
        try {
            this.editable = editable;
            this.activity = activity;
            configBean = activity.getConfiguration();
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            processPanel = createProcessPanel(configBean.getProcess());
            add(processPanel, c); 
        
            int row;
            StiltsPreProcessBean preprocessBean = configBean.getPreprocess();
            if (preprocessBean != null){
                preprocessPanel = createPreprocessPanel(preprocessBean);
                c.gridy = 1;
                add(preprocessPanel, c); 
                row = 2;
            } else {
                preprocessPanel = null;
                row = 1;
            }
        
            outputPanel = new JPanel(new GridLayout(0, 2));
            outputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Outputs"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
            c.gridy = row;
            add(outputPanel, c);
        
            miscellaneousPanel = new JPanel(new GridLayout(0, 2));
            c.gridy = row + 1;
            add(miscellaneousPanel, c);
        
            initGui();
        } catch (RuntimeException ex){
            ex.printStackTrace();
            throw ex;
        }     

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
        if (preprocessPanel != null){
            if (!preprocessPanel.checkValues()){
                return false;
            }
        }
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
        if (processPanel.isConfigurationChanged()){
            return true;
        }
        if (preprocessPanel != null){
            if (preprocessPanel.isConfigurationChanged()){
                return true;
            }
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
        if (preprocessPanel != null){
            preprocessPanel.noteConfiguration();
            configBean.setPreprocess(preprocessPanel.getConfiguration());
        } else {
            configBean.setPreprocess(null);            
        }
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
        if (preprocessPanel != null){
            preprocessPanel.refreshConfiguration(configBean.getPreprocess());
        }
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
             System.err.println("Unexpected process bean class: " + processBean.getClass());
             throw new UnsupportedOperationException("Unexpected process bean class: " + processBean.getClass());
        }   
    }

    private StiltsPreProcessConfigurationPanel createPreprocessPanel(StiltsPreProcessBean preprocessBean) {
        if (preprocessBean instanceof UserSpecifiedPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = new UserSpecifiedPreProcessorConfigurationPanel((UserSpecifiedPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof DeleteColumnPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = new DeleteColumnPreProcessorConfigurationPanel((DeleteColumnPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof KeepColumnPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = new KeepColumnPreProcessorConfigurationPanel((KeepColumnPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof AddColumnByCommandPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = 
                    new AddColumnByCommandPreProcessorConfigurationPanel((AddColumnByCommandPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof AddColumnOneVariablesPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = 
                    new AddColumnOneVariablePreProcessorConfigurationPanel((AddColumnOneVariablesPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof AddColumnTwoVariablesPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = 
                    new AddColumnTwoVariablePreProcessorConfigurationPanel((AddColumnTwoVariablesPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof SelectByCommandPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = 
                    new SelectByCommandPreProcessorConfigurationPanel((SelectByCommandPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof SelectTwoVariablesPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = 
                    new SelectTwoVariablePreProcessorConfigurationPanel((SelectTwoVariablesPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof HeadRowsPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = new HeadRowsPreProcessorConfigurationPanel((HeadRowsPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof TailRowsPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = new TailRowsPreProcessorConfigurationPanel((TailRowsPreProcessorBean)preprocessBean, editable);
            return panel;
        } else if (preprocessBean instanceof SortPreProcessorBean){
            StiltsPreProcessConfigurationPanel panel = new SortPreProcessorConfigurationPanel((SortPreProcessorBean)preprocessBean, editable);
            return panel;
        } else{
            System.err.println("Unexpected preprocess bean class: " + preprocessBean.getClass());
            throw new UnsupportedOperationException("Unexpected process bean class: " + preprocessBean.getClass());
        }      
    }
}
