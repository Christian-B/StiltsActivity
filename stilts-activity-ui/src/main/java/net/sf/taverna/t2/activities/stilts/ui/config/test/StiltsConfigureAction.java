package net.sf.taverna.t2.activities.stilts.ui.config.test;

import net.sf.taverna.t2.activities.stilts.ui.config.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.test.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.test.StiltsBean;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;

@SuppressWarnings("serial")
public class StiltsConfigureAction extends
		ActivityConfigurationAction<StiltsActivity, StiltsBean> {

    public StiltsConfigureAction(StiltsActivity activity, Frame owner) {
        super(activity);
    }

    protected StiltsActivity getActivity() {
        return activity;
    }

    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        ActivityConfigurationDialog<StiltsActivity, StiltsBean> currentDialog = 
                ActivityConfigurationAction.getDialog(getActivity());
        
        if (currentDialog != null) {
            currentDialog.toFront();
            return;
        }
        StiltsConfigurationPanel panel = new StiltsConfigurationPanel(getActivity());
        
        ActivityConfigurationDialog<StiltsActivity, StiltsBean> dialog = 
                new ActivityConfigurationDialog<StiltsActivity, StiltsBean>(
                        getActivity(), panel);

        ActivityConfigurationAction.setDialog(getActivity(), dialog);

    }

}
