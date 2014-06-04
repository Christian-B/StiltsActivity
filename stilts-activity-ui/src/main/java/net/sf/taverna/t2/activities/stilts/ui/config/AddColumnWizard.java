package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;

/**
 *
 * @author christian
 */
public class AddColumnWizard extends FunctionWizard{
        
    //private StiltsLocationType newColumnLocation;
    private final JLabel locationLabel;
    private final JComboBox locationSelector;
    private static final String LOCATION_NAME = "How to place new column";

    private final JLabel columnRefrenceLabel;
    private final JTextField columnRefrenceField;
    
    private final JLabel newColumnLabel;
    private final JTextField newColumnField;

    private AddColumnWizard(String title){
        super(title);
        locationLabel = new JLabel(LOCATION_NAME);
        locationSelector = new JComboBox(StiltsLocationType.values());
        locationSelector.setSelectedItem(StiltsLocationType.END);
        locationSelector.setRenderer(listCellRenderer);
        locationSelector.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                checkLocation();
                updateCommandLabel();
            }
        });
        columnRefrenceLabel = new JLabel("");
        columnRefrenceField = textField();
        columnRefrenceField.getDocument().addDocumentListener(updateFullCommandDocumentListener());
        
        newColumnLabel = new JLabel("Name of new column");
        newColumnField = textField();
        newColumnField.setText("newColumn");
        newColumnField.getDocument().addDocumentListener(updateFullCommandDocumentListener());
    }

    @Override
    void initGui(){
        checkLocation();
        
        addNextRow(locationLabel);
        addNextCol(locationSelector);
        
        addNextRow(columnRefrenceLabel);
        addNextCol(columnRefrenceField);

        addNextRow(newColumnLabel);
        addNextCol(newColumnField);

        this.addTitletRow(new JLabel("Function to compute the new Values"));
        super.initGui();
    }
    
    private void checkLocation() {
        StiltsLocationType newColumnLocation = (StiltsLocationType)locationSelector.getSelectedItem();
        switch (newColumnLocation){
            case AFTER:{
                columnRefrenceLabel.setText("Column to place after");
                columnRefrenceField.setEnabled(true);
                break;
            }
            case BEFORE:{
                columnRefrenceLabel.setText("Column to place before");
                columnRefrenceField.setEnabled(true);
                break;
            }
            case END:{
                columnRefrenceLabel.setText("No reference column required");
                columnRefrenceField.setEnabled(false);
                break;
            }
            default:    
                throw new UnsupportedOperationException(newColumnLocation + " not supported");
        }
    }
    
    @Override
    boolean checkValid(){
        if (super.checkValid()){
            if (columnRefrenceField.isEnabled()){
                if (getColumn(columnRefrenceField) == null){
                    return false;
                }
            }
            if (newColumnField.getText().trim().isEmpty()){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
    
    String fullCommand(){
        StiltsLocationType newColumnLocation = (StiltsLocationType)locationSelector.getSelectedItem();
        String result = "addcol ";
        String reference = getColumn(columnRefrenceField);
        if (reference == null){
            reference = "TOSET ";
        } else {
            reference+= " ";
        }
        switch (newColumnLocation){
            case AFTER:
               result+= "-after " + reference;  
               break;
            case BEFORE:
               result+= "-before " + reference; 
               break;
            case END:
               break;
            default:    
                throw new UnsupportedOperationException(newColumnLocation + " not supported");
        }
        if (newColumnField.getText().trim().isEmpty()){
            result+= "TOSET";
        } else {
            result+= newColumnField.getText();
        }
        return result + " " + getSimpleCommand();
    }
    
    public static String getCommand(String title) {
        AddColumnWizard wizard = new AddColumnWizard(title);
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
                String newCommand = AddColumnWizard.getCommand("Test Wizard");
                System.out.println("Return " + newCommand);
                test.setText(newCommand);
            }
        });    
        
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
