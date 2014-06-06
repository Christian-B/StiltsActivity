package net.sf.taverna.t2.activities.stilts.ui.config;

import net.sf.taverna.t2.activities.stilts.ui.textfield.DoubleTextField;
import net.sf.taverna.t2.activities.stilts.ui.textfield.ColumnIdTextField;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;

/**
 *
 * @author christian
 */
public class FunctionWizard extends BasedWizard{
        
    private final DoubleTextField numberField;
    private final JButton numberButton;
    private final ColumnIdTextField inputField;
    private final JButton inputButton;
    
    private static final int NOT_FOUND = -1;
    private static final boolean ANY_VALUE = false;
    private static final boolean BOOLEAN_ONLY = true;
    
    private FunctionWizard(String title, boolean forceBoolean){
        super(title);
        numberButton = new JButton("Use a number");
        numberButton.setEnabled(false);
        numberField = new DoubleTextField();
        numberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand(numberField.getColumn().toString());
            }
        });    
        numberField.addButton(numberButton);
        inputButton = new JButton("Use column name/number");
        inputButton.setEnabled(false);
        inputField = new ColumnIdTextField("");
        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand(inputField.getColumn());
            }
        });    
        inputField.addButton(inputButton);
    }
    
    void initGui(){
        addNextRow(numberButton);
        addNextCol(numberField);
        addNextRow(inputButton);
        addNextCol(inputField);
        addNextCol(inputHelpButton());
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
        final JComboBox twoSelector = new JComboBox(StiltsTwoVariableOperator.values());
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

        super.initGui();
    }

/*  final JTextField textField(){
        JTextField field = new JTextField();
        field.setColumns(10);
        return field;
    }
*/
    private JComponent inputHelpButton() {
        final JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                Frame frame = Frame.getFrames()[0]; 
                JOptionPane.showMessageDialog(frame,
                        "This can be the Column Name or Index\n" 
                        + "The name of the column may be used if it contains no spaces \n"
                        + "and doesn't start with a minus character ('-'). \n"
                        + "It is usually matched case insensitively. \n"
                        + "If multiple columns have the same name, the first one that matches is selected. \n"
                        + "Column Index is a useful fallback if the column name isn't suitable for some reason.\n"
                        + "The first column is '$1', the second is '$2' and so on.");
             }
        });    
        return help;
    }

    private String obtainCommand(){
        initGui();
        pack();
        setVisible(true);
        return command;
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
