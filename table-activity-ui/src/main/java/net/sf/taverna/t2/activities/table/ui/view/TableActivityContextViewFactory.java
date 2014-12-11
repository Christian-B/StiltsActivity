package net.sf.taverna.t2.activities.table.ui.view;

import java.util.Arrays;
import java.util.List;

import net.sf.taverna.t2.workbench.ui.views.contextualviews.ContextualView;
import net.sf.taverna.t2.workbench.ui.views.contextualviews.activity.ContextualViewFactory;

import net.sf.taverna.t2.activities.table.TableActivity;

/**
 * Single ContextualViewFactory class for all Stilts Activities.
 * <p>
 * There is only one implementation of ContextualViewFactory because no matter which process and preprocess is used 
 * the TableActivityConfigurationPanel class is used, with the details delegated to panels.
 * Similarly there is only one bstractAsynchronousActivity (TableActivity) which handles the running no matter which process or preprocess is used.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class TableActivityContextViewFactory implements
            ContextualViewFactory<TableActivity> {

    public boolean canHandle(Object selection) {
        return selection instanceof TableActivity;
    }

    public List<ContextualView> getViews(TableActivity activity) {
        return Arrays.<ContextualView>asList(new TableActivityContextualView((TableActivity)activity));
    }
 	
}
