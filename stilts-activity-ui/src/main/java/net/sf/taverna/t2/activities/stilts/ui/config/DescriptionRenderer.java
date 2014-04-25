package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 * Simple render that adds a tool tip using the DescribableInterface interface
 * @author christian
 */
public class DescriptionRenderer extends JLabel implements ListCellRenderer<DescribableInterface>{

    public Component getListCellRendererComponent(JList<? extends DescribableInterface> jlist, 
            DescribableInterface element, int i, boolean bln, boolean bln1) {
        JLabel label = new JLabel(element.toString());
        label.setToolTipText(element.getDescription());
        return label;
    }
   
}
