package net.sf.taverna.t2.activities.stilts.ui.config;

import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.SingleInputActivity;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class SingleInputConfigurationPanel <BoundedActivity extends SingleInputActivity, 
        BoundedBean extends SingleInputBean>  extends
        AbstractStiltsConfigurationPanel<BoundedActivity, BoundedBean> {

    private JComboBox inputFormatSelector;
    private JComboBox inputTypeSelector;
            
    public static final String INPUT_FORMAT_LABEL = "Input Format";
    public static final String INPUT_TYPE_LABEL = "Input Type";
            
    public SingleInputConfigurationPanel(BoundedActivity activity) {
        super(activity);
    }

    protected void initGui() {
        super.initGui();
  
        JLabel labelInputFormatType = new JLabel(INPUT_FORMAT_LABEL + ": ");
        inputPanel.add(labelInputFormatType);
        inputFormatSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_FORMATS_ARRAY);
        inputTypeSelector.setSelectedItem(configBean.getTypeOfInput());
        inputPanel.add(inputFormatSelector);
        labelInputFormatType.setLabelFor(inputFormatSelector);

        JLabel labelInputType = new JLabel(INPUT_TYPE_LABEL + ": ");
        inputPanel.add(labelInputType);
        inputTypeSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_TYPE_ARRAY);
        inputFormatSelector.setSelectedItem(configBean.getFormatOfInput());
        inputPanel.add(inputTypeSelector);
        labelInputType.setLabelFor(inputTypeSelector);
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
            JOptionPane.showMessageDialog(this, message, "Illegal " + INPUT_FORMAT_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST.contains(inputTypeSelector.getSelectedItem())){
            String message = inputTypeSelector.getSelectedItem() + 
                    " Used for " + INPUT_TYPE_LABEL + 
                    " Is not a valid input type. Valid types are: " + StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST;
            JOptionPane.showMessageDialog(this, message, "Illegal " + INPUT_TYPE_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
         // All valid, return true
        return true;
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

    protected void noteConfiguration(SingleInputBean bean) {
    	super.noteConfiguration(bean);
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
        inputFormatSelector.setSelectedItem(configBean.getFormatOfInput());
        inputTypeSelector.setSelectedItem(configBean.getTypeOfInput());
    }
}
