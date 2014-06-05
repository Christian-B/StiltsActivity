package net.sf.taverna.t2.activities.stilts.ui.config.process;

import javax.swing.JTextArea;
import net.sf.taverna.t2.activities.stilts.process.TJoinBean;

/**
 * Join Process Configuration Panels
 * <p>
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TJoinConfigurationPanel extends StiltsProcessConfigurationPanel <TJoinBean>{
     
    public TJoinConfigurationPanel(TJoinBean processBean){
        super(processBean);
        add(new JTextArea("Performs a trivial side-by-side join of multiple tables. \n"
                + "The N'th row of the output table consists of the N'th row of the first input table, \n"
                + "followed by the N'th row of the second input table, ... and so on. \n"
                + "It is suitable if you want to amalgamate two or more tables whose row orderings correspond exactly to each other."));
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

}
