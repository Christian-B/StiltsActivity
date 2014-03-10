package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.util.Arrays;
import java.util.List;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ContextualViewFactory;

import net.sf.taverna.t2.activities.stilts.StiltsActivity;

public class StiltsActivityContextViewFactory implements
            ContextualViewFactory<StiltsActivity> {

    public boolean canHandle(Object selection) {
        return selection instanceof StiltsActivity;
    }

    public List<ContextualView> getViews(StiltsActivity activity) {
        return Arrays.<ContextualView>asList(new StiltsContextualView((StiltsActivity)activity));
    }
 	
}
