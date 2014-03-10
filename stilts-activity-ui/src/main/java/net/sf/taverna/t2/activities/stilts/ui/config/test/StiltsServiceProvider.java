package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.input.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.input.FlexibleInputsBean;
import net.sf.taverna.t2.activities.stilts.input.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.process.TCatBean;
import net.sf.taverna.t2.activities.stilts.process.TCatNBean;
import net.sf.taverna.t2.activities.stilts.process.TJoinBean;
import net.sf.taverna.t2.activities.stilts.process.TPipeBean;
import net.sf.taverna.t2.activities.stilts.input.TwoInputsBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

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
