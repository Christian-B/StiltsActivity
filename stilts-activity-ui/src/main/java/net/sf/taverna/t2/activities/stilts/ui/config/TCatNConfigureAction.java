package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsActivity;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.TCatActivity;
import net.sf.taverna.t2.activities.stilts.TCatNActivity;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;


@SuppressWarnings("serial")
public class TCatNConfigureAction extends
		ActivityConfigurationAction<TCatNActivity, MultipleFormatsBean> {

    public TCatNConfigureAction(TCatNActivity activity, Frame owner) {
        super(activity);
    }

    protected TCatNActivity getActivity() {
        return activity;
    }

    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        ActivityConfigurationDialog<TCatActivity, SingleInputBean> currentDialog = 
                ActivityConfigurationAction.getDialog(getActivity());
        if (currentDialog != null) {
            currentDialog.toFront();
            return;
        }
        AbstractStiltsConfigurationPanel<TCatNActivity, MultipleFormatsBean> panel = 
                new MultipleFormatsConfigurationPanel(getActivity());
        
        ActivityConfigurationDialog<TCatNActivity, MultipleFormatsBean> dialog = 
                new ActivityConfigurationDialog<TCatNActivity, MultipleFormatsBean>(
                        getActivity(), panel);

        ActivityConfigurationAction.setDialog(getActivity(), dialog);

    }

}
