package net.sf.taverna.t2.activities.stilts.ui.config;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsActivity;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import static net.sf.taverna.t2.activities.stilts.ui.config.MultipleInputsConfigurationPanel.INPUT_TYPE_LABEL;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class MultipleFormatsConfigurationPanel 
        <BoundedActivity extends MultipleFormatsActivity, 
        BoundedBean extends MultipleFormatsBean>  extends
        MultipleInputsConfigurationPanel<BoundedActivity, BoundedBean> {
	
    private List<JComboBox> inputsFormatsSelectors;
            
    public static final String INPUT_FORMAT_LABEL = "Input Format";
            
    public MultipleFormatsConfigurationPanel(BoundedActivity activity) {
        super(activity);
    }

    protected void initGui() {
        super.initGui();
  
        inputsFormatsSelectors = new ArrayList<JComboBox>();
        for (int i = 1; i<= numberOfInputs; i++){
            JLabel labelInputFormat = new JLabel(INPUT_FORMAT_LABEL + " (table "+ i + "): ");
            inputPanel.add(labelInputFormat);
            JComboBox inputFormatSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_FORMATS_ARRAY);
            inputFormatSelector.setSelectedItem(configBean.getFormatsOfInputs().get(i-1));            
            inputPanel.add(inputFormatSelector);
            labelInputFormat.setLabelFor(inputFormatSelector);
            inputsFormatsSelectors.add(inputFormatSelector);
        }
    }

    /**
      * Check that user values in UI are valid
      */
    @Override
    public boolean checkValues() {
        if (!super.checkValues()){
            return false;
        }
        for (int i = 0; i< configBean.getNumberOfInputs(); i++){
            if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(inputsFormatsSelectors.get(i).getSelectedItem())){
                String message = inputsFormatsSelectors.get(i).getSelectedItem() + 
                        " Used for " + INPUT_FORMAT_LABEL + (i+1) +  
                        " Is not a valid input format. Valid types are: " + StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST;
                JOptionPane.showMessageDialog(this, message, "Illegal " + INPUT_FORMAT_LABEL + (i+1), JOptionPane.ERROR_MESSAGE);
                return false;
            }
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
        for (int i = 0; i < numberOfInputs; i++){
            String beanType = configBean.getFormatsOfInputs().get(i);
            Object configType = inputsFormatsSelectors.get(i).getSelectedItem();
            if (!beanType.equals(configType)){
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
        noteConfiguration(new SingleFormatMultipleInputsBean());
    }

    protected void noteConfiguration(SingleFormatMultipleInputsBean bean) {
    	super.noteConfiguration(configBean);
        ArrayList<String> formats = new ArrayList<String>(0);
        for (int i = 0; i < numberOfInputs; i++){
            formats.add(inputsFormatsSelectors.get(i).getSelectedItem().toString());
        }
        configBean.setFormatsOfInputs(formats);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();
        for (int i = 0; i < numberOfInputs; i++){
            inputsFormatsSelectors.get(i).setSelectedItem(configBean.getFormatsOfInputs().get(i));
        }
    }
    
    protected void updateIndividualInputFields() {
        super.updateIndividualInputFields();
        for (int i = configBean.getFormatsOfInputs().size(); i < numberOfInputs; i++){
            configBean.getFormatsOfInputs().add(configBean.getFormatsOfInputs().get(0));
       }
    }


}
