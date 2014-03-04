package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JComponent;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;

import net.sf.taverna.t2.activities.stilts.TCopyActivity;
import net.sf.taverna.t2.activities.stilts.ui.config.TCopyConfigureAction;

@SuppressWarnings("serial")
public class TCopyContextualView extends AbstractStiltsContextualView {
    private final TCopyActivity activity;
    private StiltsBeanPanel mainFrame;

    public TCopyContextualView(TCopyActivity activity) {
        super();
        this.activity = activity;
        initView();
    }

    @Override
    public JComponent getMainFrame() {
        SingleInputBean bean = activity.getConfiguration();
        mainFrame = StiltsBeanPanelFactory.getMainFrame(bean);
        return mainFrame;
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        SingleInputBean configuration = activity.getConfiguration();
        StiltsBeanPanelFactory.refreshView(configuration, mainFrame);
    }

    @Override
    public String getViewTitle() {
        SingleInputBean configuration = activity.getConfiguration();
        return "Copy from format " + configuration.getFormatOfInput() + " to " + configuration.getFormatOfInput();
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new TCopyConfigureAction(activity, owner);
    }

}
