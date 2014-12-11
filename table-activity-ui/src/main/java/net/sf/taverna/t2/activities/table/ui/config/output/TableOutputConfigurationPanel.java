package net.sf.taverna.t2.activities.table.ui.config.output;

import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.ui.config.BasePanel;
import net.sf.taverna.t2.activities.table.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.table.utils.DescribableInterface;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class TableOutputConfigurationPanel extends BasePanel{

    TableActivityConfigurationBean outputBean;
    
    private final JComboBox<TableOutputType> outputTypeSelector;
    private final JComboBox<TableOutputFormat> outputFormatSelector;
    public final String FORMAT_LABEL = "Format";
    public final String TYPE_LABEL = "Type";
    protected final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    
    static final Logger logger = Logger.getLogger(TableOutputConfigurationPanel.class);

    public TableOutputConfigurationPanel(TableActivityConfigurationBean outputBean){
        this.outputBean = outputBean;
        
        addNextRow(new JLabel(TYPE_LABEL),1);
        outputTypeSelector = new JComboBox<TableOutputType>(TableOutputType.values());
        outputTypeSelector.setSelectedItem(outputBean.getOutputType());
        outputTypeSelector.setRenderer(listCellRenderer);
        addNextCol(outputTypeSelector, 1);

        addNextRow(new JLabel(FORMAT_LABEL),1);
        outputFormatSelector = new JComboBox<TableOutputFormat>(TableOutputFormat.values());
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
    public void noteConfiguration(TableActivityConfigurationBean outputBean){
        outputBean.setOutputFormat((TableOutputFormat)outputFormatSelector.getSelectedItem());
        outputBean.setOutputType((TableOutputType)outputTypeSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(TableActivityConfigurationBean outputBean){
        this.outputBean = outputBean;
        outputTypeSelector.setSelectedItem(outputBean.getOutputType());
        outputFormatSelector.setSelectedItem(outputBean.getOutputFormat());
    }

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
     * @return Return configuration bean generated from user interface
      */
    public TableActivityConfigurationBean getConfiguration(){
        return outputBean;
    }
     
}
