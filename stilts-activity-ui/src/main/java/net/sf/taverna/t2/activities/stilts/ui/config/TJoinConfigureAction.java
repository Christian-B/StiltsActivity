package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsActivity;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.TCatActivity;
import net.sf.taverna.t2.activities.stilts.TCatNActivity;
import net.sf.taverna.t2.activities.stilts.TJoinActivity;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;


@SuppressWarnings("serial")
public class TJoinConfigureAction extends
		ActivityConfigurationAction<TJoinActivity, MultipleFormatsBean> {

    public TJoinConfigureAction(TJoinActivity activity, Frame owner) {
        super(activity);
    }

    protected TJoinActivity getActivity() {
        return activity;
    }

    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        ActivityConfigurationDialog<TJoinActivity, SingleInputBean> currentDialog = 
                ActivityConfigurationAction.getDialog(getActivity());
        if (currentDialog != null) {
            currentDialog.toFront();
            return;
        }
        AbstractStiltsConfigurationPanel<TJoinActivity, MultipleFormatsBean> panel = 
                new MultipleFormatsConfigurationPanel(getActivity());
        
        ActivityConfigurationDialog<TJoinActivity, MultipleFormatsBean> dialog = 
                new ActivityConfigurationDialog<TJoinActivity, MultipleFormatsBean>(
                        getActivity(), panel);

        ActivityConfigurationAction.setDialog(getActivity(), dialog);

    }

}
