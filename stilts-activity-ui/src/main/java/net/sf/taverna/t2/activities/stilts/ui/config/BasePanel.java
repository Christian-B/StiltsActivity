package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    
    protected final void addNextRow(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col = 0;
        c.gridx = col;
        row++;
        c.gridy = row;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    protected final void addTitleRow(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col = 0;
        c.gridx = col;
        row++;
        c.gridy = row;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    protected final void addNextCol(JComponent component){
        System.out.println(""+ col + component);
        GridBagConstraints c = new GridBagConstraints();
        col++;
        c.gridx = col;
        c.gridy = row;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    protected final JTextField  newTextField(String text){
        JTextField textField = newTextField();
        textField.setText(text);
        return textField;
    }

    protected final JTextField newTextField(){
        JTextField textField = new JTextField(fieldWidth);
        return textField;
    }
}
