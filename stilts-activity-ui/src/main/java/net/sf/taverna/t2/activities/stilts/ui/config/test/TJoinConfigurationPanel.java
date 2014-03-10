package net.sf.taverna.t2.activities.stilts.ui.config.test;

import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.test.TJoinBean;

@SuppressWarnings("serial")
public class TJoinConfigurationPanel extends StiltsProcessConfigurationPanel <TJoinBean>{
     
    TJoinConfigurationPanel(TJoinBean processBean, MultipleFormatsConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Multiple File Join Tool"));
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();    
        processBean = new TJoinBean((MultipleFormatsBean)inputPanel.getConfiguration());
    }
}
