package net.sf.taverna.t2.activities.table.ui.view;

import java.awt.Frame;
import java.util.List;
import javax.swing.Action;
import javax.swing.JComponent;
import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.ui.config.TableActivityConfigurationPanel;
import net.sf.taverna.t2.activities.table.ui.config.TableActivityConfigureAction;
import net.sf.taverna.t2.workbench.ui.actions.activity.HTMLBasedActivityContextualView;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;

/**
 * Single ContextualView class for all Stilts Activities.
 * <p>
 * There is only one implementation of ContextualView because no matter which process and preprocess is used 
 * the TableActivityConfigurationPanel class is used, with the details delegated to panels.
 * Similarly there is only one bstractAsynchronousActivity (TableActivity) which handles the running no matter which process or preprocess is used.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TableActivityContextualView extends HTMLBasedActivityContextualView<TableActivityConfigurationBean>{
    private final TableActivity activity;
    //private StiltsBeanPanel mainFrame;

    public TableActivityContextualView(TableActivity activity) {
        super(activity);
        this.activity = activity;
        initView();
    }

    @Override
    public String getViewTitle() {
        try {
            return activity.getTitle();
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

 //   @Override
 //   public JComponent getMainFrame() {
 //       return new  TableActivityConfigurationPanel(activity, TableActivityConfigurationPanel.NOT_EDITABLE);
 //   }

    /**
      * Typically called when the activity configuration has changed.
      */
//    @Override
//    public void refreshView() {
//        TableActivityConfigurationBean bean = activity.getConfiguration();
//    }

//    @Override
//    public String getViewTitle() {
//        TableActivityConfigurationBean bean = activity.getConfiguration();
//        return "Test";
//   }

//    @Override
//    public Action getConfigureAction(final Frame owner) {
//        return new TableActivityConfigureAction(activity, owner);
//    }
    
   /**
      * View position hint
      */
    @Override
    public int getPreferredPosition() {
        // We want to be on top
        return 100;
    }

    @Override
    protected String getRawTableRowsHtml() {
        if (activity == null){
            return "null";
        }
        StringBuilder html = new StringBuilder();
        for (TableParameterConfiguration configuration:activity.configurations()){
            addHtmlRow(html, configuration.getName(), configuration.getItem());
        }
        return html.toString();
    }

    private void addHtmlRow(StringBuilder html, String name, Object item) {
        html.append("<tr><td>");
        html.append(name);
        html.append("</td><td>");
        html.append(item);
        html.append("</td></tr>");
    }
        
    @Override
    public Action getConfigureAction(final Frame owner) {
        return new TableActivityConfigureAction(activity, owner);
    }

}
