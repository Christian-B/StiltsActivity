package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.MultipleFormatsConfigurationPanel;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TCatNBean;

/**
 * TCatn Process Configuration Panels
 * <p>
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TCatNConfigurationPanel extends StiltsProcessConfigurationPanel <TCatNBean>{
     
    public TCatNConfigurationPanel(TCatNBean processBean){
        super(processBean);
        add(new JLabel(processBean.title()));
    }

}
