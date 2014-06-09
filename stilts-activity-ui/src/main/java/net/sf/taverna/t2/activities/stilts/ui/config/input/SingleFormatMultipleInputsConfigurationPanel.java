package net.sf.taverna.t2.activities.stilts.ui.config.input;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.input.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;

/**
 * Configuration panel for multiple inputs which that can have the same formats but possibly different types 
 * Allows various processes to share the same configuration code if they share the same type of input.
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> Any type of StitlsInputsBean
 */@SuppressWarnings("serial")
public class SingleFormatMultipleInputsConfigurationPanel<BoundedBean extends SingleFormatMultipleInputsBean> 
        extends MultipleInputsConfigurationPanel<BoundedBean>{
 
    private JComboBox<StiltsInputFormat> inputsFormatSelector;
            
    public SingleFormatMultipleInputsConfigurationPanel(BoundedBean inputBean){
        super(inputBean, ADJUSTABLE_NUMBER_OF_INPUT_TABLES);
    }
    
   @Override
    void addAllRow() {
        addNextRow(new JLabel(TYPE_LABEL),1);
        inputsFormatSelector = new JComboBox<StiltsInputFormat>(StiltsInputFormat.values());
        StiltsInputFormat format = inputBean.getFormatOfInputs();
        inputsFormatSelector.setSelectedItem(format);
        inputsFormatSelector.setRenderer(listCellRenderer);
        addNextCol(inputsFormatSelector, 1);
    }

    @Override
    void initGui() {
        super.initGui();
    }

    @Override
    int getNumberOfInputs() {
        //Must come from the bean as is called before 
        return inputBean.getNumberOfInputs();
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
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
    @Override
    public void noteConfiguration(){
        super.noteConfiguration();
        inputBean.setNumberOfInputs(oldNumberOfInputs);
        inputBean.setFormatOfInputs((StiltsInputFormat)inputsFormatSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(BoundedBean inputBean){
        super.refreshConfiguration(inputBean);
    }

 
}
