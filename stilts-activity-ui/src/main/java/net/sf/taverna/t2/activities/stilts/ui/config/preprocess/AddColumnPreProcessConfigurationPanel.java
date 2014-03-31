package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;
import net.sf.taverna.t2.activities.stilts.utils.StiltsLocationType;

@SuppressWarnings("serial")
public class AddColumnPreProcessConfigurationPanel <BoundedBean extends AddColumnPreProcessorBean> extends StiltsPreProcessConfigurationPanel<BoundedBean>{
 
    private JComboBox<StiltsLocationType> locationTypeSelector;
    private JTextField locationColumnField;
    private JTextField newColumnNameField;
    
    private static final String NEW_COLUMN_NAME_LABEL = "Name of new Column";
    private static final String NEW_COLUMN_LOCATION = "Location to add new column";
    private static final String NEW_COLUMN_REFFERENCE = "Reference Column for location" ;       
    
    protected static ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    AddColumnPreProcessConfigurationPanel(BoundedBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
    }

    @Override
    void addEditable(BoundedBean preprocessBean){ 
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel(NEW_COLUMN_NAME_LABEL), c);
        c.gridx = 1;
        newColumnNameField = new JTextField(preprocessBean.getNewColName(),20);
        add(newColumnNameField, c);
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel(NEW_COLUMN_LOCATION), c);
        c.gridx = 1;
        StiltsLocationType locationType = preprocessBean.getNewColumnLocation();
        locationTypeSelector = new JComboBox<StiltsLocationType>(StiltsLocationType.values());
        locationTypeSelector.setSelectedItem(locationType);
        locationTypeSelector.setRenderer(listCellRenderer);
        add(locationTypeSelector, c);
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel(NEW_COLUMN_REFFERENCE), c);
        c.gridx = 1;
        if (preprocessBean.getLocationColumn() != null){
            locationColumnField = new JTextField(preprocessBean.getLocationColumn(), 20);
        } else {
            locationColumnField = new JTextField(20);
        }
        add(locationColumnField, c);
    }
    
    protected final int nextRow(){
        return 3;
    }
    
    /**
      * Check that user values in UI are valid
      */
    public boolean checkValues() {
/*        if (newColumnNameField.getText().trim().isEmpty()){
            String message = NEW_COLUMN_NAME_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + NEW_COLUMN_NAME_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!locationTypeSelector.getSelectedItem().equals(StiltsLocationType.END)){
            if (locationColumnField.getText().trim().isEmpty()){
                String message = NEW_COLUMN_REFFERENCE + " can not be empty when " + NEW_COLUMN_LOCATION + " set to " + locationTypeSelector.getSelectedItem(); 
                JOptionPane.showMessageDialog(this, message, "Empty " + NEW_COLUMN_REFFERENCE, JOptionPane.ERROR_MESSAGE);
                return false;
            }        
        }
*/        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    public boolean isConfigurationChanged() {
        if (!newColumnNameField.getText().equals(preprocessBean.getNewColName())){
            return true;
        }
        if (!locationTypeSelector.getSelectedItem().equals(StiltsLocationType.END)){
            if (!locationColumnField.getText().equals(preprocessBean.getLocationColumn())){
                return true;
            }
        }        
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    public void noteConfiguration() {
        preprocessBean.setNewColName(newColumnNameField.getText());
        preprocessBean.setNewColumnLocation((StiltsLocationType)locationTypeSelector.getSelectedItem());
        if (preprocessBean.getNewColumnLocation().equals(StiltsLocationType.END)){
            preprocessBean.setLocationColumn(null);
        } else {
            preprocessBean.setLocationColumn(locationColumnField.getText());
        }
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(BoundedBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        newColumnNameField = new JTextField(preprocessBean.getNewColName());
        locationTypeSelector.setSelectedItem(preprocessBean.getNewColumnLocation());
        locationColumnField = new JTextField(preprocessBean.getNewColName());
    }
}
