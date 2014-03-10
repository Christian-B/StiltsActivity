package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.MultipleFormatsConfigurationPanel;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TCatNBean;

@SuppressWarnings("serial")
public class TCatNConfigurationPanel extends StiltsProcessConfigurationPanel <TCatNBean>{
     
    public TCatNConfigurationPanel(TCatNBean processBean, MultipleFormatsConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Multiple File Catenatiom Tool"));
    }

}