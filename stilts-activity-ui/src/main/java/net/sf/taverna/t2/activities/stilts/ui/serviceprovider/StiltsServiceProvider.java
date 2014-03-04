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

        ArrayList<String> allFilePath = new ArrayList<String>();
        allFilePath.add(StiltsConfigurationConstants.FILE_PATH_TYPE);
        allFilePath.add(StiltsConfigurationConstants.FILE_PATH_TYPE);

        ArrayList<String> AllCsv = new ArrayList<String>();
        AllCsv.add("csv");
        AllCsv.add("csv");

        List<ServiceDescription> results = new ArrayList<ServiceDescription>();

        TCopyServiceDesc tcopyService = new TCopyServiceDesc();
        // Populate the service description bean
        tcopyService.setFormatOfInput("csv");
	tcopyService.setFormatOfOutput("tst");
        tcopyService.setTypeOfInput(StiltsConfigurationConstants.FILE_PATH_TYPE);
	tcopyService.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
        tcopyService.setDebugMode(true);
	// Optional: set description
	tcopyService.setDescription("TCopy");
	results.add(tcopyService);

        TCatServiceDesc tcatService = new TCatServiceDesc();
        // Populate the service description bean
        tcatService.setFormatOfInputs("csv");
        tcatService.setFormatOfOutput("tst");
        tcatService.setNumberOfInputs(2);
        tcatService.setTypesOfInputs(allFilePath);
	tcatService.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
        tcatService.setDebugMode(true);
        tcatService.setFixedNumberOfInputs(false);
	// Optional: set description
	tcatService.setDescription("TCat");
	results.add(tcatService);

        TCatNServiceDesc tcatnService = new TCatNServiceDesc();
        // Populate the service description bean
        tcatnService.setFormatsOfInputs(AllCsv);
        tcatnService.setFormatOfOutput("tst");
        tcatnService.setNumberOfInputs(2);
        tcatnService.setTypesOfInputs(allFilePath);
	tcatnService.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
        tcatnService.setDebugMode(true);
        tcatnService.setFixedNumberOfInputs(false);
	// Optional: set description
	tcatnService.setDescription("TCatN");
	results.add(tcatnService);

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
