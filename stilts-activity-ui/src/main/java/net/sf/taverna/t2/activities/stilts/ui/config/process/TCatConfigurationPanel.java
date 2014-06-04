package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.SingleFormatMultipleInputsConfigurationPanel;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TCatBean;

/**
 * TCat Process Configuration Panels
 * <p>
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TCatConfigurationPanel extends StiltsProcessConfigurationPanel <TCatBean>{
     
    public TCatConfigurationPanel(TCatBean processBean){
        super(processBean);
        add(new JLabel(processBean.title()));
    }

}
