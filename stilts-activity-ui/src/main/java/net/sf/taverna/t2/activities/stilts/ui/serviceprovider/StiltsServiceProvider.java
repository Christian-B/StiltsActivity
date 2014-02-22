package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.servicedescriptions.ServiceDescriptionProvider;

public class StiltsServiceProvider implements ServiceDescriptionProvider {
	
    /**
      * Do the actual search for services. Return using the callBack parameter.
      */
    @SuppressWarnings("unchecked")
    public void findServiceDescriptionsAsync(FindServiceDescriptionsCallBack callBack) {
        // Use callback.status() for long-running searches
        // callBack.status("Resolving example services");

        List<ServiceDescription> results = new ArrayList<ServiceDescription>();

        SingleInputServiceDesc service = new SingleInputServiceDesc();
        // Populate the service description bean
        service.setFormatOfInput("csv");
	service.setFormatOfOutput("tst");
        service.setTypeOfInput(StiltsConfigurationConstants.FILE_PATH_TYPE);
	service.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
        service.setDebugMode(true);

	// Optional: set description
	service.setDescription("Stilt example ");
	results.add(service);

        // partialResults() can also be called several times from inside
        // for-loop if the full search takes a long time
        callBack.partialResults(results);

        // No more results will be coming
        callBack.finished();
    }

    /**
      * Icon for service provider
      */
    public Icon getIcon() {
        return null;
    }

    /**
      * Name of service provider, appears in right click for 'Remove service provider'
     */
    public String getName() {
        return "MGrid based on AstroTaverna";
    }
	
    @Override
    public String toString() {
        return getName();
    }

}
