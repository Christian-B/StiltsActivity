package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.TCatActivity;

import net.sf.taverna.t2.activities.stilts.TCopyActivity;
import net.sf.taverna.t2.activities.stilts.ui.config.TCatConfigureAction;
import net.sf.taverna.t2.activities.stilts.ui.config.TCopyConfigureAction;

@SuppressWarnings("serial")
public class TCatContextualView extends AbstractStiltsContextualView {
    private final TCatActivity activity;
    private JLabel description = new JLabel("ads");

    public TCatContextualView(TCatActivity activity) {
        super();
        this.activity = activity;
        initView();
    }

    @Override
    public String getViewTitle() {
        SingleFormatMultipleInputsBean configuration = activity.getConfiguration();
        return "Contatenate from format " + configuration.getFormatOfInputs() + " to " + configuration.getFormatOfOutput();
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        SingleFormatMultipleInputsBean configuration = activity.getConfiguration();
        description.setText("Copy from format " + configuration.getFormatOfInputs() 
                + " to " + configuration.getFormatOfOutput());
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new TCatConfigureAction(activity, owner);
    }

}
