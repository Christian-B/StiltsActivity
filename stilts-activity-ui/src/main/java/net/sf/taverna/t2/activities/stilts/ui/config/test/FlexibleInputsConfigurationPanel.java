package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import net.sf.taverna.t2.activities.stilts.test.FlexibleInputsBean;

@SuppressWarnings("serial")
public class FlexibleInputsConfigurationPanel extends MultipleFormatsConfigurationPanel<FlexibleInputsBean> {
 
    FlexibleInputsConfigurationPanel(FlexibleInputsBean inputBean, boolean editable){
        super(inputBean, ADJUSTABLE_NUMBER_OF_INPUT_TABLES, editable);
    }
    
    @Override
    void initGui() {
        super.initGui();        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
    }

    @Override
    int getNumberOfInputs() {
        //Must come from the bean as is called before 
        return inputBean.getNumberOfInputs();
    }

    @Override
    public void noteConfiguration() {
        inputBean = new FlexibleInputsBean();
        inputBean.setNumberOfInputs(oldNumberOfInputs);
        super.noteConfiguration();
    }

}
