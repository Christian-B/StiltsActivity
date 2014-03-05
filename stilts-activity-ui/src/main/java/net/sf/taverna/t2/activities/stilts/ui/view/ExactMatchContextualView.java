package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JComponent;
import net.sf.taverna.t2.activities.stilts.ExactMatchActivity;
import net.sf.taverna.t2.activities.stilts.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.ui.config.ExactMatchConfigureAction;

import net.sf.taverna.t2.activities.stilts.ui.config.TCatConfigureAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;

@SuppressWarnings("serial")
public class ExactMatchContextualView extends ContextualView {
    private final ExactMatchActivity activity;
    private StiltsBeanPanel mainFrame;

    public ExactMatchContextualView(ExactMatchActivity activity) {
        super();
        this.activity = activity;
        initView();
    }

    @Override
    public JComponent getMainFrame() {
        ExactMatchBean bean = activity.getConfiguration();
        mainFrame = StiltsBeanPanelFactory.getMainFrame(bean);
        return mainFrame;
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        ExactMatchBean configuration = activity.getConfiguration();
        StiltsBeanPanelFactory.refreshView(configuration, mainFrame);
    }

    @Override
    public String getViewTitle() {
        ExactMatchBean configuration = activity.getConfiguration();
        return "Exac Match based on " + configuration.getNumbertOfColumnsToMatch() + " columns!";
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new ExactMatchConfigureAction(activity, owner);
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
