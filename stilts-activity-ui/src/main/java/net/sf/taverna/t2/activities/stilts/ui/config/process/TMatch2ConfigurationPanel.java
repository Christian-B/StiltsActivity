package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.input.StitlsInputsBean;
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
     
    private final JComboBox<StiltsFind> findSelector;
    private final JComboBox<StiltsJoin> joinSelector;
    private final JComboBox<StiltsFixcols> fixcolsSelector;
    
    private static final String FIND_LABEL = "Stils find Value";
    private static final String JOIN_LABEL = "Stils join Value";
    private static final String FIXCOLS_LABEL = "Stils fixcols Value";
    
    protected final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    TMatch2ConfigurationPanel(BoundedBean processBean){
        super(processBean);
        addNextRow(new JLabel(FIND_LABEL), 1);
        findSelector = new JComboBox<StiltsFind>(StiltsFind.values());
        findSelector.setRenderer(listCellRenderer);
        findSelector.setSelectedItem(processBean.getFindValue());
        addNextCol(findSelector, 1);

        addNextRow(new JLabel(JOIN_LABEL), 1);
        joinSelector = new JComboBox<StiltsJoin>(StiltsJoin.values());
        joinSelector.setRenderer(listCellRenderer);
        joinSelector.setSelectedItem(processBean.getJoinValue());
        addNextCol(joinSelector, 1);
        
        addNextRow(new JLabel(FIXCOLS_LABEL), 1);
        fixcolsSelector = new JComboBox<StiltsFixcols>(StiltsFixcols.values());
        fixcolsSelector.setRenderer(listCellRenderer);
        fixcolsSelector.setSelectedItem(processBean.getFixcolsValue());
        addNextCol(fixcolsSelector, 1);
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
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
     public void noteConfiguration(StitlsInputsBean inputBean) {
        super.noteConfiguration(inputBean);
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
    
    @Override
    public boolean isConfigurable() {
        return true;
    }

}
