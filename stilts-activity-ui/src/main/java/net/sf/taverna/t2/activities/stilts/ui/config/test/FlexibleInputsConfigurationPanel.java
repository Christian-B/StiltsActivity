package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.FlexibleInputsBean;
import net.sf.taverna.t2.activities.stilts.test.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.test.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;

@SuppressWarnings("serial")
public class FlexibleInputsConfigurationPanel extends MultipleFormatsConfigurationPanel<FlexibleInputsBean>{
 
    private List<JComboBox> inputsFormatsSelectors;
    private int oldNumberOfInputs;
    
    FlexibleInputsConfigurationPanel(FlexibleInputsBean inputBean, boolean editable){
        super(inputBean, editable);
        oldNumberOfInputs = inputBean.getNumberOfInputs();
    }
    
    @Override
    void initGui() {
        System.out.println("initGui");
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
