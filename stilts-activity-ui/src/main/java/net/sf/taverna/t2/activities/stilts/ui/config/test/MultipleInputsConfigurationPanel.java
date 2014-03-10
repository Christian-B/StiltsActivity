package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;

@SuppressWarnings("serial")
public abstract class MultipleInputsConfigurationPanel<BoundedBean extends MultipleInputsBean> 
        extends StiltsInputConfigurationPanel<BoundedBean>{
 
    private List<JComboBox<StiltsInputType>> inputsTypesSelectors;
    final int headerRows; 

    MultipleInputsConfigurationPanel(BoundedBean inputBean, boolean editable, int headerRows){
        super(inputBean, editable);
        this.headerRows = headerRows;
        initGui();
    }
    
    void initGui() {
        removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

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
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        for (int i = 0; i < getNumberOfInputs(); i++){
            inputsTypesSelectors.get(i).setSelectedItem(types.get(i));
        }
    }

    abstract int getNumberOfInputs();
}
