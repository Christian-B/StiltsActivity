package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;
import java.util.List;
import javax.swing.Action;
import javax.swing.JComponent;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.ui.config.StiltsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.StiltsConfigureAction;
import net.sf.taverna.t2.workbench.ui.actions.activity.HTMLBasedActivityContextualView;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;

/**
 * Single ContextualView class for all Stilts Activities.
 * <p>
 * There is only one implementation of ContextualView because no matter which process and preprocess is used 
 * the StiltsConfigurationPanel class is used, with the details delegated to panels.
 * Similarly there is only one bstractAsynchronousActivity (StiltsActivity) which handles the running no matter which process or preprocess is used.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class StiltsContextualView extends HTMLBasedActivityContextualView<StiltsBean>{
    private final StiltsActivity activity;
    //private StiltsBeanPanel mainFrame;

    public StiltsContextualView(StiltsActivity activity) {
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
 //       return new  StiltsConfigurationPanel(activity, StiltsConfigurationPanel.NOT_EDITABLE);
 //   }

    /**
      * Typically called when the activity configuration has changed.
      */
//    @Override
//    public void refreshView() {
//        StiltsBean bean = activity.getConfiguration();
//    }

//    @Override
//    public String getViewTitle() {
//        StiltsBean bean = activity.getConfiguration();
//        return "Test";
//   }

//    @Override
//    public Action getConfigureAction(final Frame owner) {
//        return new StiltsConfigureAction(activity, owner);
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
        List<StiltsConfiguration> configurations = activity.configurations();
        StringBuilder html = new StringBuilder();
        for (StiltsConfiguration configuration:configurations){
            html.append("<tr><td>").append(configuration.getName()).append("</td><td>").append(configuration.getItem()).append("</td></tr>");
        }
        return html.toString();
    }

}
