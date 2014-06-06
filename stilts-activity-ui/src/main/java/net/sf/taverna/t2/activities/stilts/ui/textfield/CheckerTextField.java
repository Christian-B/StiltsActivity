/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.ui.textfield;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author christian
 */
public abstract class CheckerTextField extends JTextField{
    
    private final List<AbstractButton> buttons; 
    
    public abstract Object getColumn();
    
    public CheckerTextField(){
        super();
        buttons = new ArrayList<AbstractButton>();
        DocumentListener checker = columnChecker();
        getDocument().addDocumentListener(checker);
    }
    
    @Override
    public void setText(String text){
        super.setText(text);
        checkColumn();
    }
    
    private DocumentListener columnChecker(){ 
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
                checkColumn();
            }
            public void insertUpdate(DocumentEvent e) {
                checkColumn();
            }
        };
    }
 
    private void checkColumn(){
        if (getColumn() == null){
            setButtonsEnabled(false);
            this.setBackground(Color.RED);
        } else {
            setButtonsEnabled(true);
            this.setBackground(Color.WHITE);
        }                        
    }

    private void setButtonsEnabled(boolean setting){
        for (AbstractButton button:buttons){
            button.setEnabled(setting);
        }
    }

    public void addButton(AbstractButton button){
        buttons.add(button);
    }

}
