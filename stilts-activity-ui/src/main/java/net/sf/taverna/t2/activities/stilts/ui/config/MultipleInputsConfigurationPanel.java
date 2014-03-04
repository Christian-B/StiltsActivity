package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.MultipleInputsTypeActivity;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class MultipleInputsConfigurationPanel 
        <BoundedActivity extends MultipleInputsTypeActivity, 
        BoundedBean extends MultipleInputsBean>  extends
        AbstractStiltsConfigurationPanel<BoundedActivity, BoundedBean> 
        implements DocumentListener{

    private JTextField numberOfInputsField;
    private int numberOfInputs;
    private List<JComboBox> inputsTypesSelectors;
            
    public static final String NUMBER_OF_INPUTS = "Number of input tables";
    public static final String INPUT_TYPE_LABEL = "Input Type";
            
    public MultipleInputsConfigurationPanel(BoundedActivity activity) {
        super(activity);
        initGui();
    }

    protected void initGui() {
        super.initGui();
  
        JLabel labelNumberOfInputs = new JLabel(NUMBER_OF_INPUTS  + ": ");
        inputPanel.add(labelNumberOfInputs);
        numberOfInputs = configBean.getNumberOfInputs();
        numberOfInputsField = new JTextField(numberOfInputs+"");
        numberOfInputsField.getDocument().addDocumentListener(this);
        inputPanel.add(numberOfInputsField);
        labelNumberOfInputs.setLabelFor(numberOfInputsField);
        
        JLabel setFirstLabel = new JLabel("Warning set the number of inputs first.");
        inputPanel.add(setFirstLabel);
        inputPanel.add(new JLabel(""));
        
        inputsTypesSelectors = new ArrayList<JComboBox>();
        for (int i = 1; i<= numberOfInputs; i++){
            JLabel labelInputType = new JLabel(INPUT_TYPE_LABEL + " (table "+ i + "): ");
            inputPanel.add(labelInputType);
            JComboBox inputTypeSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_TYPE_ARRAY);
            inputPanel.add(inputTypeSelector);
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
        for (int i = 0; i< configBean.getNumberOfInputs(); i++){
            if (!StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST.contains(inputsTypesSelectors.get(i).getSelectedItem())){
                String message = inputsTypesSelectors.get(i).getSelectedItem() + 
                        " Used for " + INPUT_TYPE_LABEL + (i+1) +  
                        " Is not a valid input type. Valid types are: " + StiltsConfigurationConstants.VALID_INPUT_TYPE_LIST;
                JOptionPane.showMessageDialog(this, "test", "Illegal type ", JOptionPane.ERROR_MESSAGE);
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
        if (configBean.getNumberOfInputs() != numberOfInputs){
           return true;
        }
        for (int i = 0; i < numberOfInputs; i++){
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
        noteConfiguration(new MultipleInputsBean());
    }

    protected void noteConfiguration(MultipleInputsBean bean) {
    	super.noteConfiguration(bean);
        configBean.setNumberOfInputs(numberOfInputs);
        ArrayList<String> types = new ArrayList<String>(0);
        for (int i = 0; i < numberOfInputs; i++){
            types.add(inputsTypesSelectors.get(i).getSelectedItem().toString());
        }
        configBean.setTypesOfInputs(types);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();
        numberOfInputs = configBean.getNumberOfInputs();
        numberOfInputsField.setText(numberOfInputs+"");
        for (int i = 0; i < numberOfInputs; i++){
            inputsTypesSelectors.get(i).setSelectedItem(configBean.getTypesOfInputs().get(i));
        }
    }
    
    private void checkNumberOfInputsChanged() {
        if (numberOfInputsField.getText().isEmpty()){
           return;
        }
        int newNumberOfInputs = Integer.parseInt(numberOfInputsField.getText());
        if (numberOfInputs != newNumberOfInputs){
            numberOfInputs = newNumberOfInputs;
            configBean.setNumberOfInputs(numberOfInputs);
            updateIndividualInputFields();
            initGui();
            this.revalidate();
            SwingUtilities.getWindowAncestor(this).pack();
        }
    }

    private void updateIndividualInputFields() {
        for (int i = configBean.getTypesOfInputs().size(); i < numberOfInputs; i++){
            configBean.getTypesOfInputs().add(configBean.getTypesOfInputs().get(0));
       }
    }

    public void changedUpdate(DocumentEvent de) {
        checkNumberOfInputsChanged();
    }
    public void insertUpdate(DocumentEvent de) {
        checkNumberOfInputsChanged();
    }

    public void removeUpdate(DocumentEvent de) {
        checkNumberOfInputsChanged();
    }

}
