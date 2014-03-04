package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author christian
 */
public class StiltsBeanPanel extends JPanel{

    private final JPanel inputPanel;
    private final JPanel outputPanel;
    private final JPanel miscellaneousPanel;

    protected StiltsBeanPanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        inputPanel = new JPanel(new GridLayout(0, 1));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Inputs"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        add(inputPanel, c);
        outputPanel = new JPanel(new GridLayout(0, 1));
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Outputs"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        c.gridy = 1;
        add(outputPanel, c);
        miscellaneousPanel = new JPanel(new GridLayout(0, 1));
        c.gridy = 2;
        add(miscellaneousPanel, c);
    }
    
    void clear() {
        inputPanel.removeAll();
        outputPanel.removeAll();
        miscellaneousPanel.removeAll();
    }

    void addOutput(JComponent component){
        outputPanel.add(component);
    }

    void addInput(JComponent component){
        inputPanel.add(component);
    }

    void addMiscellaneous(JComponent component) {
        miscellaneousPanel.add(component);
    }
}
