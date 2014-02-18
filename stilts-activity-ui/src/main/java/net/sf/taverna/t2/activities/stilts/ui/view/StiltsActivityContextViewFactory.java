package net.sf.taverna.t2.activities.stilts.ui.view;

import java.util.Arrays;
import java.util.List;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ContextualViewFactory;

import net.sf.taverna.t2.activities.stilts.OutputTypeActivity;
import net.sf.taverna.t2.activities.stilts.TcopyActivity;

public class StiltsActivityContextViewFactory implements
		ContextualViewFactory<TcopyActivity> {

	public boolean canHandle(Object selection) {
		return selection instanceof OutputTypeActivity;
	}

	public List<ContextualView> getViews(TcopyActivity selection) {
		return Arrays.<ContextualView>asList(new TCopyContextualView(selection));
	}

 	
}
