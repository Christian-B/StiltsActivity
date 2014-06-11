package net.sf.taverna.t2.activities.stilts.ui.config;

import javax.swing.Action;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.actions.StiltsActivityConfigurationAction;
import net.sf.taverna.t2.workbench.activitytools.AbstractConfigureActivityMenuAction;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

//import net.sf.taverna.t2.activities.beanshell.BeanshellActivity;
//import net.sf.taverna.t2.activities.localworker.LocalworkerActivity;
//import net.sf.taverna.t2.activities.beanshell.actions.BeanshellActivityConfigurationAction;

public class ConfigureStiltslMenuAction extends
		AbstractConfigureActivityMenuAction<StiltsActivity> {

	public ConfigureStiltslMenuAction() {
		super(StiltsActivity.class);
	}
	
	@Override
	protected Action createAction() {
		Activity a = findActivity();
		Action result = null;
		//if (!(a instanceof LocalworkerActivity)) {
			result = new StiltsActivityConfigurationAction(
					findActivity(), getParentFrame());
			result.putValue(Action.NAME, "Table tool configuration");
			addMenuDots(result);
		//}
		return result;
	}


}
