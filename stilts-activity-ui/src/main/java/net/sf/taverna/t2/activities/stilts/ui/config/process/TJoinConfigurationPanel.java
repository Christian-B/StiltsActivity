package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.MultipleFormatsConfigurationPanel;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TJoinBean;

@SuppressWarnings("serial")
public class TJoinConfigurationPanel extends StiltsProcessConfigurationPanel <TJoinBean>{
     
    public TJoinConfigurationPanel(TJoinBean processBean, MultipleFormatsConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Multiple File Join Tool"));
    }

}
