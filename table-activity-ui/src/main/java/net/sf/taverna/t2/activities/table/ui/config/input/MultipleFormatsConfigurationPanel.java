package net.sf.taverna.t2.activities.table.ui.config.input;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import net.sf.taverna.t2.activities.table.input.MultipleFormatsBean;
import net.sf.taverna.t2.activities.table.utils.TableInputFormat;

/**
 * Based Configuration panel for multiple inputs which that can have different formats and types 
 * Super classes will allow the specification the number of input tables
 * Allows various processes to share the same configuration code if they share the same type of input.
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> Any type of MultipleFormatsBean
 */
@SuppressWarnings("serial")
public abstract class MultipleFormatsConfigurationPanel<BoundedBean extends MultipleFormatsBean> extends MultipleInputsConfigurationPanel<BoundedBean>{
 
    private List<JComboBox<TableInputFormat>> inputsFormatsSelectors;
            
    MultipleFormatsConfigurationPanel(BoundedBean inputBean, boolean adjustableNumberOfInputs){
        super(inputBean, adjustableNumberOfInputs);
    }
    
    void resetSelectors() {
        super.resetSelectors();
        inputsFormatsSelectors = new ArrayList<JComboBox<TableInputFormat>>();
    }
    
    @Override
    void addAllRow() {
        //do nothing
    }

    @Override
    void addTableRow(int table) {
        super.addTableRow(table);
        List<TableInputFormat> formats = inputBean.getFormatsOfInputs();
        JComboBox<TableInputFormat> box = new JComboBox<TableInputFormat>(TableInputFormat.values());
        box.setSelectedItem(formats.get(table));
        box.setRenderer(listCellRenderer);
        addNextCol(box, 1);
        inputsFormatsSelectors.add(box);
    }

    @Override
    void addHeaderRow() {
        super.addHeaderRow();
        addNextCol(new JLabel("Format"),1);        
    }

    /**
     * Check that user values in UI are valid
     */
    @Override
    public boolean checkValues(){
        //Assume inputsTypesSelectors of correct length
        //All must have a valid value;
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged(){
        if (super.isConfigurationChanged()){
            return true;
        }
        List<TableInputFormat> formats = inputBean.getFormatsOfInputs();
        for (int i = 0; i < getNumberOfInputs(); i++){
            if (!inputsFormatsSelectors.get(i).getSelectedItem().equals(formats.get(i))){
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
        super.noteConfiguration();
        ArrayList<TableInputFormat> formats = new ArrayList<TableInputFormat>();
        for (int i = 0; i < getNumberOfInputs(); i++){
            formats.add((TableInputFormat)inputsFormatsSelectors.get(i).getSelectedItem());
        }
        inputBean.setFormatsOfInputs(formats);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(BoundedBean inputBean){
        super.refreshConfiguration(inputBean);
        List<TableInputFormat> formats = inputBean.getFormatsOfInputs();
        for (int i = 0; i < getNumberOfInputs(); i++){
            inputsFormatsSelectors.get(i).setSelectedItem(formats.get(i));
        }
    }

}
