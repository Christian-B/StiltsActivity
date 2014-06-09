package net.sf.taverna.t2.activities.stilts.ui.config.input;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.sf.taverna.t2.activities.stilts.input.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.ui.textfield.IntegerTextField;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;

/**
 * Configuration panel for multiple inputs which have the same format but possibly different types 
 * Allows various processes to share the same configuration code if they share the same type of input.
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> Any type of MultipleInputsBean
 */
@SuppressWarnings("serial")
public abstract class MultipleInputsConfigurationPanel<BoundedBean extends MultipleInputsBean> 
        extends StiltsInputConfigurationPanel<BoundedBean> implements DocumentListener{
 
    private List<JComboBox<StiltsInputType>> inputsTypesSelectors;
    private List<JComboBox> inputsFormatsSelectors;
    int oldNumberOfInputs;
    final int headerRows;

    private final String NUMBER_OF_INPUTS_LABEL = "Number of Input Tables";
    private IntegerTextField numberOfInputsField; 
    private final boolean adjustableNumberOfInputs;
    static final boolean ADJUSTABLE_NUMBER_OF_INPUT_TABLES = true;        
    static final boolean FIXED_NUMBER_OF_INPUT_TABLES = false;        

    MultipleInputsConfigurationPanel(BoundedBean inputBean, boolean adjustableNumberOfInputs){
        super(inputBean);
        this.adjustableNumberOfInputs = adjustableNumberOfInputs;
        oldNumberOfInputs = getNumberOfInputs();
        if (adjustableNumberOfInputs){
            headerRows = 1;
        } else {
            headerRows = 0;            
        }
        initGui();
    }
    
   void initGui() {
        removeAll();
        resetSelectors();
        
        if (adjustableNumberOfInputs){
            addNextRow(new JLabel(NUMBER_OF_INPUTS_LABEL),1);
            numberOfInputsField = new IntegerTextField();
            numberOfInputsField.getDocument().addDocumentListener(this);
            addNextCol(numberOfInputsField, 1);
            numberOfInputsField.setValue(this.inputBean.retreiveNumberOfInputs());
            addNextRow(new JLabel("Warning: Set number of input tables first!"),1);  
        }
        
        addAllRow();
        addHeaderRow();
        for (int i = 0; i < getNumberOfInputs(); i++){
            addTableRow(i);
        }
    }
    
    void resetSelectors() {
        inputsTypesSelectors = new ArrayList<JComboBox<StiltsInputType>>();
    }

    abstract void addAllRow();

    void addHeaderRow(){
        addNextRow(new JLabel(""),1);
        addNextCol(new JLabel("Input Type"),1);
    }
    
    void addTableRow(int i){
        addNextRow(new JLabel("Input Table " + (i+1)),1);
        JComboBox<StiltsInputType> box = new JComboBox<StiltsInputType>(StiltsInputType.values());
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        box.setSelectedItem(types.get(i));
        box.setRenderer(listCellRenderer);
        addNextCol(box, 1);
        inputsTypesSelectors.add(box);
    }
       
    /**
      * Check that user values in UI are valid
      * @return true if and only if no errors found
      */
    @Override
    public boolean checkValues() {
        if (adjustableNumberOfInputs){
            String numberOfInputsString = numberOfInputsField.getText();
            if (numberOfInputsString == null || numberOfInputsString.isEmpty()){
                String message = NUMBER_OF_INPUTS_LABEL + " must be specified";
                JOptionPane.showMessageDialog(this, message, "Missing " + NUMBER_OF_INPUTS_LABEL, JOptionPane.ERROR_MESSAGE);
                return false;
            }
            int numberOfInputs;
            try{
                numberOfInputs = Integer.parseInt(numberOfInputsString);
            } catch (NumberFormatException ex){
                String message = NUMBER_OF_INPUTS_LABEL + " is not a valid possitive integer.";
                JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_INPUTS_LABEL, JOptionPane.ERROR_MESSAGE);
                return false;            
            }
            if (numberOfInputs < 2){
                String message = NUMBER_OF_INPUTS_LABEL + " should be 2 or more.";
                JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_INPUTS_LABEL, JOptionPane.ERROR_MESSAGE);
                return false;                        
            }
        }
        return true;
    }
    
    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged(){
        if (adjustableNumberOfInputs){
            String numberOfInputsString = numberOfInputsField.getText();
            if (numberOfInputsString == null || numberOfInputsString.isEmpty()){
                return true;
            }
            int numberOfInputs;
            try{
                numberOfInputs = Integer.parseInt(numberOfInputsString);
            } catch (NumberFormatException ex){
                return true;            
            }
            if (numberOfInputs !=  oldNumberOfInputs){
                return true;                        
            }
        }
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        for (int i = 0; i < getNumberOfInputs(); i++){
            if (!inputsTypesSelectors.get(i).getSelectedItem().equals(types.get(i))){
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
    public void noteConfiguration(){
        ArrayList<StiltsInputType> types = new ArrayList<StiltsInputType>();
        for (int i = 0; i < getNumberOfInputs(); i++){
            types.add((StiltsInputType)inputsTypesSelectors.get(i).getSelectedItem());
        }
        inputBean.setTypesOfInputs(types);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
     @Override
     public void refreshConfiguration(BoundedBean inputBean){
        super.refreshConfiguration(inputBean);
        initGui();
    }

    abstract int getNumberOfInputs();
    
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
