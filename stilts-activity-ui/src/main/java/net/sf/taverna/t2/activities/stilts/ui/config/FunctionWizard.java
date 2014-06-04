package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;

/**
 *
 * @author christian
 */
public class FunctionWizard extends BasedWizard{
        
    private final JTextField inputField;
    private final JButton inputButton;
    
    private static final int NOT_FOUND = -1;
    
    FunctionWizard(String title){
        super(title);
        inputButton = new JButton("Use column name/number");
        inputButton.setEnabled(false);
        inputField = textField();
        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand(getColumn(inputField));
            }
        });    
        DocumentListener checker = columnChecker(inputButton, inputField);
        inputField.getDocument().addDocumentListener(checker);
    }
    
    void initGui(){
        addNextRow(inputButton);
        addNextCol(inputField);
        /*byNumberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand("$" + getNumber());
            }
        });    

        addNextRow(byNameButton);
        byNameButton.setEnabled(false);
        addNextCol(nameField);
        byNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand(nameField.getText());
            }
        });    
*/
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

    final JTextField textField(){
        JTextField field = new JTextField();
        field.setColumns(10);
        return field;
    }
    
    String getColumn(JTextField field){
        String asString = field.getText();
        System.out.println("get column; " + asString);
        asString = asString.trim();
        if (asString.isEmpty()){
            return null;
        }        
        if (asString.startsWith("$")){
            if (asString.matches("\\$\\d+")){
                return asString;            
            } else {
                return null;
            }
        }
        if (asString.matches("\\d+")){
            return "$" + asString;    
        }
        if (asString.contains(" ")){
            return null;
        }        
        if (asString.contains("-")){
            return null;
        }        
        return asString;
    }
    
    private void checkColumn(JButton button, JTextField field){
        if (getColumn(field) == null){
            button.setEnabled(false);                    
        } else {
            button.setEnabled(true);
        }                        
    }
    
    private DocumentListener columnChecker(final JButton button, final JTextField field){ 
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
                checkColumn(button, field);
            }
            public void insertUpdate(DocumentEvent e) {
                checkColumn(button, field);
            }
        };
    }
    
    public static String getCommand(String title) {
        FunctionWizard wizard = new FunctionWizard(title);
        wizard.initGui();
        wizard.pack();
        wizard.setVisible(true);
        return wizard.fullCommand();
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