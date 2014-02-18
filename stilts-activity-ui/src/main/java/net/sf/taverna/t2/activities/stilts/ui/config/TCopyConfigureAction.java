package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.InputTypeBean;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;

import net.sf.taverna.t2.activities.stilts.TcopyActivity;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;

@SuppressWarnings("serial")
public class TCopyConfigureAction
		extends
		ActivityConfigurationAction<TcopyActivity, InputTypeBean> {

	public TCopyConfigureAction(TcopyActivity activity, Frame owner) {
		super(activity);
	}

        protected TcopyActivity getActivity() {
            return (TcopyActivity)activity;
        }

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		ActivityConfigurationDialog<TcopyActivity, InputTypeBean> currentDialog = ActivityConfigurationAction
				.getDialog(getActivity());
		if (currentDialog != null) {
			currentDialog.toFront();
			return;
		}
		ActivityConfigurationPanel<TcopyActivity, InputTypeBean> panel = new InputTypeConfigurationPanel(getActivity());
                
		ActivityConfigurationDialog<TcopyActivity, InputTypeBean> dialog = new ActivityConfigurationDialog<TcopyActivity, InputTypeBean>(
				getActivity(), panel);

		ActivityConfigurationAction.setDialog(getActivity(), dialog);

	}

}
