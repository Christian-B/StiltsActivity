package net.sf.taverna.t2.activities.stilts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.taverna.t2.reference.T2Reference;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivity;
import net.sf.taverna.t2.workflowmodel.processor.activity.AsynchronousActivityCallback;

public class TCatNActivity extends InputsTypeActivity<InputsTypeBean>
		implements AsynchronousActivity<InputsTypeBean>,  Activity<InputsTypeBean>{

    protected List<String> prepareParameters(final Map<String, T2Reference> inputs, final AsynchronousActivityCallback callback, File outputFile) {
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add("tcatn");
        parameters.add("nin=" + getConfiguration().getNumberOfInputs());
        parameters.addAll(super.prepareParameters(inputs, callback, outputFile));
        return parameters;
    }

}
