package net.sf.taverna.t2.activities.stilts.ui.config.process;

import javax.swing.JTextArea;
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
        add(new JTextArea("Takes two tables T1 and T2 which contain similar columns, \n"
                + "produces a new table whose metadata (row headings etc) comes from T1 \n"
                + "and whose data consists of all the rows of T1 \n"
                + "followed by all the rows of T2. \n"
                + "For this concatenation to make sense, \n"
                + "each column of T1 must be compatible with the corresponding column of T2 \n"
                + "they must have compatible types and, presumably, meanings. \n"));
    }

}
