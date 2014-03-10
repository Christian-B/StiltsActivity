/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 *
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
