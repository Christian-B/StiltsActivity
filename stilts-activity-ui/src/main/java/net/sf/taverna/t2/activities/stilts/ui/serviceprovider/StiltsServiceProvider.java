package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.input.*;
import net.sf.taverna.t2.activities.stilts.preprocess.*;
import net.sf.taverna.t2.activities.stilts.process.*;
import net.sf.taverna.t2.activities.stilts.utils.*;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.servicedescriptions.ServiceDescriptionProvider;

public class StiltsServiceProvider implements ServiceDescriptionProvider {
	
    private final String SINGLE_FILE_PATH = "Change single file";
    private final String CONCAT_PATH = "Concatenate";
    private final String JOIN_PATH = "Join/merge";
    
    /**
      * Do the actual search for services. Return using the callBack parameter.
 * @author Christian Brenninkmeijer
 * @version 1.0
      */
    @SuppressWarnings("unchecked")
    public void findServiceDescriptionsAsync(FindServiceDescriptionsCallBack callBack) {
        // Use callback.status() for long-running searches
        // callBack.status("Resolving example services");

        List<StiltsInputType> typesOfInputsEnums = new ArrayList<StiltsInputType>();
        typesOfInputsEnums.add(StiltsInputType.FILE);
        typesOfInputsEnums.add(StiltsInputType.STRING);
        List<StiltsInputFormat> formatsOfInputsEnums = new ArrayList<StiltsInputFormat>();
        formatsOfInputsEnums.add(StiltsInputFormat.TST);
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);

        List<ServiceDescription> results = new ArrayList<ServiceDescription>();

        SingleInputBean singleInputBean = new SingleInputBean(StiltsInputFormat.TST, StiltsInputType.FILE);
        TPipeBean tPipeBean = new TPipeBean(singleInputBean);
        StiltsServiceDesc tPipeDescription = new StiltsServiceDesc(tPipeBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "reformat", SINGLE_FILE_PATH);
 	// Optional: set description
	tPipeDescription.setDescription("Copy the table to a new format, without chaning any data");
	results.add(tPipeDescription);

        MultipleFormatsBean flexibleInputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        TCatNBean tCatNBean = new TCatNBean(flexibleInputBean);
        StiltsServiceDesc tCatNDescription = new StiltsServiceDesc(tCatNBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Concatenate multiple formats", CONCAT_PATH);
	// Optional: set description
	tCatNDescription.setDescription("Concatenate various files which do not have to be the same format.");
	results.add(tCatNDescription);

        SingleFormatMultipleInputsBean singleFormatMultipleInputsBean = 
                new SingleFormatMultipleInputsBean(typesOfInputsEnums, StiltsInputFormat.CSV);
        TCatBean tCatBean = new TCatBean(singleFormatMultipleInputsBean);
        StiltsServiceDesc tCatDescription = new StiltsServiceDesc(tCatBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Concatenate similar Files", CONCAT_PATH);
	tCatDescription.setDescription("Concatenate various files which must have to be the same format.");
        results.add(tCatDescription);
        
        TJoinBean tJoinBean = new TJoinBean(flexibleInputBean);
        StiltsServiceDesc tJoinDescription = new StiltsServiceDesc(tJoinBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Join based on row number", JOIN_PATH);
        results.add(tJoinDescription);
        
        TwoInputsBean twoInputBean = new TwoInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        ExactMatchBean exactMatchBean = new ExactMatchBean(1, StiltsFind.ALL, StiltsFixcols.DUPS, StiltsJoin.ONE_AND_TWO, twoInputBean);
        StiltsServiceDesc exactMatchDescription = new StiltsServiceDesc(exactMatchBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Join based on macthing values", JOIN_PATH);
        results.add(exactMatchDescription);

        UserSpecifiedPreProcessorBean userSpecifiedPreProcessorBean = new UserSpecifiedPreProcessorBean("delcols 1");
        StiltsServiceDesc userSpecifiedPreProcessorDescription =
                new StiltsServiceDesc(userSpecifiedPreProcessorBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Generic convertor", SINGLE_FILE_PATH);
 	results.add(userSpecifiedPreProcessorDescription);

        DeleteColumnPreProcessorBean deleteColumnPreProcessorBean = new DeleteColumnPreProcessorBean("1");
        StiltsServiceDesc deleteColumnPreProcessorDescription =
                new StiltsServiceDesc(deleteColumnPreProcessorBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Delete column(s)", SINGLE_FILE_PATH);
 	results.add(deleteColumnPreProcessorDescription);

        KeepColumnPreProcessorBean keepColumnPreProcessorBean = new KeepColumnPreProcessorBean("1");
        StiltsServiceDesc keepColumnPreProcessorDescription =
                new StiltsServiceDesc(keepColumnPreProcessorBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Keep column(s)",SINGLE_FILE_PATH);
 	results.add(keepColumnPreProcessorDescription);

        AddColumnPreProcessorBean  addColumnByCommandBean = 
                new AddColumnPreProcessorBean("$1 + $3", "newCol", StiltsLocationType.AFTER, "$2");
        StiltsServiceDesc addColumnByCommandDescription =
                new StiltsServiceDesc(addColumnByCommandBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Add column",SINGLE_FILE_PATH);
 	results.add(addColumnByCommandDescription);

        SelectByCommandPreProcessorBean selectByCommandBean = new SelectByCommandPreProcessorBean("$1 <= 1");
        StiltsServiceDesc selectByCommandDescription =
                new StiltsServiceDesc(selectByCommandBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Select rows",SINGLE_FILE_PATH);
 	results.add(selectByCommandDescription);

        HeadRowsPreProcessorBean headRowsPreProcessorBean = new HeadRowsPreProcessorBean(10);
        StiltsServiceDesc headRowsPreProcessorDescription =
                new StiltsServiceDesc(headRowsPreProcessorBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Keep first X rows(s)",SINGLE_FILE_PATH);
 	results.add(headRowsPreProcessorDescription);

        TailRowsPreProcessorBean tailRowsPreProcessorBean = new TailRowsPreProcessorBean(10);
        StiltsServiceDesc tailRowsPreProcessorDescription =
                new StiltsServiceDesc(tailRowsPreProcessorBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Keeap last X rows(s)",SINGLE_FILE_PATH);
 	results.add(tailRowsPreProcessorDescription);

        SortPreProcessorBean sortPreProcessorBean = new SortPreProcessorBean("1", false, false);
        StiltsServiceDesc sortPreProcessorDescription =
                new StiltsServiceDesc(sortPreProcessorBean, tPipeBean, 
                StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, 
                "Sort rows", SINGLE_FILE_PATH);
 	results.add(sortPreProcessorDescription);

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

    public String getId() {
        return "Stilts based Table tools";
    }

}
