package net.sf.taverna.t2.activities.table.ui.config.process;

import net.sf.taverna.t2.activities.table.process.TPipeBean;

/**
 * TPipe Process Configuration Panels
 * <p>
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TPipeConfigurationPanel extends TableProcessConfigurationPanel <TPipeBean>{
     
    public TPipeConfigurationPanel(TPipeBean processBean){
        super(processBean);
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

}
