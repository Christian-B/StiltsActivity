package net.sf.taverna.t2.activities.stilts.ui.view;

import java.util.Arrays;
import java.util.List;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ContextualViewFactory;

import net.sf.taverna.t2.activities.stilts.StiltsActivity;

/**
 * Single ContextualViewFactory class for all Stilts Activities.
 * <p>
 * There is only one implementation of ContextualViewFactory because no matter which process and preprocess is used 
 * the StiltsConfigurationPanel class is used, with the details delegated to panels.
 * Similarly there is only one bstractAsynchronousActivity (StiltsActivity) which handles the running no matter which process or preprocess is used.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class StiltsActivityContextViewFactory implements
            ContextualViewFactory<StiltsActivity> {

    public boolean canHandle(Object selection) {
        return selection instanceof StiltsActivity;
    }

    public List<ContextualView> getViews(StiltsActivity activity) {
        return Arrays.<ContextualView>asList(new StiltsContextualView((StiltsActivity)activity));
    }
 	
}
