package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;

@SuppressWarnings("serial")
public class SingleFormatMultipleInputsConfigurationPanel<BoundedBean extends SingleFormatMultipleInputsBean> 
        extends MultipleInputsConfigurationPanel<BoundedBean>{
 
    private JComboBox<StiltsInputFormat> inputsFormatSelector;
            
    SingleFormatMultipleInputsConfigurationPanel(BoundedBean inputBean, boolean editable){
        super(inputBean, ADJUSTABLE_NUMBER_OF_INPUT_TABLES, editable);
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
        StiltsInputFormat format = inputBean.getFormatOfInputs();
        c.gridx = 1;
        c.gridwidth = this.getNumberOfInputs();
        if (editable){
            inputsFormatSelector = new JComboBox<StiltsInputFormat>(StiltsInputFormat.values());
            inputsFormatSelector.setSelectedItem(format);
            inputsFormatSelector.setRenderer(listCellRenderer);
            add(inputsFormatSelector, c);
        } else {
            label = new JLabel(format.toString());
            label.setToolTipText(format.getDescription());
            add(label, c);
        }     
    }

    @Override
    int getNumberOfInputs() {
        //Must come from the bean as is called before 
        return inputBean.getNumberOfInputs();
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    public boolean isConfigurationChanged(){
        if (super.isConfigurationChanged()){
            return true;
        }
        if (!inputsFormatSelector.getSelectedItem().equals(inputBean.getFormatOfInputs())){
            return true;
        }
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    public void noteConfiguration(){
        super.noteConfiguration();
        inputBean.setNumberOfInputs(oldNumberOfInputs);
        inputBean.setFormatOfInputs((StiltsInputFormat)inputsFormatSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(){
        super.refreshConfiguration();
        inputsFormatSelector.setSelectedItem(inputBean.getFormatOfInputs());
    }

}
