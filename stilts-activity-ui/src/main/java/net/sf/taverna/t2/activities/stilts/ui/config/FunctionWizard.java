package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.text.NumberFormatter;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 *
 * @author christian
 */
public class FunctionWizard extends JDialog{
    
    private int row = -1;  //So first row becomes 0
    private int col = 0;
    private String command = null;
    private final JLabel commandLabel;
    private final JButton done;
    
    private static final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    private static final String TODO = "Select one of the options above";
    
    private FunctionWizard(String title){
        setTitle(title);
        setLayout(new GridBagLayout());
        this.setModal(true);
        commandLabel = new JLabel(TODO);
        
        JButton byNumber = new JButton("By column number");
        addNextRow(byNumber);
        final JFormattedTextField numberField = integerField();
        addNextCol(numberField);
        byNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    numberField.commitEdit();
                    Integer number = (Integer)numberField.getValue();
                    setCommand("$" + number);
                } catch (ParseException ex) {
                    Logger.getLogger(FunctionWizard.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });    

        JButton byName = new JButton("By column name");
        addNextRow(byName);
        final JTextField name = new JTextField();
        name.setColumns(10);
        addNextCol(name);
        byName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand(name.getText());
            }
        });    

        JButton byOne = new JButton("Using one value function");
        addNextRow(byOne);
        final JComboBox oneSelector = new JComboBox(StiltsOneVariableOperator.values());
        oneSelector.setRenderer(listCellRenderer);
        addNextCol(oneSelector);
        byOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                StiltsOneVariableOperator operator = (StiltsOneVariableOperator)oneSelector.getSelectedItem();
                String inside = getCommand("Input for " + operator);
                if (inside == null){
                   clearCommand();
                } else {
                    setCommand(operator.retrieveStilsCommand(inside));
                }
            }
        });    
        
        JButton byTwo = new JButton("Using two value function");
        addNextRow(byTwo);
        JComboBox twoSelector = new JComboBox(StiltsTwoVariableOperator.values());
        twoSelector.setRenderer(listCellRenderer);
        addNextCol(twoSelector);
        byTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                StiltsTwoVariableOperator operator = (StiltsTwoVariableOperator)oneSelector.getSelectedItem();
                String inside1 = getCommand("First input for " + operator);
                if (inside1 == null){
                   clearCommand();
                } else {
                    String inside2 = getCommand("First input for " + operator);
                    if (inside2 == null){
                        clearCommand();
                    }
                    setCommand(operator.retrieveStilsCommand(inside1, inside2));
                }
            }
        });    

        addNextRow(commandLabel);
        done = new JButton("Done");
        addNextCol(done);
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });    
        done.setEnabled(false);
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
   
    private void addNextRow(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col = 0;
        c.gridx = col;
        row++;
        c.gridy = row;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    private void addNextCol(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col++;
        c.gridx = col;
        c.gridy = row;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    private JFormattedTextField integerField()
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
    
    private void setCommand(String newCommand){
        command = newCommand;
        commandLabel.setText(command);
        done.setEnabled(true);
    }
    
    private void clearCommand(){
        command = null;
        commandLabel.setText(TODO);
        done.setEnabled(false);
    }

    public static String getCommand(String title) {
        FunctionWizard wizard = new FunctionWizard(title);
        wizard.pack();
        wizard.setVisible(true);
        return wizard.command;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        final JFrame frame = new JFrame("Function Wizard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        final JButton test = new JButton("test");
        frame.add(test);
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                String newCommand = FunctionWizard.getCommand("Test Wizard");
                System.out.println("Return " + newCommand);
                test.setText(newCommand);
            }
        });    
        
        
        //Create and set up the content pane.
        //JComponent newContentPane = new FunctionWizard();
       // newContentPane.setOpaque(true); //content panes must be opaque
        //frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
