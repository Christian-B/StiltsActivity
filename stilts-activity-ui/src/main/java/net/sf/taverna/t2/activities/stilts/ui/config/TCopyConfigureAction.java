package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;

import net.sf.taverna.t2.activities.stilts.TCopyActivity;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;

@SuppressWarnings("serial")
public class TCopyConfigureAction
		extends
		ActivityConfigurationAction<TCopyActivity, SingleInputBean> {

	public TCopyConfigureAction(TCopyActivity activity, Frame owner) {
		super(activity);
	}

        protected TCopyActivity getActivity() {
            return (TCopyActivity)activity;
        }

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		ActivityConfigurationDialog<TCopyActivity, SingleInputBean> currentDialog = ActivityConfigurationAction
				.getDialog(getActivity());
		if (currentDialog != null) {
			currentDialog.toFront();
			return;
		}
		ActivityConfigurationPanel<TCopyActivity, SingleInputBean> panel = new InputTypeConfigurationPanel(getActivity());
                
		ActivityConfigurationDialog<TCopyActivity, SingleInputBean> dialog = new ActivityConfigurationDialog<TCopyActivity, SingleInputBean>(
				getActivity(), panel);

		ActivityConfigurationAction.setDialog(getActivity(), dialog);

	}

}
