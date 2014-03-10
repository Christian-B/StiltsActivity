package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.SingleInputConfigurationPanel;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TPipeBean;

@SuppressWarnings("serial")
public class TPipeConfigurationPanel extends StiltsProcessConfigurationPanel <TPipeBean>{
     
    public TPipeConfigurationPanel(TPipeBean processBean, SingleInputConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Simple Piping Tool"));
    }

}
