package net.sf.taverna.t2.activities.table.ui.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;


import net.sf.taverna.t2.activities.table.*;
import net.sf.taverna.t2.activities.table.ui.config.input.TableInputConfigurationPanel;
import net.sf.taverna.t2.activities.table.ui.config.output.TableOutputConfigurationPanel;
import net.sf.taverna.t2.activities.table.ui.config.preprocess.TablePreProcessConfigurationPanel;
import net.sf.taverna.t2.activities.table.ui.config.process.TableProcessConfigurationPanel;
import net.sf.taverna.t2.activities.table.ui.serviceprovider.TableServiceDesc;
import net.sf.taverna.t2.activities.table.utils.*;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;
import org.apache.log4j.Logger;

/**
 * Main/Root Configuration panel for the Stilts Process.
 * <p>
 * Adds four panels to the gui.
 * <p>
 * processPanel which contains the information for the specific process being used.
 * This will contain a superclass of TableProcessConfigurationPanel depending on the process being done.
 * <p>
 * preprocessPanel Added if required to obtain settings for the preprocessor
 * This will contain a super class of TablePreProcessConfigurationPanel depending on the actual preprocessing being done.
 * <p>
 * OutputPanel: To get the output type and format
 * <p>
 * miscellaneousPanel: Any other information such as the debug state.
 * <p>
 * This class includes factory methods createProcessPanel and createPreprocessPanel to create the processPanel and preprocessPanel panels.
 * The specific detail of the methods checkValues(), getConfiguration(), isConfigurationChanged(), noteConfiguration() and refreshConfiguration()
 * is delegated to these panels.
 * <p>
 * This allows a single ActivityConfigurationAction, ContextualViewFactory, ContextualView implementation to be used for all Processes and preprocesses, 
 * rather than having to have specific implementations for all combination of these.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TableActivityConfigurationPanel extends
        ActivityConfigurationPanel<TableActivity, TableActivityConfigurationBean> {

    private final TableActivity activity;
    private static final ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();
    private TableActivityConfigurationBean bean;
    
    final TableInputConfigurationPanel inputPanel;
    final TableOutputConfigurationPanel outputPanel;
    final TableProcessConfigurationPanel processPanel;
    final TablePreProcessConfigurationPanel preprocessPanel;
    
    static final Logger logger = Logger.getLogger(TableActivityConfigurationPanel.class);
    
    public TableActivityConfigurationPanel(TableActivity activity) {
        this.activity = activity;
        bean = activity.getConfiguration();
        setLayout(new GridBagLayout());
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, null,
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Lucida Grande", 1, 12)));
        outputPanel = new TableOutputConfigurationPanel(bean);
        processPanel = TableProcessConfigurationPanel.factory(bean.getProcess());
        preprocessPanel = TablePreProcessConfigurationPanel.factory(bean.getPreprocess());
        inputPanel = TableInputConfigurationPanel.factory(bean.getProcess().getInputs());
//        refreshConfiguration();
        addTabbedPane();
   }
    
    private void addTabbedPane(){
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Inputs", inputPanel);
        if (processPanel.isConfigurable()){
            tabbedPane.addTab("Process", processPanel);
        }
        if (preprocessPanel != null){
            tabbedPane.addTab("Reconfiguration", preprocessPanel);
        }
        tabbedPane.addTab("Outputs", outputPanel);     
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        GridBagConstraints outerConstraint = new GridBagConstraints();
        outerConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        outerConstraint.gridx = 0;  
        outerConstraint.gridy = 0;

        outerConstraint.fill = GridBagConstraints.BOTH;
        outerConstraint.weighty = 0.1;
        outerConstraint.weightx = 0.1;
        add(tabbedPane, outerConstraint);
    }
     
    @Override
    public boolean isConfigurationChanged() {
        logger.debug("isConfigurationChanged");
        if (processPanel.isConfigurationChanged()){
            logger.debug("process Changed");
            return true;
        }
        if (inputPanel.isConfigurationChanged()){
            logger.debug("input Changed");
            return true;
        }
        return outputPanel.isConfigurationChanged();
    }

    @Override
    public TableActivityConfigurationBean getConfiguration() {
        return activity.getConfiguration();
    }

    @Override
    public void noteConfiguration() {
        inputPanel.noteConfiguration();
        processPanel.noteConfiguration(inputPanel.getConfiguration());
        bean.setProcess(processPanel.getConfiguration());
        if (preprocessPanel != null){
            preprocessPanel.noteConfiguration();
            bean.setPreprocess(preprocessPanel.getConfiguration());
        }
        outputPanel.noteConfiguration(bean);
    }
    
    @Override
    public final void refreshConfiguration() {
        bean = activity.getConfiguration();
        processPanel.refreshConfiguration(bean.getProcess());
        inputPanel.refreshConfiguration(bean.getProcess().getInputs());
        outputPanel.refreshConfiguration(bean);
    }
    
    @Override
    public boolean checkValues() {
        if (processPanel.checkValues()){
            if (inputPanel.checkValues()){
                return outputPanel.checkValues();
            }
        } 
        return false;
    }

}
