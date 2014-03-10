package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.GridBagConstraints;
import net.sf.taverna.t2.activities.stilts.input.FlexibleInputsBean;
import net.sf.taverna.t2.activities.stilts.input.TwoInputsBean;

@SuppressWarnings("serial")
public class TwoInputsConfigurationPanel extends MultipleFormatsConfigurationPanel<TwoInputsBean> {
 
    TwoInputsConfigurationPanel(TwoInputsBean inputBean, boolean editable){
        super(inputBean, FIXED_NUMBER_OF_INPUT_TABLES, editable);
    }
    
    @Override
    void initGui() {
        super.initGui();        
    }

    @Override
    int getNumberOfInputs() {
        return 2;
    }

    @Override
    public void noteConfiguration() {
        inputBean = new TwoInputsBean();
        super.noteConfiguration();
    }

}
