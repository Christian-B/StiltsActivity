package net.sf.taverna.t2.activities.table.ui.serviceprovider;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sf.taverna.t2.activities.table.TableActivity;
import net.sf.taverna.t2.activities.table.TableActivityConfigurationBean;
import net.sf.taverna.t2.activities.table.AbstractTableActivityConfiguration;
import net.sf.taverna.t2.activities.table.preprocess.AbstractPreProcessBean;
import net.sf.taverna.t2.activities.table.process.AbstractProcessBean;
import net.sf.taverna.t2.activities.table.utils.TableOutputFormat;
import net.sf.taverna.t2.activities.table.utils.TableOutputType;
import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import org.apache.log4j.Logger;

/**
 * Single Stilts implementation of the ServiceDescription.
 * <p>
 * Only a single ServiceDescription class is required because the details of the process and preprocess are stored in individual beans.
 * 
 * There is possible because there is only one implementation of Activity (TableActivity) which handles all the details no matter which process of preprocess is called.
 * @author Christian Brenninkmeijer
 * @version 1.0
 */
public class TableServiceDesc extends ServiceDescription<TableActivityConfigurationBean> 
        implements AbstractTableActivityConfiguration{

    private AbstractProcessBean process;
    private AbstractPreProcessBean preprocess;
    private TableOutputFormat outputFormatEnum;
    private TableOutputType outputTypeEnum;    
    private boolean debugMode;
    private String name;
    private String[] paths;
    
    private static final Logger logger = Logger.getLogger(TableServiceDesc.class);
    private static ImageIcon stiltsIcon = null;
    
    public TableServiceDesc(AbstractPreProcessBean prepocessBean, AbstractProcessBean process, TableOutputFormat outputFormatEnum, 
            TableOutputType outputTypeEnum, boolean debugMode, String name, String... paths){
       this.process =  process;
       this.preprocess = prepocessBean;
       this.outputFormatEnum = outputFormatEnum;
       this.outputTypeEnum = outputTypeEnum;
       this.debugMode = debugMode;
       this.name = name;
       this.paths = paths;
    }
    
    public TableServiceDesc(AbstractProcessBean process, TableOutputFormat outputFormatEnum, 
            TableOutputType outputTypeEnum, boolean debugMode, String name, String... path){
        this(null, process, outputFormatEnum, outputTypeEnum, debugMode, name, path);
    }
    
    @Override
    public boolean isTemplateService(){
        logger.info("isTemplateService called");
        return true;
    }
    
    public AbstractProcessBean getProcess() {
        return process;
    }

    public AbstractPreProcessBean getPreprocess() {
        return preprocess;
    }

    public TableOutputFormat getOutputFormat() {
        return outputFormatEnum;
    }

    public TableOutputType getOutputType() {
        return outputTypeEnum;
    }

    public boolean isDebugMode() {
       return debugMode;
    }

    @Override
    public Class<? extends Activity<TableActivityConfigurationBean>> getActivityClass() {
        return TableActivity.class;
    }

    @Override
    public TableActivityConfigurationBean getActivityConfiguration() {
        return new TableActivityConfigurationBean(this);
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
