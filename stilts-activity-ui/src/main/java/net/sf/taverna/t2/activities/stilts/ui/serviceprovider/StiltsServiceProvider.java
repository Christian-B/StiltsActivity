package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import net.sf.taverna.t2.activities.stilts.process.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;
import net.sf.taverna.t2.activities.stilts.operator.StiltsOneVariableOperator;
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
        StiltsServiceDesc tPipeDescription = new StiltsServiceDesc(tPipeBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Copy to new Format");
 	// Optional: set description
	//tPipeDescription.setDescription("Copy to new Format");
	results.add(tPipeDescription);

        MultipleFormatsBean flexibleInputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        TCatNBean tCatNBean = new TCatNBean(flexibleInputBean);
        StiltsServiceDesc tCatNDescription = new StiltsServiceDesc(tCatNBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Concatenate various Files diffferent formats");
	// Optional: set description
	//tCatNDescription.setDescription("Concatenate various Files");
	results.add(tCatNDescription);

        SingleFormatMultipleInputsBean singleFormatMultipleInputsBean = 
                new SingleFormatMultipleInputsBean(typesOfInputsEnums, StiltsInputFormat.CSV);
        TCatBean tCatBean = new TCatBean(singleFormatMultipleInputsBean);
        StiltsServiceDesc tCatDescription = new StiltsServiceDesc(tCatBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Concatenate various Files same format");
        results.add(tCatDescription);
        
        TJoinBean tJoinBean = new TJoinBean(flexibleInputBean);
        StiltsServiceDesc tJoinDescription = new StiltsServiceDesc(tJoinBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Join various files different formats");
        results.add(tJoinDescription);
        
        TwoInputsBean twoInputBean = new TwoInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        ExactMatchBean exactMatchBean = new ExactMatchBean(1, StiltsFind.ALL, StiltsFixcols.DUPS, StiltsJoin.ONE_AND_TWO, twoInputBean);
        StiltsServiceDesc exactMatchDescription = new StiltsServiceDesc(exactMatchBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Exact Macth two or more files");
        results.add(exactMatchDescription);

        UserSpecifiedPreProcessorBean userSpecifiedPreProcessorBean = new UserSpecifiedPreProcessorBean("delcols 1");
        StiltsServiceDesc userSpecifiedPreProcessorDescription =
                new StiltsServiceDesc(userSpecifiedPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Generic PreProcessor");
 	results.add(userSpecifiedPreProcessorDescription);

        DeleteColumnPreProcessorBean deleteColumnPreProcessorBean = new DeleteColumnPreProcessorBean("1");
        StiltsServiceDesc deleteColumnPreProcessorDescription =
                new StiltsServiceDesc(deleteColumnPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Delete Column(s)");
 	results.add(deleteColumnPreProcessorDescription);

        KeepColumnPreProcessorBean keepColumnPreProcessorBean = new KeepColumnPreProcessorBean("1");
        StiltsServiceDesc keepColumnPreProcessorDescription =
                new StiltsServiceDesc(keepColumnPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Keep Column(s)");
 	results.add(keepColumnPreProcessorDescription);

        AddColumnByCommandPreProcessorBean  addColumnByCommandBean = 
                new AddColumnByCommandPreProcessorBean("$1 + $3", "newCol", StiltsLocationType.AFTER, "$2");
        StiltsServiceDesc addColumnByCommandDescription =
                new StiltsServiceDesc(addColumnByCommandBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Add Column Fully Configurable");
 	results.add(addColumnByCommandDescription);

        AddColumnOneVariablesPreProcessorBean  addColumnOneVariableBean = 
                new AddColumnOneVariablesPreProcessorBean(StiltsOneVariableOperator.ABS, "$3", "Absolute");
        StiltsServiceDesc addColumnOneVariableDescription =
                new StiltsServiceDesc(addColumnOneVariableBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Add Column One Variable Operator");
 	results.add(addColumnOneVariableDescription);

        AddColumnTwoVariablesPreProcessorBean  addColumnTwoVariableBean = 
                new AddColumnTwoVariablesPreProcessorBean(StiltsTwoVariableOperator.AND, "$1", "$3", "sum");
        StiltsServiceDesc addColumnTwoVariableDescription =
                new StiltsServiceDesc(addColumnTwoVariableBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Add Column Two Variables Operator");
 	results.add(addColumnTwoVariableDescription);
        
        SelectByCommandPreProcessorBean selectByCommandBean = new SelectByCommandPreProcessorBean("$1 <= 1");
        StiltsServiceDesc selectByCommandDescription =
                new StiltsServiceDesc(selectByCommandBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Select Rows Fully Configurable");
 	results.add(selectByCommandDescription);

        SelectTwoVariablesPreProcessorBean  selectTwoVariableBean = 
                new SelectTwoVariablesPreProcessorBean(StiltsTwoVariableOperator.GREATHER_THAN_EQUALS, "$1", "$3");
        StiltsServiceDesc selectTwoVariableDescription =
                new StiltsServiceDesc(selectTwoVariableBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Select Rows Two Variables Operator");
 	results.add(selectTwoVariableDescription);
        
        HeadRowsPreProcessorBean headRowsPreProcessorBean = new HeadRowsPreProcessorBean(10);
        StiltsServiceDesc headRowsPreProcessorDescription =
                new StiltsServiceDesc(headRowsPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Head rows(s)");
 	results.add(headRowsPreProcessorDescription);

        TailRowsPreProcessorBean tailRowsPreProcessorBean = new TailRowsPreProcessorBean(10);
        StiltsServiceDesc tailRowsPreProcessorDescription =
                new StiltsServiceDesc(tailRowsPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Tail rows(s)");
 	results.add(tailRowsPreProcessorDescription);

        SortPreProcessorBean sortPreProcessorBean = new SortPreProcessorBean("1", false, false);
        StiltsServiceDesc sortPreProcessorDescription =
                new StiltsServiceDesc(sortPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Sort rows");
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

}
