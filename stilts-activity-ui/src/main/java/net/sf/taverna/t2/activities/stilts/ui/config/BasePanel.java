package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.ui.textfield.CheckerTextField;

/**
 *
 * @author christian
 */
public class BasePanel extends JPanel{
    
    private int col = 0;
    private int row = -1;// so first is 0 after ++
    private int fieldWidth = 20;
    
    protected BasePanel(){
        setLayout(new GridBagLayout());
    }
    
    protected final void addNextRow(JComponent component, int gridWidth){
        GridBagConstraints c = new GridBagConstraints();
        col = 0;
        c.gridx = col;
        row++;
        c.gridy = row;
        c.gridwidth = gridWidth;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    protected final void addNextCol(JComponent component, int gridWidth){
        System.out.println(""+ col + component);
        GridBagConstraints c = new GridBagConstraints();
        col++;
        c.gridx = col;
        c.gridy = row;
        c.gridwidth = gridWidth;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    protected final void addHelpButton(final CheckerTextField field){
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
                {
                    Frame frame = Frame.getFrames()[0]; 
                    JOptionPane.showMessageDialog(frame, field.helpText());
                }
            });    
        addNextCol(helpButton, 1);     
    }
    
}
