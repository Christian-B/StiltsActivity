package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.StiltsInterface;
import net.sf.taverna.t2.activities.stilts.preprocess.StiltsPreProcessBean;
import net.sf.taverna.t2.activities.stilts.process.StiltsProcessBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;
import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import org.apache.log4j.Logger;

/**
 * Single Stilts implementation of the ServiceDescription.
 * <p>
 * Only a single ServiceDescription class is required because the details of the process and preprocess are stored in individual beans.
 * 
 * There is possible because there is only one implementation of Activity (StiltsActivity) which handles all the details no matter which process of preprocess is called.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class StiltsServiceDesc extends ServiceDescription<StiltsBean> 
        implements StiltsInterface{

    private StiltsProcessBean process;
    private StiltsPreProcessBean preprocess;
    private StiltsOutputFormat outputFormatEnum;
    private StiltsOutputType outputTypeEnum;    
    private boolean debugMode;
    private String name;
    private String[] paths;
    
    private static final Logger logger = Logger.getLogger(StiltsServiceDesc.class);
    private static ImageIcon stiltsIcon = null;
    
    public StiltsServiceDesc(StiltsPreProcessBean prepocessBean, StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, 
            StiltsOutputType outputTypeEnum, boolean debugMode, String name, String... paths){
       this.process =  process;
       this.preprocess = prepocessBean;
       this.outputFormatEnum = outputFormatEnum;
       this.outputTypeEnum = outputTypeEnum;
       this.debugMode = debugMode;
       this.name = name;
       this.paths = paths;
    }
    
    public StiltsServiceDesc(StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, 
            StiltsOutputType outputTypeEnum, boolean debugMode, String name, String... path){
        this(null, process, outputFormatEnum, outputTypeEnum, debugMode, name, path);
    }
    
    @Override
    public boolean isTemplateService(){
        logger.info("isTemplateService called");
        return true;
    }
    
    @Override
    public StiltsProcessBean getProcess() {
        return process;
    }

    @Override
    public StiltsPreProcessBean getPreprocess() {
        return preprocess;
    }

    @Override
    public StiltsOutputFormat getOutputFormat() {
        return outputFormatEnum;
    }

    @Override
    public StiltsOutputType getOutputType() {
        return outputTypeEnum;
    }

    @Override
    public boolean isDebugMode() {
       return debugMode;
    }

    @Override
    public Class<? extends Activity<StiltsBean>> getActivityClass() {
        return StiltsActivity.class;
    }

    @Override
    public StiltsBean getActivityConfiguration() {
        return new StiltsBean(this);
    }
    
    public static ImageIcon stiltsIcon(){
        if (stiltsIcon == null){
            String path = "http://www.star.bristol.ac.uk/~mbt/stilts/sun256/ttools2.gif";
            try {
                URL imgURL = new URL(path);
                ImageIcon fullSized = new ImageIcon(imgURL);
                Image image = fullSized.getImage().getScaledInstance(28, 28, java.awt.Image.SCALE_SMOOTH);
                stiltsIcon = new ImageIcon(image);
            } catch (MalformedURLException ex) {
                logger.error ("Error reading " + path, ex);
            }
        }
        return stiltsIcon;
    }

    @Override
    public Icon getIcon() {
        return stiltsIcon();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<? extends Comparable> getPath() {
        List results= new ArrayList<String>();
        results.add("Table processing tools");
        for (String path:paths){
            results.add(path);
        }
        return results;
    }

    @Override
    protected List<? extends Object> getIdentifyingData() {
        return Arrays.<Object>asList(name, process.toString());
    }

}
