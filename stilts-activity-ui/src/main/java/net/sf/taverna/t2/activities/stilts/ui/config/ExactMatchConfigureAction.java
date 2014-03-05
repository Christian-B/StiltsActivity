package net.sf.taverna.t2.activities.stilts.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import net.sf.taverna.t2.activities.stilts.ExactMatchActivity;
import net.sf.taverna.t2.activities.stilts.ExactMatchBean;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;

@SuppressWarnings("serial")
public class ExactMatchConfigureAction extends
		ActivityConfigurationAction<ExactMatchActivity, ExactMatchBean> {

    public ExactMatchConfigureAction(ExactMatchActivity activity, Frame owner) {
        super(activity);
    }

    protected ExactMatchActivity getActivity() {
        return activity;
    }

    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        ActivityConfigurationDialog<ExactMatchActivity, ExactMatchBean> currentDialog = 
                ActivityConfigurationAction.getDialog(getActivity());
        if (currentDialog != null) {
            currentDialog.toFront();
            return;
        }
        AbstractStiltsConfigurationPanel<ExactMatchActivity, ExactMatchBean> panel = 
                new ExactMatchConfigurationPanel(getActivity());
        
        ActivityConfigurationDialog<ExactMatchActivity, ExactMatchBean> dialog = 
                new ActivityConfigurationDialog<ExactMatchActivity, ExactMatchBean>(
                        getActivity(), panel);

        ActivityConfigurationAction.setDialog(getActivity(), dialog);

    }

}
