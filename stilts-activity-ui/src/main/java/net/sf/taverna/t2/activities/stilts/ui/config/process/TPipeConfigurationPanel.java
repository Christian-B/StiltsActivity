package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.SingleInputConfigurationPanel;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TPipeBean;

/**
 * TPipe Process Configuration Panels
 * <p>
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TPipeConfigurationPanel extends StiltsProcessConfigurationPanel <TPipeBean>{
     
    public TPipeConfigurationPanel(TPipeBean processBean){
        super(processBean);
        processPanel.add(new JLabel("Simple Piping Tool"));
    }

}
