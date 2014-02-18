package net.sf.taverna.t2.activities.stilts.ui.view;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;

@SuppressWarnings("serial")
public abstract class AbstractStiltsContextualView extends ContextualView {
    private JLabel description = new JLabel("ads");

    @Override
    public JComponent getMainFrame() {
        JPanel jPanel = new JPanel();
        jPanel.add(description);
        refreshView();
        return jPanel;
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
