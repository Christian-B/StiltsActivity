package net.sf.taverna.t2.activities.stilts.ui.config;

import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.AbstractMatchActivity;
import net.sf.taverna.t2.activities.stilts.AbstractMatchBean;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class AbstractMatchConfigurationPanel 
        <BoundedActivity extends AbstractMatchActivity, 
        BoundedBean extends AbstractMatchBean>  extends
        MultipleFormatsConfigurationPanel<BoundedActivity, BoundedBean> {
	
    private JComboBox findSelector;
    private JComboBox joinSelector;
    private JComboBox fixcolsSelector;

    public AbstractMatchConfigurationPanel(BoundedActivity activity) {
        super(activity);
    }

    protected void initGui() {
        super.initGui();
  
        JLabel findLabel = new JLabel(AbstractMatchActivity.FIND_PARAMETER_NAME);
        miscellaneousPanel.add(findLabel,0);
        findSelector = new JComboBox(StiltsConfigurationConstants.VALID_FIND_VALUES_ARRAY);
        findSelector.setSelectedItem(configBean.getFindValue());            
        miscellaneousPanel.add(findSelector, 1);
        findLabel.setLabelFor(findSelector);

        JLabel joinLabel = new JLabel(AbstractMatchActivity.JOIN_PARAMETER_NAME);
        miscellaneousPanel.add(joinLabel, 2);
        joinSelector = new JComboBox(StiltsConfigurationConstants.VALID_JOIN_VALUES_ARRAY);
        joinSelector.setSelectedItem(configBean.getJoinValue());            
        miscellaneousPanel.add(joinSelector, 3);
        findLabel.setLabelFor(joinSelector);

        JLabel fixcolsLabel = new JLabel(AbstractMatchActivity.FIXCOLS_PARAMETER_NAME);
        miscellaneousPanel.add(fixcolsLabel,4);
        fixcolsSelector = new JComboBox(StiltsConfigurationConstants.VALID_FIXCOLS_VALUES_ARRAY);
        fixcolsSelector.setSelectedItem(configBean.getFixcolsValue());            
        miscellaneousPanel.add(fixcolsSelector, 5);
        findLabel.setLabelFor(fixcolsSelector);
    }

    /**
      * Check that user values in UI are valid
      */
    @Override
    public boolean checkValues() {
        if (!super.checkValues()){
            return false;
        }
        if (!StiltsConfigurationConstants.VALID_FIND_VALUES_LIST .contains(findSelector.getSelectedItem())){
            String message = findSelector.getSelectedItem() + 
                    " Used for " + AbstractMatchActivity.FIND_PARAMETER_NAME +  
                    " Is not a valid input format. Valid types are: " + StiltsConfigurationConstants.VALID_FIND_VALUES_LIST;
            JOptionPane.showMessageDialog(this, message, "Illegal " + AbstractMatchActivity.FIND_PARAMETER_NAME, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!StiltsConfigurationConstants.VALID_JOIN_VALUES_LIST.contains(joinSelector.getSelectedItem())){
            String message = joinSelector.getSelectedItem() + 
                    " Used for " + AbstractMatchActivity.JOIN_PARAMETER_NAME +  
                    " Is not a valid input format. Valid types are: " + StiltsConfigurationConstants.VALID_JOIN_VALUES_LIST;
            JOptionPane.showMessageDialog(this, message, "Illegal " + AbstractMatchActivity.JOIN_PARAMETER_NAME, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!StiltsConfigurationConstants.VALID_FIXCOLS_VALUES_LIST.contains(fixcolsSelector.getSelectedItem())){
            String message = fixcolsSelector.getSelectedItem() + 
                    " Used for " + AbstractMatchActivity.FIXCOLS_PARAMETER_NAME +  
                    " Is not a valid input format. Valid types are: " + StiltsConfigurationConstants.VALID_FIXCOLS_VALUES_LIST;
            JOptionPane.showMessageDialog(this, message, "Illegal " + AbstractMatchActivity.FIXCOLS_PARAMETER_NAME, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // All valid, return true
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (findSelector.getSelectedItem().equals(configBean.getFindValue())){
            return true;
        }
        if (joinSelector.getSelectedItem().equals(configBean.getJoinValue())){
            return true;
        }
        if (fixcolsSelector.getSelectedItem().equals(configBean.getFixcolsValue())){
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
        noteConfiguration(new AbstractMatchBean());
    }

    protected void noteConfiguration(AbstractMatchBean bean) {
    	super.noteConfiguration(configBean);
        configBean.setFindValue(findSelector.getSelectedItem().toString());
        configBean.setJoinValue(joinSelector.getSelectedItem().toString());
        configBean.setFixcolsValue(fixcolsSelector.getSelectedItem().toString());     
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();
        findSelector.setSelectedItem(configBean.getFindValue());
        joinSelector.setSelectedItem(configBean.getJoinValue());
        fixcolsSelector.setSelectedItem(configBean.getFixcolsValue());
    }
    
}
