package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.InputTypeBean;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;

import net.sf.taverna.t2.activities.stilts.TCopyActivity;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;

@SuppressWarnings("serial")
public class TCopyConfigureAction
		extends
		ActivityConfigurationAction<TCopyActivity, InputTypeBean> {

	public TCopyConfigureAction(TCopyActivity activity, Frame owner) {
		super(activity);
	}

        protected TCopyActivity getActivity() {
            return (TCopyActivity)activity;
        }

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		ActivityConfigurationDialog<TCopyActivity, InputTypeBean> currentDialog = ActivityConfigurationAction
				.getDialog(getActivity());
		if (currentDialog != null) {
			currentDialog.toFront();
			return;
		}
		ActivityConfigurationPanel<TCopyActivity, InputTypeBean> panel = new InputTypeConfigurationPanel(getActivity());
                
		ActivityConfigurationDialog<TCopyActivity, InputTypeBean> dialog = new ActivityConfigurationDialog<TCopyActivity, InputTypeBean>(
				getActivity(), panel);

		ActivityConfigurationAction.setDialog(getActivity(), dialog);

	}

}
