package net.sf.taverna.t2.activities.stilts.ui.config.operator;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOperatorBean;
import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 * Note: This was was an idea to split Operator from Preprocessor but is not currently being used.
 * 
 * @author christian
 * @param <BoundedBean> 
 */
@SuppressWarnings("serial")
public abstract class OperatorPreProcessConfigurationPanel <BoundedBean extends StiltsOperatorBean> extends JPanel{
 
    BoundedBean operatorBean;
    
    protected static ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    OperatorPreProcessConfigurationPanel(BoundedBean operatorBean){
        this.operatorBean = operatorBean;
    }
    
   /**
      * Check that user values in UI are valid
      */
    public abstract boolean checkValues();

    /**
      * Check if the user has changed the configuration from the original
      */
    public abstract boolean isConfigurationChanged();

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    public abstract void noteConfiguration();

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public abstract void refreshConfiguration(BoundedBean operatorBean);
}
