package net.sf.taverna.t2.activities.table.ui.config;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;

import net.sf.taverna.t2.workbench.ui.actions.activity.ActivityConfigurationAction;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;

/**
 * Single ConfigurationAction class for all Stilts Activities.
 * <p>
 * There is only one implementation of ConfigurationAction because no matter which process and preprocess is used 
 * the TableActivityConfigurationPanel class is used, with the details delegated to panels.
 * Similarly there is only one bstractAsynchronousActivity (TableActivity) which handles the running no matter which process or preprocess is used.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TableActivityConfigureAction extends
		ActivityConfigurationAction<TableActivity, TableActivityConfigurationBean> {

    public TableActivityConfigureAction(TableActivity activity, Frame owner) {
        super(activity);
    }

    protected TableActivity getActivity() {
        return activity;
    }

    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        ActivityConfigurationDialog<TableActivity, TableActivityConfigurationBean> currentDialog = 
                ActivityConfigurationAction.getDialog(getActivity());
        
        if (currentDialog != null) {
            currentDialog.toFront();
            return;
        }
        TableActivityConfigurationPanel panel = new TableActivityConfigurationPanel(getActivity());
        
        ActivityConfigurationDialog<TableActivity, TableActivityConfigurationBean> dialog = 
                new ActivityConfigurationDialog<TableActivity, TableActivityConfigurationBean>(
                        getActivity(), panel);

        ActivityConfigurationAction.setDialog(getActivity(), dialog);

    }

}
