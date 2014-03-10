package net.sf.taverna.t2.activities.stilts.ui.view;

import net.sf.taverna.t2.activities.stilts.ui.config.StiltsConfigureAction;
import net.sf.taverna.t2.activities.stilts.ui.config.StiltsConfigurationPanel;
import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JComponent;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.StiltsBean;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;

@SuppressWarnings("serial")
public class StiltsContextualView extends ContextualView {
    private final StiltsActivity activity;
    //private StiltsBeanPanel mainFrame;

    public StiltsContextualView(StiltsActivity activity) {
        super();
        this.activity = activity;
        initView();
    }

    @Override
    public JComponent getMainFrame() {
        return new  StiltsConfigurationPanel(activity, StiltsConfigurationPanel.NOT_EDITABLE);
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        StiltsBean bean = activity.getConfiguration();
    }

    @Override
    public String getViewTitle() {
        StiltsBean bean = activity.getConfiguration();
        return "Test";
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new StiltsConfigureAction(activity, owner);
    }
    
   /**
      * View position hint
      */
    @Override
    public int getPreferredPosition() {
        // We want to be on top
        return 100;
    }

}
