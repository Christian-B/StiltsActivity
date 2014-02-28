package net.sf.taverna.t2.activities.stilts.ui.config;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.MultipleInputsTypeActivity;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class MultipleInputsConfigurationPanel <InputActivityType extends MultipleInputsTypeActivity, InputType extends MultipleInputsBean>  extends
        AbstractStiltsConfigurationPanel<InputActivityType, InputType> {

    private JTextField numberOfInputsField;
    private List<JComboBox> inputsTypesSelectors;
            
    private static final String NUMBER_OF_INPUTS = "Number of input tables";
    private static final String INPUT_TYPE_LABEL = "Input Type";
            
    public MultipleInputsConfigurationPanel(InputActivityType activity) {
        super(activity);
        initGui();
    }

    protected void initGui() {
        super.initGui();
  
        JLabel labelNumberOfInputs = new JLabel(NUMBER_OF_INPUTS  + ": ");
        add(labelNumberOfInputs);
        numberOfInputsField = new JTextField(configBean.getNumberOfInputs());
        add(numberOfInputsField);
        labelNumberOfInputs.setLabelFor(numberOfInputsField);

        JLabel setFirstLabel = new JLabel("Warning set the number of inputs first.");
        add(setFirstLabel);

        inputsTypesSelectors = new ArrayList<JComboBox>();
        for (int i = 1; i<= configBean.getNumberOfInputs(); i++){
            JLabel labelInputType = new JLabel(INPUT_TYPE_LABEL + " (table "+ i + "): ");
            add(labelInputType);
            JComboBox inputTypeSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_TYPE_ARRAY);
            add(inputTypeSelector);
            labelInputType.setLabelFor(inputTypeSelector);
            inputsTypesSelectors.add(inputTypeSelector);
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
        String numberOfInputsString = numberOfInputsField.getText();
        if (numberOfInputsString == null || numberOfInputsString.isEmpty()){
            String message = NUMBER_OF_INPUTS + " must be specified";
            JOptionPane.showMessageDialog(this, "test", "Missing " + NUMBER_OF_INPUTS, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int numberOfInputs;
        try{
            numberOfInputs = Integer.parseInt(numberOfInputsString);
        } catch (NumberFormatException ex){
            String message = NUMBER_OF_INPUTS + " is not a valid possitive integer.";
            JOptionPane.showMessageDialog(this, "test", "Invalid " + NUMBER_OF_INPUTS, JOptionPane.ERROR_MESSAGE);
            return false;            
        }
        if (numberOfInputs < 2){
            String message = NUMBER_OF_INPUTS + " should be 2 or more.";
            JOptionPane.showMessageDialog(this, "test", "Invalid " + NUMBER_OF_INPUTS, JOptionPane.ERROR_MESSAGE);
            return false;                        
        }
        for (int i = 1; i<= configBean.getNumberOfInputs(); i++){
            if (!StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST.contains(inputsTypesSelectors.get(i).getSelectedItem())){
                String message = inputsTypesSelectors.get(i).getSelectedItem() + 
                        " Used for " + INPUT_TYPE_LABEL + i +  
                        " Is not a valid input type. Valid types are: " + StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST;
                JOptionPane.showMessageDialog(this, "test", "Illegal type ", JOptionPane.ERROR_MESSAGE);
                return false;
            }
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
        if (!(configBean.getNumberOfInputs() + "").equals(this.numberOfInputsField.getText())){
            initGui();
            return true;
        }
        for (int i = 0; i < configBean.getNumberOfInputs(); i++){
            String beanType = configBean.getTypesOfInputs().get(i);
            Object configType = inputsTypesSelectors.get(i).getSelectedItem();
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
        noteConfiguration(new SingleInputBean());
    }

    protected void noteConfiguration(InputType bean) {
    	super.noteConfiguration(configBean);
        String numberOfInputsString = numberOfInputsField.getText();
        try{
            int numberOfInputs = Integer.parseInt(numberOfInputsString);
            configBean.setNumberOfInputs(numberOfInputs);
        } catch (NumberFormatException ex){
        }
       //         .setFormatOfInput(inputFormatSelector.getSelectedItem().toString());
        //configBean.setTypeOfInput(inputTypeSelector.getSelectedItem().toString());        
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();
        configBean = (InputType)activity.getConfiguration();
        numberOfInputsField.setText(configBean.getNumberOfInputs()+"");
        for (int i = 0; i < configBean.getNumberOfInputs(); i++){
            inputsTypesSelectors.get(i).setSelectedItem(configBean.getTypesOfInputs().get(i));
        }
    }
}
