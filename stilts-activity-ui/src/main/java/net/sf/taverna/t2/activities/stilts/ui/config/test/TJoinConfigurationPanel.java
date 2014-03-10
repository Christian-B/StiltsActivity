package net.sf.taverna.t2.activities.stilts.ui.config.test;

import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TJoinBean;

@SuppressWarnings("serial")
public class TJoinConfigurationPanel extends StiltsProcessConfigurationPanel <TJoinBean>{
     
    TJoinConfigurationPanel(TJoinBean processBean, MultipleFormatsConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Multiple File Join Tool"));
    }

}
