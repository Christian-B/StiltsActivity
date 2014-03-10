package net.sf.taverna.t2.activities.stilts.ui.config.test;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.test.StiltsProcessBean;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

import net.sf.taverna.t2.activities.stilts.test.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.test.StiltsBean;
import net.sf.taverna.t2.activities.stilts.test.StiltsInterface;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

public class StiltsServiceDesc extends ServiceDescription<StiltsBean> 
        implements StiltsInterface{

    private StiltsProcessBean process;
    private StiltsOutputFormat outputFormatEnum;
    private StiltsOutputType outputTypeEnum;    
    private boolean debugMode;
    private String name;
    
    public StiltsServiceDesc(StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, 
            StiltsOutputType outputTypeEnum, boolean debugMode, String name){
       this.process =  process;
       this.outputFormatEnum = outputFormatEnum;
       this.outputTypeEnum = outputTypeEnum;
       this.debugMode = debugMode;
       this.name = name;
    }
    
    @Override
    public StiltsProcessBean getProcess() {
        return process;
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

    @Override
    public Icon getIcon() {
       // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<? extends Comparable> getPath() {
        return Arrays.asList("Stilts Tools");
    }

    @Override
    protected List<? extends Object> getIdentifyingData() {
        return getPath();
    }

}
