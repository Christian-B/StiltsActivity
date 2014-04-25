package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.preprocess.SortPreProcessorBean;

/**
 * Sort PreProcess Configuration Panels
 * <p>
 * Allows users to specify how the sort should be handled for example ascending vs descending and where to place nulls.
 * Specification of the columns list is handled by the super class.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SortPreProcessorConfigurationPanel extends ColumnListPreProcessorConfigurationPanel<SortPreProcessorBean>{
 
    private JCheckBox ascendingCheckBox;
    private JCheckBox nullsAtBeginingCheckBox;

    public SortPreProcessorConfigurationPanel(SortPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
    }

    @Override
    void addEditable(SortPreProcessorBean preprocessBean){ 
        super.addEditable(preprocessBean);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = nextY();
        c.gridwidth = 1;
        JLabel label1 = new JLabel ("Sort Data Ascending");
        add(label1, c);
        c.gridx = 1;
        ascendingCheckBox = new JCheckBox();
        ascendingCheckBox.setSelected(preprocessBean.isAscending());
        add(ascendingCheckBox,c);
        
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        nullsAtBeginingCheckBox = new JCheckBox("Place nuls at beginging", preprocessBean.isNullsAtBegining());
        add(nullsAtBeginingCheckBox, c);
    }
    
    //Not required as a checkbox always has a valid value
    //@Override
    // public boolean checkValues() {
    //    if (!super.checkValues()){
    //        return false;
    //    }
    //}
    
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (ascendingCheckBox.isSelected() != this.preprocessBean.isAscending()){
            return true;
        }
        if (nullsAtBeginingCheckBox.isSelected() != this.preprocessBean.isNullsAtBegining()){
            return true;
        }
        return false;
    }
    
    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean = new SortPreProcessorBean();
        super.noteConfiguration();
        preprocessBean.setAscending(ascendingCheckBox.isSelected());
        preprocessBean.setNullsAtBegining(nullsAtBeginingCheckBox.isSelected());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(SortPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        ascendingCheckBox.setSelected(preprocessBean.isAscending());
        nullsAtBeginingCheckBox.setSelected(preprocessBean.isNullsAtBegining());
    }

    @Override
    String getAction() {
        return "Sort";
    }
}
