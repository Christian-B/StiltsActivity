package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.sf.taverna.t2.activities.stilts.test.MultipleInputsBean;
import static net.sf.taverna.t2.activities.stilts.ui.config.MultipleInputsConfigurationPanel.NUMBER_OF_INPUTS;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;

@SuppressWarnings("serial")
public abstract class MultipleInputsConfigurationPanel<BoundedBean extends MultipleInputsBean> 
        extends StiltsInputConfigurationPanel<BoundedBean> implements DocumentListener{
 
    private List<JComboBox<StiltsInputType>> inputsTypesSelectors;
    private List<JComboBox> inputsFormatsSelectors;
    int oldNumberOfInputs;
    final int headerRows;

    private final String NUMBER_OF_INPUTS_LABEL = "Number of Input Tables";
    private JTextField numberOfInputsField; 
    private final boolean adjustableNumberOfInputs;
    static final boolean ADJUSTABLE_NUMBER_OF_INPUT_TABLES = true;        

    MultipleInputsConfigurationPanel(BoundedBean inputBean, boolean adjustableNumberOfInputs, boolean editable){
        super(inputBean, editable);
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
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        if (adjustableNumberOfInputs){
            if (editable){
                JPanel numberOfInputsPanel = new JPanel();
                JLabel numberOfInputsLabel = new JLabel(NUMBER_OF_INPUTS_LABEL);
                numberOfInputsPanel.add(numberOfInputsLabel);
                numberOfInputsField = new JTextField(getNumberOfInputs()+"");
                numberOfInputsField.setColumns(3);
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
        
        //Format Type Table Header
        c.gridx = 0;
        c.gridy = headerRows;
        JLabel label = new JLabel("");
        add(label, c);
        for (int i = 0; i < getNumberOfInputs(); i++){
            label = new JLabel("Table " + (i+1));
            c.gridx = i + 1;
            add(label, c);
        }
        
        c.gridx = 0;
        c.gridy = headerRows + 1;
        label = new JLabel(TYPE_LABEL);
        add(label, c);
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        if (editable){
            inputsTypesSelectors = new ArrayList<JComboBox<StiltsInputType>>();
            for (int i = 0; i < getNumberOfInputs(); i++){
                JComboBox<StiltsInputType> box = new JComboBox<StiltsInputType>(StiltsInputType.values());
                box.setSelectedItem(types.get(i));
                box.setRenderer(listCellRenderer);
                c.gridx = i + 1;
                add(box, c);
                inputsTypesSelectors.add(box);
            }
        } else {
            for (int i = 0; i < getNumberOfInputs(); i++){
                label = new JLabel(types.get(i).toString());
                label.setToolTipText(types.get(i).getDescription());
                c.gridx = i + 1;
                add(label, c);
            }            
        }
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
        }
        return true;
    }
    
    /**
      * Check if the user has changed the configuration from the original
      */
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
    public void refreshConfiguration(){
        if (adjustableNumberOfInputs){
            numberOfInputsField = new JTextField(getNumberOfInputs()+"");
        }
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        for (int i = 0; i < getNumberOfInputs(); i++){
            inputsTypesSelectors.get(i).setSelectedItem(types.get(i));
        }
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
