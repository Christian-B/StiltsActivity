package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.preprocess.ColumnListPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.ui.textfield.ColumnListTextField;

/**
 * Base class of all the All PreProcess using a column list Configuration Panels
 * <p>
 * Handles the details of the column list shared by these PreProcessors.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> a specific ColumnListPreProcessorBean
 */
@SuppressWarnings("serial")
public abstract class ColumnListPreProcessorConfigurationPanel<BoundedBean extends ColumnListPreProcessorBean>  extends StiltsPreProcessConfigurationPanel<BoundedBean>{
 
    private ColumnListTextField columnsListField;
        
    public ColumnListPreProcessorConfigurationPanel(BoundedBean preprocessBean){
        super(preprocessBean);
        //JLabel label1 = new JLabel ("Please specify the Column(s) to " + getAction() + ".");
        //addNextRow(label1, 2);
            
        //JLabel label2 = new JLabel ("Can be the column name as specified in the file.");
        //addNextRow(label2, 2);

        //JLabel label3 = new JLabel ("Can be the column number (starting at 1)(No $).");
        //addNextRow(label3, 2);

        //JLabel label4 = new JLabel ("Multiple columns can be specified. Seperaated by a space.");
        //addNextRow(label4, 2);

        JLabel commandLabel = new JLabel ("Column(s) to " + getAction() + ": ");
        addNextRow(commandLabel, 1);
        columnsListField = new ColumnListTextField(preprocessBean.getColumnList());
        columnsListField.setText(preprocessBean.getColumnList());
                
        addNextCol(columnsListField, 1);
        addHelpButton(columnsListField);
    }
   
    final int nextY(){
        return 5;
    }
    
    /**
      * Check that user values in UI are valid
     * @return 
      */
    @Override
    public boolean checkValues() {
         // All valid, return true
        if (columnsListField.getText().trim().isEmpty()){
            String message = "Column(s) to " + getAction() + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + "Column(s) to " + getAction(), JOptionPane.ERROR_MESSAGE);
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
        return (!columnsListField.getText().equals(preprocessBean.getColumnList()));
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean.setColumnList(columnsListField.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(BoundedBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        columnsListField.setText(preprocessBean.getColumnList());
    }

    abstract String getAction();
}
