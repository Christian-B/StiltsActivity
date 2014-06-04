package net.sf.taverna.t2.activities.stilts.ui.config.input;

import java.awt.GridBagConstraints;
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
        inputsFormatSelector = new JComboBox<StiltsInputFormat>(StiltsInputFormat.values());
        inputsFormatSelector.setSelectedItem(format);
        inputsFormatSelector.setRenderer(listCellRenderer);
        add(inputsFormatSelector, c);
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
        inputsFormatSelector.setSelectedItem(inputBean.getFormatOfInputs());
    }

}
