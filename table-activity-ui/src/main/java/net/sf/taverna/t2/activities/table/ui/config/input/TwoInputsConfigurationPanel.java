package net.sf.taverna.t2.activities.table.ui.config.input;

import net.sf.taverna.t2.activities.table.input.TwoInputsBean;

/**
 * Configuration panel for exactly two input tables which that can have different formats and types 
 * Allows various processes to share the same configuration code if they share the same type of input.
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> Any type of AbstractInputsBean
 */
@SuppressWarnings("serial")
public class TwoInputsConfigurationPanel extends MultipleFormatsConfigurationPanel<TwoInputsBean> {
 
    public TwoInputsConfigurationPanel(TwoInputsBean inputBean){
        super(inputBean, FIXED_NUMBER_OF_INPUT_TABLES);
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
