package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.InputTypeBean;

import net.sf.taverna.t2.activities.stilts.TCopyActivity;
import net.sf.taverna.t2.activities.stilts.ui.config.TCopyConfigureAction;

@SuppressWarnings("serial")
public class TCopyContextualView extends AbstractStiltsContextualView {
    private final TCopyActivity activity;
    private JLabel description = new JLabel("ads");

    public TCopyContextualView(TCopyActivity activity) {
        super();
        this.activity = activity;
        initView();
    }

    @Override
    public String getViewTitle() {
        InputTypeBean configuration = activity.getConfiguration();
        return "Copy from format " + configuration.getFormatOfInput() + " to " + configuration.getFormatOfInput();
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        InputTypeBean configuration = activity.getConfiguration();
        description.setText("Copy from format " + configuration.getFormatOfInput() 
                + " to " + configuration.getFormatOfInput());
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new TCopyConfigureAction(activity, owner);
    }

}
