package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.test.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;

@SuppressWarnings("serial")
public abstract class MultipleFormatsConfigurationPanel<BoundedBean extends MultipleFormatsBean> extends MultipleInputsConfigurationPanel<BoundedBean>{
 
    private List<JComboBox<StiltsInputFormat>> inputsFormatsSelectors;
            
    MultipleFormatsConfigurationPanel(BoundedBean inputBean, boolean adjustableNumberOfInputs, boolean editable){
        super(inputBean, adjustableNumberOfInputs, editable);
    }
    
    @Override
    void initGui() {
        super.initGui();
        GridBagConstraints c = new GridBagConstraints();

        //Format Type Table Header
        c.gridx = 0;
        c.gridy = headerRows + 2;
        JLabel label = new JLabel(TYPE_LABEL);
        add(label, c);
        List<StiltsInputFormat> formats = inputBean.getFormatsOfInputs();
        if (editable){
            inputsFormatsSelectors = new ArrayList<JComboBox<StiltsInputFormat>>();
            for (int i = 0; i < getNumberOfInputs(); i++){
                JComboBox<StiltsInputFormat> box = new JComboBox<StiltsInputFormat>(StiltsInputFormat.values());
                box.setSelectedItem(formats.get(i));
                box.setRenderer(listCellRenderer);
                c.gridx = i + 1;
                add(box, c);
                inputsFormatsSelectors.add(box);
            }
        } else {
            for (int i = 0; i < getNumberOfInputs(); i++){
                label = new JLabel(formats.get(i).toString());
                label.setToolTipText(formats.get(i).getDescription());
                c.gridx = i + 1;
                add(label, c);
            }            
        }
        
    }
    
    /**
     * Check that user values in UI are valid
     */
    public boolean checkValues(){
        //Assume inputsTypesSelectors of correct length
        //All must have a valid value;
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    public boolean isConfigurationChanged(){
        List<StiltsInputFormat> formats = inputBean.getFormatsOfInputs();
        for (int i = 0; i < getNumberOfInputs(); i++){
            if (inputsFormatsSelectors.get(i).getSelectedItem().equals(formats.get(i))){
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
        super.noteConfiguration();
        ArrayList<StiltsInputFormat> formats = new ArrayList<StiltsInputFormat>();
        for (int i = 0; i < getNumberOfInputs(); i++){
            formats.add((StiltsInputFormat)inputsFormatsSelectors.get(i).getSelectedItem());
        }
        System.out.println(formats);
        inputBean.setFormatsOfInputs(formats);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(){
        List<StiltsInputFormat> formats = inputBean.getFormatsOfInputs();
        for (int i = 0; i < getNumberOfInputs(); i++){
            inputsFormatsSelectors.get(i).setSelectedItem(formats.get(i));
        }
    }

}
