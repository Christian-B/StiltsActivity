package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.TCatActivity;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;

import net.sf.taverna.t2.activities.stilts.TCopyActivity;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationPanel;

@SuppressWarnings("serial")
public class TCatConfigureAction extends
		ActivityConfigurationAction<TCatActivity, SingleFormatMultipleInputsBean> {

    public TCatConfigureAction(TCatActivity activity, Frame owner) {
        super(activity);
    }

    protected TCatActivity getActivity() {
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
        System.out.println("creating");
        AbstractStiltsConfigurationPanel<TCatActivity, SingleInputBean> panel = 
                new SingleFormatMultipleInputsConfigurationPanel(getActivity());
        System.out.println("created");
        panel.refreshConfiguration();
        System.out.println("refresh");
        
        ActivityConfigurationDialog<TCatActivity, SingleInputBean> dialog = 
                new ActivityConfigurationDialog<TCatActivity, SingleInputBean>(
                        getActivity(), panel);

        ActivityConfigurationAction.setDialog(getActivity(), dialog);

    }

}
