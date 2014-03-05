package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JComponent;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.TJoinActivity;

import net.sf.taverna.t2.activities.stilts.ui.config.TJoinConfigureAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;

@SuppressWarnings("serial")
public class TJoinContextualView extends ContextualView {
    private final TJoinActivity activity;
    private StiltsBeanPanel mainFrame;

    public TJoinContextualView(TJoinActivity activity) {
        super();
        this.activity = activity;
        initView();
    }

    @Override
    public JComponent getMainFrame() {
        MultipleFormatsBean bean = activity.getConfiguration();
        mainFrame = StiltsBeanPanelFactory.getMainFrame(bean);
        return mainFrame;
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        MultipleFormatsBean configuration = activity.getConfiguration();
        StiltsBeanPanelFactory.refreshView(configuration, mainFrame);
    }

    @Override
    public String getViewTitle() {
        MultipleFormatsBean configuration = activity.getConfiguration();
        return "Contatenate from to " + configuration.getFormatOfOutput();
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new TJoinConfigureAction(activity, owner);
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
