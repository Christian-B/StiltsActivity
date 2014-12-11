package net.sf.taverna.t2.activities.table.ui.config;

import javax.swing.Action;
import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.actions.TableActivityConfigurationAction;
import net.sf.taverna.t2.workbench.activitytools.AbstractConfigureActivityMenuAction;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

//import net.sf.taverna.t2.activities.beanshell.BeanshellActivity;
//import net.sf.taverna.t2.activities.localworker.LocalworkerActivity;
//import net.sf.taverna.t2.activities.beanshell.actions.BeanshellActivityConfigurationAction;

public class ConfigureTableActivityMenuAction extends
		AbstractConfigureActivityMenuAction<TableActivity> {

	public ConfigureTableActivityMenuAction() {
		super(TableActivity.class);
	}
	
	@Override
	protected Action createAction() {
		Activity a = findActivity();
		Action result = null;
		//if (!(a instanceof LocalworkerActivity)) {
			result = new TableActivityConfigurationAction(
					findActivity(), getParentFrame());
			result.putValue(Action.NAME, "Table tool configuration");
			addMenuDots(result);
		//}
		return result;
	}


}
