package net.sf.taverna.t2.activities.stilts.ui.config.output;

import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.ui.config.BasePanel;
import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class StiltsOutputConfigurationPanel extends BasePanel{

    StiltsBean outputBean;
    
    private final JComboBox<StiltsOutputType> outputTypeSelector;
    private final JComboBox<StiltsOutputFormat> outputFormatSelector;
    public final String FORMAT_LABEL = "Format";
    public final String TYPE_LABEL = "Type";
    protected final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    static final Logger logger = Logger.getLogger(StiltsOutputConfigurationPanel.class);

    public StiltsOutputConfigurationPanel(StiltsBean outputBean){
        this.outputBean = outputBean;
        
        addNextRow(new JLabel(TYPE_LABEL),1);
        outputTypeSelector = new JComboBox<StiltsOutputType>(StiltsOutputType.values());
        outputTypeSelector.setSelectedItem(outputBean.getOutputType());
        outputTypeSelector.setRenderer(listCellRenderer);
        addNextCol(outputTypeSelector, 1);

        addNextRow(new JLabel(FORMAT_LABEL),1);
        outputFormatSelector = new JComboBox<StiltsOutputFormat>(StiltsOutputFormat.values());
        outputFormatSelector.setSelectedItem(outputBean.getOutputFormat());
        outputFormatSelector.setRenderer(listCellRenderer);
        addNextCol(outputFormatSelector, 1);
    }

    /**
     * Check that user values in UI are valid
     */
    public boolean checkValues(){
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    public boolean isConfigurationChanged(){
        Object configType = outputTypeSelector.getSelectedItem();
        if (!outputBean.getOutputType().equals(configType)){
            logger.debug("OutputType changed");
            return true;
        }
        Object configFormat = outputFormatSelector.getSelectedItem();
        if (!outputBean.getOutputFormat().equals(configFormat)){
            logger.debug("OutputFormat changed");
            return true;
        }
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    public void noteConfiguration(StiltsBean outputBean){
        outputBean.setOutputFormat((StiltsOutputFormat)outputFormatSelector.getSelectedItem());
        outputBean.setOutputType((StiltsOutputType)outputTypeSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(StiltsBean outputBean){
        this.outputBean = outputBean;
        outputTypeSelector.setSelectedItem(outputBean.getOutputType());
        outputFormatSelector.setSelectedItem(outputBean.getOutputFormat());
    }

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
     * @return Return configuration bean generated from user interface
      */
    public StiltsBean getConfiguration(){
        return outputBean;
    }
     
}
