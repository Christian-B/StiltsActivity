package net.sf.taverna.t2.activities.table.ui.config.process;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;

import net.sf.taverna.t2.activities.table.input.AbstractInputsBean;
import net.sf.taverna.t2.activities.table.process.TMatch2Bean;
import net.sf.taverna.t2.activities.table.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.table.utils.DescribableInterface;
import net.sf.taverna.t2.activities.table.utils.TableFind;
import net.sf.taverna.t2.activities.table.utils.TableFixcols;
import net.sf.taverna.t2.activities.table.utils.TableJoin;

/**
 * Base class of all TMatch2 Process Configuration Panels
 * <p>
 * Allows the specification of Stilts find join and fixCols parameters while,
 * Subclasses will specify how the match should be done.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> a specific AbstractProcessBean
 */
@SuppressWarnings("serial")
public class TMatch2ConfigurationPanel<BoundedBean extends TMatch2Bean> extends TableProcessConfigurationPanel <BoundedBean>{
     
    private final JComboBox<TableFind> findSelector;
    private final JComboBox<TableJoin> joinSelector;
    private final JComboBox<TableFixcols> fixcolsSelector;
    
    private static final String FIND_LABEL = "Use rows more than once: ";
    private static final String JOIN_LABEL = "Include which rows:";
    private static final String FIXCOLS_LABEL = "New column names";
    
    protected final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    TMatch2ConfigurationPanel(BoundedBean processBean){
        super(processBean);
        addNextRow(new JLabel(FIND_LABEL), 1);
        findSelector = new JComboBox<TableFind>(TableFind.values());
        findSelector.setRenderer(listCellRenderer);
        findSelector.setSelectedItem(processBean.getFindValue());
        addNextCol(findSelector, 1);

        addNextRow(new JLabel(JOIN_LABEL), 1);
        joinSelector = new JComboBox<TableJoin>(TableJoin.values());
        joinSelector.setRenderer(listCellRenderer);
        joinSelector.setSelectedItem(processBean.getJoinValue());
        addNextCol(joinSelector, 1);
        
        addNextRow(new JLabel(FIXCOLS_LABEL), 1);
        fixcolsSelector = new JComboBox<TableFixcols>(TableFixcols.values());
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
       
        if (!processBean.getFixcolsValue().equals(fixcolsSelector.getSelectedItem())){
            return true;
        }
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
     @Override
     public void noteConfiguration(AbstractInputsBean inputBean) {
        super.noteConfiguration(inputBean);
        processBean.setFindValue((TableFind)findSelector.getSelectedItem());
        processBean.setJoinValue((TableJoin)joinSelector.getSelectedItem());
        processBean.setFixcolsValue((TableFixcols)fixcolsSelector.getSelectedItem());
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
