package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.TwoInputsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.process.TMatch2Bean;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;

/**
 * Base class of all TMatch2 Process Configuration Panels
 * <p>
 * Allows the specification of Stilts find join and fixCols parameters while,
 * Subclasses will specify how the match should be done.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> a specific StiltsProcessBean
 */
@SuppressWarnings("serial")
public class TMatch2ConfigurationPanel<BoundedBean extends TMatch2Bean> extends StiltsProcessConfigurationPanel <BoundedBean>{
     
    private JComboBox<StiltsFind> findSelector;
    private JComboBox<StiltsJoin> joinSelector;
    private JComboBox<StiltsFixcols> fixcolsSelector;
    
    private static final String FIND_LABEL = "Stils find Value";
    private static final String JOIN_LABEL = "Stils join Value";
    private static final String FIXCOLS_LABEL = "Stils fixcols Value";
    
    protected final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    private final boolean editable;
    
    TMatch2ConfigurationPanel(BoundedBean processBean, TwoInputsConfigurationPanel inputPanel, boolean editable){
        super(processBean, inputPanel);
        this.editable = editable;
        processPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;
        JLabel findLabel = new JLabel(FIND_LABEL);
        processPanel.add(findLabel, c);
        c.gridx = 1;
        if (editable){
            findSelector = new JComboBox<StiltsFind>(StiltsFind.values());
            findSelector.setRenderer(listCellRenderer);
            findSelector.setSelectedItem(processBean.getFindValue());
            processPanel.add(findSelector, c);
        } else {
            JLabel findInfo = new JLabel(processBean.getFindValue().toString());  
            findInfo.setToolTipText(processBean.getFindValue().getDescription());
            processPanel.add(findInfo, c);
        }    

        c.gridx = 0;
        c.gridy = 2;        
        JLabel joinLabel = new JLabel(JOIN_LABEL);
        processPanel.add(joinLabel, c);
        c.gridx = 1;
        if (editable){
            joinSelector = new JComboBox<StiltsJoin>(StiltsJoin.values());
            joinSelector.setRenderer(listCellRenderer);
            joinSelector.setSelectedItem(processBean.getJoinValue());
            processPanel.add(joinSelector, c);
        } else {
            JLabel joinInfo = new JLabel(processBean.getJoinValue().toString());  
            joinInfo.setToolTipText(processBean.getJoinValue().getDescription());
            processPanel.add(joinInfo, c);
        }        
        
        c.gridx = 0;
        c.gridy = 3;
        JLabel fixcolsLabel = new JLabel(FIXCOLS_LABEL);
        processPanel.add(fixcolsLabel, c);
        c.gridx = 1;
        if (editable){
            fixcolsSelector = new JComboBox<StiltsFixcols>(StiltsFixcols.values());
            fixcolsSelector.setRenderer(listCellRenderer);
            fixcolsSelector.setSelectedItem(processBean.getFixcolsValue());
            processPanel.add(fixcolsSelector, c);
        } else {
            JLabel fixcolsInfo = new JLabel(processBean.getFixcolsValue().toString());  
            fixcolsInfo.setToolTipText(processBean.getFixcolsValue().getDescription());
            processPanel.add(fixcolsInfo, c);       
        }
    }

    @Override
    int getInputRow(){
        return 4;
    }
    
    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (!editable){
            return false; //not editable so never changed!
        }
        if (!processBean.getFindValue().equals(findSelector.getSelectedItem())){
            return true;
        }
        if (!processBean.getJoinValue().equals(joinSelector.getSelectedItem())){
            return true;
        }
       
        if (processBean.getFixcolsValue().equals(fixcolsSelector.getSelectedItem())){
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
        if (!editable){
            return; //Should never be called but just in case do nothing
        }
        super.noteConfiguration();
        processBean.setFindValue((StiltsFind)findSelector.getSelectedItem());
        processBean.setJoinValue((StiltsJoin)joinSelector.getSelectedItem());
        processBean.setFixcolsValue((StiltsFixcols)fixcolsSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(BoundedBean processBean) {
        super.refreshConfiguration(processBean);   
        findSelector.setSelectedItem(processBean.getFindValue());
        joinSelector.setSelectedItem(processBean.getJoinValue());
        fixcolsSelector.setSelectedItem(processBean.getFixcolsValue());
    }
    
}
