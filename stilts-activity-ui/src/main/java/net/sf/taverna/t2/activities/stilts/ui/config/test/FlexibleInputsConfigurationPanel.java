package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.sf.taverna.t2.activities.stilts.test.FlexibleInputsBean;
import static net.sf.taverna.t2.activities.stilts.ui.config.MultipleInputsConfigurationPanel.INPUT_TYPE_LABEL;
import static net.sf.taverna.t2.activities.stilts.ui.config.MultipleInputsConfigurationPanel.NUMBER_OF_INPUTS;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class FlexibleInputsConfigurationPanel extends MultipleFormatsConfigurationPanel<FlexibleInputsBean>
        implements DocumentListener{
 
    private List<JComboBox> inputsFormatsSelectors;
    private int oldNumberOfInputs;
    private final String NUMBER_OF_INPUTS_LABEL = "Number of Input Tables";
    private JTextField numberOfInputsField; 
            
    FlexibleInputsConfigurationPanel(FlexibleInputsBean inputBean, boolean editable){
        super(inputBean, editable, 1);
        oldNumberOfInputs = inputBean.getNumberOfInputs();
    }
    
    @Override
    void initGui() {
        super.initGui();        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        if (editable){
            JPanel numberOfInputsPanel = new JPanel();
            JLabel numberOfInputsLabel = new JLabel(NUMBER_OF_INPUTS_LABEL);
            numberOfInputsPanel.add(numberOfInputsLabel);
            numberOfInputsField = new JTextField(getNumberOfInputs()+"  ");
            numberOfInputsField.getDocument().addDocumentListener(this);
            numberOfInputsPanel.add(numberOfInputsField);
            JLabel warningLabel = new JLabel("Warning: Set this first!");  
            numberOfInputsPanel.add(warningLabel);        
            add(numberOfInputsPanel, c);
        } else {
            JLabel numberOfInputsLabel = new JLabel(NUMBER_OF_INPUTS_LABEL + ": " + getNumberOfInputs());
            add(numberOfInputsLabel, c);
        }
    }

    @Override
    int getNumberOfInputs() {
        //Must come from the bean as is called before 
        return inputBean.getNumberOfInputs();
    }

    /**
      * Check that user values in UI are valid
      * @return true if and only if no errors found
      */
    @Override
    public boolean checkValues() {
        if (!super.checkValues()){
            return false;
        }
        String numberOfInputsString = numberOfInputsField.getText();
        if (numberOfInputsString == null || numberOfInputsString.isEmpty()){
            String message = NUMBER_OF_INPUTS + " must be specified";
            JOptionPane.showMessageDialog(this, message, "Missing " + NUMBER_OF_INPUTS, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int numberOfInputs;
        try{
            numberOfInputs = Integer.parseInt(numberOfInputsString);
        } catch (NumberFormatException ex){
            String message = NUMBER_OF_INPUTS + " is not a valid possitive integer.";
            JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_INPUTS, JOptionPane.ERROR_MESSAGE);
            return false;            
        }
        if (numberOfInputs < 2){
            String message = NUMBER_OF_INPUTS + " should be 2 or more.";
            JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_INPUTS, JOptionPane.ERROR_MESSAGE);
            return false;                        
        }
        return true;
    }

    @Override
    public void noteConfiguration() {
        inputBean = new FlexibleInputsBean();
        inputBean.setNumberOfInputs(oldNumberOfInputs);
        super.noteConfiguration();
    }

    private void checkNumberOfInputsChanged() {
        if (numberOfInputsField.getText().isEmpty()){
           return;
        }
        int newNumberOfInputs = Integer.parseInt(numberOfInputsField.getText().trim());
        if (newNumberOfInputs < 2){
            return;
        }
        if (oldNumberOfInputs != newNumberOfInputs){
            oldNumberOfInputs = newNumberOfInputs;
            //Using resetNumberOfInputs will get the bean to add Type and format values to the list if applicable
            inputBean.resetNumberOfInputs(newNumberOfInputs);
            initGui();
            this.revalidate();
            SwingUtilities.getWindowAncestor(this).pack();
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
