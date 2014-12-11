package net.sf.taverna.t2.activities.table.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import net.sf.taverna.t2.activities.table.preprocess.SortPreProcessorBean;

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

    public SortPreProcessorConfigurationPanel(SortPreProcessorBean preprocessBean){
        super(preprocessBean);
        ascendingCheckBox = new JCheckBox("Sort Data Ascending", preprocessBean.isAscending());
        addNextRow(ascendingCheckBox, 2);
        
        nullsAtBeginingCheckBox = new JCheckBox("Place nulls at beginging", preprocessBean.isNullsAtBegining());
        addNextRow(nullsAtBeginingCheckBox, 2);
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
