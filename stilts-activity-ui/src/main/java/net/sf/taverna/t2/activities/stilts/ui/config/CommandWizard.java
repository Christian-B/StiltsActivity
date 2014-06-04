package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author christian
 */
public class CommandWizard extends BasedWizard{
        
    private CommandWizard(String title){
        super(title);
        JButton addColumn = new JButton("Add column");
        addColumn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setCommand("TODO");
            }
        });    
    }

    public static String getCommand(String title) {
        CommandWizard wizard = new CommandWizard(title);
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
                String newCommand = CommandWizard.getCommand("Test Wizard");
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
