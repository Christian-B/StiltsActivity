package net.sf.taverna.t2.activities.stilts.ui.config;

import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.SingleInputActivity;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.AbstractStilsActivity;
import net.sf.taverna.t2.activities.stilts.AbstractStiltsBean;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;

@SuppressWarnings("serial")
public class InputTypeConfigurationPanel <InputActivityType extends SingleInputActivity, InputType extends SingleInputBean>  extends
        AbstractStiltsConfigurationPanel<InputActivityType, InputType> {

    private final SingleInputActivity activity;
    private InputType configBean;
	
    private JComboBox inputFormatSelector;
    private JComboBox inputTypeSelector;
            
    private static final String INPUT_FORMAT_LABEL = "Input Format";
    private static final String INPUT_TYPE_LABEL = "Input Type";
            
    public InputTypeConfigurationPanel(InputActivityType activity) {
        super(activity);
        this.activity = activity;
        configBean = (InputType)activity.getConfiguration();
        initGui();
    }

    protected void initGui() {
        super.initGui();
  
        JLabel labelInputFormatType = new JLabel(INPUT_FORMAT_LABEL + ": ");
        add(labelInputFormatType);
        inputFormatSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_FORMATS_ARRAY);
        add(inputFormatSelector);
        labelInputFormatType.setLabelFor(inputFormatSelector);

        JLabel labelInputType = new JLabel(INPUT_TYPE_LABEL + ": ");
        add(labelInputType);
        inputTypeSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_TYPE_ARRAY);
        add(inputTypeSelector);
        labelInputType.setLabelFor(inputTypeSelector);

        // Populate fields from activity configuration bean
        refreshConfiguration();
    }

    /**
      * Check that user values in UI are valid
      */
    @Override
    public boolean checkValues() {
        if (!super.checkValues()){
            return false;
        }
        if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(inputFormatSelector.getSelectedItem())){
            String message = inputFormatSelector.getSelectedItem() + 
                    " Used for " + INPUT_FORMAT_LABEL + 
                    " Is not a valid Input format. Valid formats are: " + StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST;
            JOptionPane.showMessageDialog(this, "test", "Illegal format", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST.contains(inputTypeSelector.getSelectedItem())){
            String message = inputFormatSelector.getSelectedItem() + 
                    " Used for " + INPUT_FORMAT_LABEL + 
                    " Is not a valid input type. Valid types are: " + StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST;
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
    public InputType getConfiguration() {
        // Should already have been made by noteConfiguration()
        return configBean;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (!configBean.getFormatOfInput().equals(inputFormatSelector.getSelectedItem())){
            return true;
        }
        if (!configBean.getTypeOfInput().equals(inputTypeSelector.getSelectedItem())){
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
        noteConfiguration(new SingleInputBean());
    }

    protected void noteConfiguration(InputType bean) {
        configBean = (InputType) new SingleInputBean();
    	super.noteConfiguration(configBean);
        configBean.setFormatOfInput(inputFormatSelector.getSelectedItem().toString());
        configBean.setTypeOfInput(inputTypeSelector.getSelectedItem().toString());        
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();
        configBean = (InputType)activity.getConfiguration();
        
        inputFormatSelector.setSelectedItem(configBean.getFormatOfInput());
        inputTypeSelector.setSelectedItem(configBean.getTypeOfInput());
    }
}
