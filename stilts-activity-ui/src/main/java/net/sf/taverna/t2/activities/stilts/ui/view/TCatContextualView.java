package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JComponent;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.TCatActivity;

import net.sf.taverna.t2.activities.stilts.ui.config.TCatConfigureAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;

@SuppressWarnings("serial")
public class TCatContextualView extends ContextualView {
    private final TCatActivity activity;
    private StiltsBeanPanel mainFrame;

    public TCatContextualView(TCatActivity activity) {
        super();
        this.activity = activity;
        initView();
    }

    @Override
    public JComponent getMainFrame() {
        SingleFormatMultipleInputsBean bean = activity.getConfiguration();
        mainFrame = StiltsBeanPanelFactory.getMainFrame(bean);
        return mainFrame;
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        SingleFormatMultipleInputsBean configuration = activity.getConfiguration();
        StiltsBeanPanelFactory.refreshView(configuration, mainFrame);
    }

    @Override
    public String getViewTitle() {
        SingleFormatMultipleInputsBean configuration = activity.getConfiguration();
        return "Contatenate from format " + configuration.getFormatOfInputs() + " to " + configuration.getFormatOfOutput();
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new TCatConfigureAction(activity, owner);
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
