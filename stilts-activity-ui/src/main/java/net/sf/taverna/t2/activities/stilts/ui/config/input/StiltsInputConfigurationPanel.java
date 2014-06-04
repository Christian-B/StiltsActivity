package net.sf.taverna.t2.activities.stilts.ui.config.input;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.input.FlexibleInputsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.input.StitlsInputsBean;
import net.sf.taverna.t2.activities.stilts.input.TwoInputsBean;
import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;

/**
 * Base class for the Input Configuration panel.
 * Allows various processes to share the same configuration code if they share the same type of input.
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> Any type of StitlsInputsBean
 */
@SuppressWarnings("serial")
public abstract class StiltsInputConfigurationPanel<BoundedBean extends StitlsInputsBean> extends JPanel{

    BoundedBean inputBean;
    
    public final String FORMAT_LABEL = "Format";
    public final String TYPE_LABEL = "Type";
    protected final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();

    
    StiltsInputConfigurationPanel(BoundedBean inputBean){
        this.inputBean = inputBean;
    }
    
     /**
      * Check that user values in UI are valid
     * @return 
      */
    public abstract boolean checkValues();

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
     * @return Return configuration bean generated from user interface
      */
    public BoundedBean getConfiguration(){
        return inputBean;
    }

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
    public void refreshConfiguration(BoundedBean inputBean){
        this.inputBean = inputBean;
    }
    
    public static StiltsInputConfigurationPanel factory(StitlsInputsBean inputBean) {
        if (inputBean instanceof FlexibleInputsBean){
            return new FlexibleInputsConfigurationPanel((FlexibleInputsBean)inputBean);
        }
        if (inputBean instanceof SingleFormatMultipleInputsBean){
            return new SingleFormatMultipleInputsConfigurationPanel((SingleFormatMultipleInputsBean)inputBean);
        }
        if (inputBean instanceof SingleInputBean){
            return new SingleInputConfigurationPanel((SingleInputBean)inputBean);
        }
        if (inputBean instanceof TwoInputsBean){
            return new TwoInputsConfigurationPanel((TwoInputsBean)inputBean);
        }
        throw new UnsupportedOperationException(inputBean.getClass() + " not supported");
    }
 
}
