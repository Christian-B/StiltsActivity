package net.sf.taverna.t2.activities.stilts.ui.config.test;

import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.test.TCatBean;

@SuppressWarnings("serial")
public class TCatConfigurationPanel extends StiltsProcessConfigurationPanel <TCatBean>{
     
    TCatConfigurationPanel(TCatBean processBean, SingleFormatMultipleInputsConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Multiple File Catenatiom Tool"));
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();    
        processBean = new TCatBean((SingleFormatMultipleInputsBean)inputPanel.getConfiguration());
    }
}
