package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;

@SuppressWarnings("serial")
public class SingleInputConfigurationPanel extends StiltsInputConfigurationPanel<SingleInputBean>{
 
    private JComboBox<StiltsInputType> inputTypeSelector;
    private JComboBox<StiltsInputFormat> inputFormatSelector;
    
    SingleInputConfigurationPanel(SingleInputBean inputBean, boolean editable){
        super(inputBean, editable);
        initGui();
    }
    
    void initGui() {
        removeAll();
        this.setLayout(new GridLayout(0, 2));
        JLabel typeLabel = new JLabel(TYPE_LABEL);
        add(typeLabel);
        if (editable){
            inputTypeSelector = new JComboBox<StiltsInputType>(StiltsInputType.values());
            inputTypeSelector.setSelectedItem(inputBean.retreiveStiltsInputType());
            inputTypeSelector.setRenderer(listCellRenderer);
            add(inputTypeSelector);
        } else {
            StiltsInputType inputEnum = inputBean.retreiveStiltsInputType();
            JLabel typeInfoLabel = new JLabel(inputEnum.toString());
            typeInfoLabel.setToolTipText(inputEnum.getDescription());
            add(typeInfoLabel);
        }            
        JLabel formatLabel = new JLabel(FORMAT_LABEL);
        add(formatLabel);
        if (editable){
            inputFormatSelector = new JComboBox<StiltsInputFormat>(StiltsInputFormat.values());
            inputFormatSelector.setSelectedItem(inputBean.retreiveStiltsInputFormat());
            inputFormatSelector.setRenderer(listCellRenderer);
            add(inputFormatSelector);
        } else {
            StiltsInputFormat formatEnum = inputBean.retreiveStiltsInputFormat();
            JLabel typeInfoLabel = new JLabel(formatEnum.toString());
            typeInfoLabel.setToolTipText(formatEnum.getDescription());
            add(typeInfoLabel);
        }            
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
        Object configType = inputTypeSelector.getSelectedItem();
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
