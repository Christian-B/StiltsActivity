package net.sf.taverna.t2.activities.stilts.ui.config.test;

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
    
    MultipleInputsConfigurationPanel(BoundedBean inputBean, boolean editable){
        super(inputBean, editable);
        initGui();
    }
    
    void initGui() {
        removeAll();
        this.setLayout(new GridLayout(0, getNumberOfInputs() + 1));
        JLabel label = new JLabel("");
        add(label);
        for (int i = 0; i < getNumberOfInputs(); i++){
            label = new JLabel("Table " + (i+1));
            add(label);
        }
        label = new JLabel(TYPE_LABEL);
        add(label);
        List<StiltsInputType> types = inputBean.getTypesOfInputs();
        if (editable){
            inputsTypesSelectors = new ArrayList<JComboBox<StiltsInputType>>();
            for (int i = 0; i < getNumberOfInputs(); i++){
                JComboBox<StiltsInputType> box = new JComboBox<StiltsInputType>(StiltsInputType.values());
                box.setSelectedItem(types.get(i));
                box.setRenderer(listCellRenderer);
                add(box);
                inputsTypesSelectors.add(box);
            }
        } else {
            for (int i = 0; i < getNumberOfInputs(); i++){
                label = new JLabel(types.get(i).toString());
                label.setToolTipText(types.get(i).getDescription());
                add(label);
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
