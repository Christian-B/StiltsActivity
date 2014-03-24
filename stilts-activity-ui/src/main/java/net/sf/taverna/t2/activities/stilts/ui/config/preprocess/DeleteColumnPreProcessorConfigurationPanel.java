package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.DeleteColumnPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.preprocess.UserSpecifiedPreProcessorBean;

@SuppressWarnings("serial")
public class DeleteColumnPreProcessorConfigurationPanel extends StiltsPreProcessConfigurationPanel<DeleteColumnPreProcessorBean>{
 
    private JTextField columnsCommandField;
    
    private static final String COLUMN_LABEL = "Column(s) to delete: ";
    
    public DeleteColumnPreProcessorConfigurationPanel(DeleteColumnPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
    }
    
    @Override
    void addEditable(DeleteColumnPreProcessorBean preprocessBean){ 
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        JLabel label1 = new JLabel ("Please specify the columns to delete.");
        add(label1, c);
            
        c.gridy = 1;
        JLabel label2 = new JLabel ("Can be the column name as specified in the file.");
        add(label2, c);

        c.gridy = 2;
        JLabel label3 = new JLabel ("Can be the column number (starting at 1).");
        add(label3, c);

        c.gridy = 3;
        JLabel label4 = new JLabel ("Multiple columns can be specified. Seperaated by a space.");
        add(label4, c);

        c.gridy = 4;            
        c.gridwidth = 1;
        JLabel commandLabel = new JLabel (COLUMN_LABEL);
        add(commandLabel, c);
        c.gridx = 1;
        columnsCommandField = new JTextField(preprocessBean.getColumn(), 20);
        add(columnsCommandField, c);
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
    @Override
    public boolean checkValues() {
         // All valid, return true
        if (columnsCommandField.getText().trim().isEmpty()){
            String message = COLUMN_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + COLUMN_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
     * @return 
      */
    @Override
    public boolean isConfigurationChanged() {
        return (!columnsCommandField.getText().equals(preprocessBean.getColumn()));
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean = new DeleteColumnPreProcessorBean();
        preprocessBean.setColumn(columnsCommandField.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(DeleteColumnPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        columnsCommandField = new JTextField(preprocessBean.getColumn());
    }
}
