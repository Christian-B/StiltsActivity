package net.sf.taverna.t2.activities.stilts.ui.view;

import java.util.Arrays;
import java.util.List;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ContextualViewFactory;

import net.sf.taverna.t2.activities.stilts.AbstractStilsActivity;
import net.sf.taverna.t2.activities.stilts.TCopyActivity;

public class StiltsActivityContextViewFactory implements
		ContextualViewFactory<TCopyActivity> {

	public boolean canHandle(Object selection) {
		return selection instanceof AbstractStilsActivity;
	}

	public List<ContextualView> getViews(TCopyActivity selection) {
		return Arrays.<ContextualView>asList(new TCopyContextualView(selection));
	}

 	
}
