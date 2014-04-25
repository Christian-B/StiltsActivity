package net.sf.taverna.t2.activities.stilts.ui.config.input;

import net.sf.taverna.t2.activities.stilts.input.FlexibleInputsBean;

/**
 * Configuration panel for an adjustable number of input tables which that can have different formats and types 
 * Allows various processes to share the same configuration code if they share the same type of input.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FlexibleInputsConfigurationPanel extends MultipleFormatsConfigurationPanel<FlexibleInputsBean> {
 
    public FlexibleInputsConfigurationPanel(FlexibleInputsBean inputBean, boolean editable){
        super(inputBean, ADJUSTABLE_NUMBER_OF_INPUT_TABLES, editable);
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

    @Override
    public void noteConfiguration() {
        inputBean = new FlexibleInputsBean();
        inputBean.setNumberOfInputs(oldNumberOfInputs);
        super.noteConfiguration();
    }

}
