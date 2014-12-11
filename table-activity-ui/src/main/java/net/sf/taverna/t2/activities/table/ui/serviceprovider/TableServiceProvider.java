package net.sf.taverna.t2.activities.table.ui.serviceprovider;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import net.sf.taverna.t2.activities.table.input.*;
import net.sf.taverna.t2.activities.table.preprocess.*;
import net.sf.taverna.t2.activities.table.process.*;
import net.sf.taverna.t2.activities.table.utils.*;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.servicedescriptions.ServiceDescriptionProvider;

public class TableServiceProvider implements ServiceDescriptionProvider {
	
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

        List<TableInputType> typesOfInputsEnums = new ArrayList<TableInputType>();
        typesOfInputsEnums.add(TableInputType.STRING);
        typesOfInputsEnums.add(TableInputType.STRING);
        List<TableInputFormat> formatsOfInputsEnums = new ArrayList<TableInputFormat>();
        formatsOfInputsEnums.add(TableInputFormat.CSV);
        formatsOfInputsEnums.add(TableInputFormat.CSV);

        List<ServiceDescription> results = new ArrayList<ServiceDescription>();

        SingleInputBean singleInputBean = new SingleInputBean(TableInputFormat.CSV, TableInputType.STRING);
        TPipeBean tPipeBean = new TPipeBean(singleInputBean);
        TableServiceDesc tPipeDescription = new TableServiceDesc(tPipeBean, TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "reformat", SINGLE_FILE_PATH);
 	// Optional: set description
	tPipeDescription.setDescription("Copy the table to a new format, without chaning any data");
	results.add(tPipeDescription);

        MultipleFormatsBean flexibleInputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        TCatNBean tCatNBean = new TCatNBean(flexibleInputBean);
        TableServiceDesc tCatNDescription = new TableServiceDesc(tCatNBean, TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Concatenate multiple formats", CONCAT_PATH);
	// Optional: set description
	tCatNDescription.setDescription("Concatenate various files which do not have to be the same format.");
	results.add(tCatNDescription);

        SingleFormatMultipleInputsBean singleFormatMultipleInputsBean = 
                new SingleFormatMultipleInputsBean(typesOfInputsEnums, TableInputFormat.CSV);
        TCatBean tCatBean = new TCatBean(singleFormatMultipleInputsBean);
        TableServiceDesc tCatDescription = new TableServiceDesc(tCatBean, TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Concatenate similar Files", CONCAT_PATH);
	tCatDescription.setDescription("Concatenate various files which must have to be the same format.");
        results.add(tCatDescription);
        
        TJoinBean tJoinBean = new TJoinBean(flexibleInputBean);
        TableServiceDesc tJoinDescription = new TableServiceDesc(tJoinBean, TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Join based on row number", JOIN_PATH);
        tJoinDescription.setDescription("Join Tables side by side without considering the values.");
        results.add(tJoinDescription);
        
        TwoInputsBean twoInputBean = new TwoInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        ExactMatchBean exactMatchBean = new ExactMatchBean(1, TableFind.ALL, TableFixcols.DUPS, TableJoin.ONE_AND_TWO, twoInputBean);
        TableServiceDesc exactMatchDescription = new TableServiceDesc(exactMatchBean, TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Join based on macthing values", JOIN_PATH);
        exactMatchDescription.setDescription("Traditional join taking into consideration values of join columns.");
        results.add(exactMatchDescription);

        UserSpecifiedPreProcessorBean userSpecifiedPreProcessorBean = new UserSpecifiedPreProcessorBean("delcols 1");
        TableServiceDesc userSpecifiedPreProcessorDescription =
                new TableServiceDesc(userSpecifiedPreProcessorBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Generic convertor", SINGLE_FILE_PATH);
        userSpecifiedPreProcessorDescription.setDescription("Advanced prepocessing tool based on Stilts comannd format.");
 	results.add(userSpecifiedPreProcessorDescription);

        DeleteColumnPreProcessorBean deleteColumnPreProcessorBean = new DeleteColumnPreProcessorBean("$1");
        TableServiceDesc deleteColumnPreProcessorDescription =
                new TableServiceDesc(deleteColumnPreProcessorBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Delete column(s)", SINGLE_FILE_PATH);
        deleteColumnPreProcessorDescription.setDescription("Delete one or more column based on name or index");
 	results.add(deleteColumnPreProcessorDescription);

        KeepColumnPreProcessorBean keepColumnPreProcessorBean = new KeepColumnPreProcessorBean("$1");
        TableServiceDesc keepColumnPreProcessorDescription =
                new TableServiceDesc(keepColumnPreProcessorBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Keep column(s)",SINGLE_FILE_PATH);
        keepColumnPreProcessorDescription.setDescription("Delete all but the named/indexed columns");
 	results.add(keepColumnPreProcessorDescription);

        AddColumnPreProcessorBean  addColumnByCommandBean = 
                new AddColumnPreProcessorBean("$1", "newCol", TableLocationType.END, null);
        TableServiceDesc addColumnByCommandDescription =
                new TableServiceDesc(addColumnByCommandBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Add column",SINGLE_FILE_PATH);
        addColumnByCommandDescription.setDescription("Add a new column based on an equation over other columns");
 	results.add(addColumnByCommandDescription);

        SelectByCommandPreProcessorBean selectByCommandBean = new SelectByCommandPreProcessorBean("true");
        TableServiceDesc selectByCommandDescription =
                new TableServiceDesc(selectByCommandBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Select rows",SINGLE_FILE_PATH);
        selectByCommandDescription.setDescription("Select rows based on an equation over that rows columns");
 	results.add(selectByCommandDescription);

        HeadRowsPreProcessorBean headRowsPreProcessorBean = new HeadRowsPreProcessorBean(1);
        TableServiceDesc headRowsPreProcessorDescription =
                new TableServiceDesc(headRowsPreProcessorBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Keep first X rows(s)",SINGLE_FILE_PATH);
        headRowsPreProcessorDescription.setDescription("Delete all but the header and first X rows.");
 	results.add(headRowsPreProcessorDescription);

        TailRowsPreProcessorBean tailRowsPreProcessorBean = new TailRowsPreProcessorBean(1);
        TableServiceDesc tailRowsPreProcessorDescription =
                new TableServiceDesc(tailRowsPreProcessorBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Keeap last X rows(s)",SINGLE_FILE_PATH);
        tailRowsPreProcessorDescription.setDescription("Delete all but the header and last X rows.");
 	results.add(tailRowsPreProcessorDescription);

        SortPreProcessorBean sortPreProcessorBean = new SortPreProcessorBean("$1", false, false);
        TableServiceDesc sortPreProcessorDescription =
                new TableServiceDesc(sortPreProcessorBean, tPipeBean, 
                TableOutputFormat.CSV, TableOutputType.STRING, false, 
                "Sort rows", SINGLE_FILE_PATH);
        sortPreProcessorDescription.setDescription("Sort the data based on the value of the defined column.");
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
