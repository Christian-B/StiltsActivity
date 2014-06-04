package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 *
 * @author christian
 */
public class BasedWizard extends JDialog{
    
    private int row = -1;  //So first row becomes 0
    private int col = 0;
    private String command = null;
    private final JLabel commandLabel;
    private final JButton done;
    
    static final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    private static final String TODO = "Select one of the options above";
    
    BasedWizard(String title){
        setTitle(title);
        setLayout(new GridBagLayout());
        this.setModal(true);
        commandLabel = new JLabel(TODO);
        done = new JButton("Done");
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });    
        done.setEnabled(false);
    }
   
    void initGui(){
        addNextRow(commandLabel);
        addNextCol(done);
        JButton cancel = new JButton("Cancel");
        addNextCol(cancel);
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                command = null;
                setVisible(false);
            }
        });    
        addNextCol(cancel);        
    }

    final void addNextRow(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col = 0;
        c.gridx = col;
        row++;
        c.gridy = row;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    final void addTitletRow(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col = 0;
        c.gridx = col;
        row++;
        c.gridy = row;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    final void addNextCol(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col++;
        c.gridx = col;
        c.gridy = row;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    final JFormattedTextField integerField()
    {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        JFormattedTextField field = new JFormattedTextField(formatter);
        field.setColumns(10);
        return field;
    }
    
    boolean checkValid(){
        return command != null;
    }
    
    final DocumentListener updateFullCommandDocumentListener(){ 
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
                updateCommandLabel();
            }            
            public void insertUpdate(DocumentEvent e) {
                updateCommandLabel();
            }
        };
    }
    
    String fullCommand(){
        return command;
    }
    
    final String getSimpleCommand(){
        return command;
    }

    final void updateCommandLabel(){        
        commandLabel.setText(fullCommand());
        done.setEnabled(checkValid());
    }
    
    final void setCommand(String newCommand){
        command = newCommand;
        updateCommandLabel();
    }
    
    final void clearCommand(){
        command = null;
        commandLabel.setText(TODO);
        done.setEnabled(false);
    }

}
