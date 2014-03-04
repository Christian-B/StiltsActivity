package net.sf.taverna.t2.activities.stilts.ui.view;

import java.awt.Frame;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;

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
    public JComponent getMainFrame() {
        JPanel jPanel = new JPanel();
        jPanel.add(description);
        refreshView();
        return jPanel;
    }

    @Override
    public String getViewTitle() {
        SingleInputBean configuration = activity.getConfiguration();
        return "Copy from format " + configuration.getFormatOfInput() + " to " + configuration.getFormatOfInput();
    }

    /**
      * Typically called when the activity configuration has changed.
      */
    @Override
    public void refreshView() {
        SingleInputBean configuration = activity.getConfiguration();
//        description.setText("Copy from format " + configuration.getFormatOfInput() 
//                + " to " + configuration.getFormatOfInput());
    }

    @Override
    public Action getConfigureAction(final Frame owner) {
        return new TCopyConfigureAction(activity, owner);
    }

}
