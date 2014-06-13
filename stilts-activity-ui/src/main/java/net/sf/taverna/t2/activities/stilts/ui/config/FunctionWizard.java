package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;
import net.sf.taverna.t2.activities.stilts.ui.serviceprovider.StiltsServiceDesc;
import net.sf.taverna.t2.activities.stilts.ui.textfield.ColumnIdTextField;
import net.sf.taverna.t2.activities.stilts.ui.textfield.DoubleTextField;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 *
 * @author christian
 */
public class FunctionWizard extends JDialog{
    
    private int row = -1;  //So first row becomes 0
    private int col = 0;
    String command = null;
    private final JLabel commandLabel = new JLabel(TODO);
    private final JButton done = new JButton("Done");
    
    static final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    private static final String TODO = "Select one of the options above";
    
    private final boolean anyValue;
    
    private static final int NOT_FOUND = -1;
    private static final boolean ANY_VALUE = true;
    private static final boolean BOOLEAN_ONLY = false;
    
    private FunctionWizard(String title, boolean anyValue){
        setTitle(title);
        this.setIconImage(StiltsServiceDesc.stiltsIcon().getImage());
        setLayout(new GridBagLayout());
        this.setModal(true);
        this.anyValue = anyValue;
        addNumberRow();
        addColumnRow();
        addOneVariableOperatorRow();
        addTwoVariableOperatorRow();
        addCommandDoneRow();
        pack();
        setVisible(true);
    }
    
    private void addNumberRow(){
        if (anyValue){
            JButton numberButton = new JButton("Use a number");
            final DoubleTextField numberField = new DoubleTextField();
            numberButton.setEnabled(false);
            numberButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    setCommand(numberField.getValue().toString());
                }
            });    
            numberField.addButton(numberButton);
            addNextRow(numberButton);
            addNextCol(numberField);
            JButton helpButton = new JButton("Help");
            helpButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    Frame frame = Frame.getFrames()[0]; 
                    JOptionPane.showMessageDialog(frame,numberField.helpText());
                }
            });    
        addNextCol(helpButton);
        }
    }
    
    private void addColumnRow(){
        JButton columnButton = new JButton("Use column name/number");
        final ColumnIdTextField columnField = new ColumnIdTextField("",ColumnIdTextField.ALLOW_ID);
        columnButton.setEnabled(false);
        columnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand(columnField.getValue());
            }
        });    
        columnField.addButton(columnButton);
        addNextRow(columnButton);
        addNextCol(columnField);
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                Frame frame = Frame.getFrames()[0]; 
                JOptionPane.showMessageDialog(frame,columnField.helpText());
             }
        });    
        addNextCol(helpButton);
    }    
    
    private void addOneVariableOperatorRow(){
        if (anyValue){
            JButton byOne = new JButton("Using one value function");
            final JComboBox oneSelector = new JComboBox(StiltsOneVariableOperator.values());
            oneSelector.setRenderer(listCellRenderer);
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
            addNextRow(byOne);
            addNextCol(oneSelector);
        }
    }
    
    private void addTwoVariableOperatorRow(){
        JButton byTwo = new JButton("Using two value function");
        addNextRow(byTwo);
        StiltsTwoVariableOperator[] values;
        if (anyValue){
            values = StiltsTwoVariableOperator.values();
        } else {
            values = StiltsTwoVariableOperator.booleanValues();
        }
        final JComboBox twoSelector = new JComboBox(values);
        twoSelector.setRenderer(listCellRenderer);
        addNextCol(twoSelector);
        byTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                StiltsTwoVariableOperator operator = (StiltsTwoVariableOperator)twoSelector.getSelectedItem();
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
    }
    
    private void addCommandDoneRow(){
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });    
        done.setEnabled(false);
        addNextRow(commandLabel);
        addNextCol(done);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                command = null;
                setVisible(false);
            }
        });    
        addNextCol(cancel);        
    }

/*  final JTextField textField(){
        JTextField field = new JTextField();
        field.setColumns(10);
        return field;
    }
*/
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

    final void addNextCol(JComponent component){
        GridBagConstraints c = new GridBagConstraints();
        col++;
        c.gridx = col;
        c.gridy = row;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(component,c);
    }

    private String obtainCommand(){
        return command;
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
        FunctionWizard wizard = new FunctionWizard(title, ANY_VALUE);
        return wizard.obtainCommand();
    }

    public static String getBoolean(String title) {
        FunctionWizard wizard = new FunctionWizard(title, BOOLEAN_ONLY);
        return wizard.obtainCommand();
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
                String newCommand = FunctionWizard.getBoolean("Test Wizard");
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
