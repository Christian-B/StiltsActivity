package net.sf.taverna.t2.activities.stilts.ui.config.input;

import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;

/**
 * Configuration panel for exactly one input table
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> Any type of StitlsInputsBean
 */
@SuppressWarnings("serial")
public class SingleInputConfigurationPanel extends StiltsInputConfigurationPanel<SingleInputBean>{
 
    private JComboBox<StiltsInputType> inputTypeSelector;
    private JComboBox<StiltsInputFormat> inputFormatSelector;
    
    public SingleInputConfigurationPanel(SingleInputBean inputBean){
        super(inputBean);
        initGui();
    }
    
    void initGui() {
        removeAll();
        this.setLayout(new GridLayout(0, 2));
        JLabel typeLabel = new JLabel(TYPE_LABEL);
        add(typeLabel);
        inputTypeSelector = new JComboBox<StiltsInputType>(StiltsInputType.values());
        inputTypeSelector.setSelectedItem(inputBean.retreiveStiltsInputType());
        inputTypeSelector.setRenderer(listCellRenderer);
        add(inputTypeSelector);
        JLabel formatLabel = new JLabel(FORMAT_LABEL);
        add(formatLabel);
        inputFormatSelector = new JComboBox<StiltsInputFormat>(StiltsInputFormat.values());
        inputFormatSelector.setSelectedItem(inputBean.retreiveStiltsInputFormat());
        inputFormatSelector.setRenderer(listCellRenderer);
        add(inputFormatSelector);
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
        StiltsInputFormat beanType = inputBean.retreiveStiltsInputFormat();
        Object configType = inputFormatSelector.getSelectedItem();
        if (!beanType.equals(configType)){
            return true;
        }
        StiltsInputType beanFormat = inputBean.retreiveStiltsInputType();
        Object configFormat = inputTypeSelector.getSelectedItem();
        if (!beanFormat.equals(configFormat)){
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
        inputBean = new SingleInputBean();
        inputBean.resetFormatOfInput((StiltsInputFormat)inputFormatSelector.getSelectedItem());
        inputBean.resetTypeOfInput((StiltsInputType)inputTypeSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(SingleInputBean inputBean){
        super.refreshConfiguration(inputBean);
        inputTypeSelector.setSelectedItem(inputBean.retreiveStiltsInputType());
        inputFormatSelector.setSelectedItem(inputBean.retreiveStiltsInputFormat());
    }

}
