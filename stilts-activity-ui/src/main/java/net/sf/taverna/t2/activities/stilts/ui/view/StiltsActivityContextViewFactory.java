package net.sf.taverna.t2.activities.stilts.ui.view;

import java.util.Arrays;
import java.util.List;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ContextualViewFactory;

import net.sf.taverna.t2.activities.stilts.AbstractStilsActivity;
import net.sf.taverna.t2.activities.stilts.ExactMatchActivity;
import net.sf.taverna.t2.activities.stilts.TCatActivity;
import net.sf.taverna.t2.activities.stilts.TCatNActivity;
import net.sf.taverna.t2.activities.stilts.TCopyActivity;

public class StiltsActivityContextViewFactory implements
            ContextualViewFactory<AbstractStilsActivity> {

    public boolean canHandle(Object selection) {
        return selection instanceof AbstractStilsActivity;
    }

    public List<ContextualView> getViews(AbstractStilsActivity activity) {
        if (activity instanceof TCatActivity){
            return Arrays.<ContextualView>asList(new TCatContextualView((TCatActivity)activity));
        }
        if (activity instanceof TCatNActivity){
            return Arrays.<ContextualView>asList(new TCatNContextualView((TCatNActivity)activity));
        }
        if (activity instanceof TCopyActivity){
            return Arrays.<ContextualView>asList(new TCopyContextualView((TCopyActivity)activity));
        }
        if (activity instanceof ExactMatchActivity){
            return Arrays.<ContextualView>asList(new ExactMatchContextualView((ExactMatchActivity)activity));
        }
        return null;
    }
 	
}
