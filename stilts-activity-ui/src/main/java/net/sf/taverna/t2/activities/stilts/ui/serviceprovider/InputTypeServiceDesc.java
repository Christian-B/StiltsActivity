package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

import net.sf.taverna.t2.activities.stilts.AbstractStilsActivity;
import net.sf.taverna.t2.activities.stilts.InputTypeBean;
import net.sf.taverna.t2.activities.stilts.InputTypeInterface;
import net.sf.taverna.t2.activities.stilts.TCopyActivity;

public class InputTypeServiceDesc extends ServiceDescription<InputTypeBean> 
        implements InputTypeInterface{

    private String formatOfInput;
    private String formatOfOutput;
    private String typeOfInput;
    private String typeOfOutput;
    private boolean debugMode;
    
    /**
      * The subclass of Activity which should be instantiated when adding a service
      * for this description 
      */
    @Override
    public Class<? extends Activity<InputTypeBean>> getActivityClass() {
        return TCopyActivity.class;
    }

    /**
      * The configuration bean which is to be used for configuring the instantiated activity. 
      * Making this bean will typically require some of the fields set on this service
      * description, like an endpoint URL or method name. 
      * 
      */
    @Override
    public InputTypeBean getActivityConfiguration() {
        InputTypeBean bean = new InputTypeBean();
        bean.setFormatOfInput(getFormatOfInput());
        bean.setFormatOfOutput(getFormatOfOutput());
        bean.setTypeOfInput(getTypeOfInput());
        bean.setTypeOfOutput(getTypeOfOutput());
        return bean;
    }

    /**
      * An icon to represent this service description in the service palette.
      */
    @Override
    public Icon getIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
      * The display name that will be shown in service palette and will
      * be used as a template for processor name when added to workflow.
      */
    @Override
    public String getName() {
        return "Stilts Tools Name";
    }

    /**
      * The path to this service description in the service palette. Folder * will be created for each element of the returned path.
      */
    @Override
    public List<String> getPath() {
        // For deeper paths you may return several strings
        return Arrays.asList("Stilts Tools Path");
    }

    /**
      * Return a list of data values uniquely identifying this service
      * description (to avoid duplicates). Include only primary key like fields,
      * ie. ignore descriptions, icons, etc.
      */
    @Override
    protected List<? extends Object> getIdentifyingData() {
        // FIXME: Use your fields instead of example fields
        return getPath();
    }

     /**
     * @return the formatOfInput
     */
    public String getFormatOfInput() {
        return formatOfInput;
    }

    /**
     * @param formatOfInput the formatOfInput to set
     */
    public void setFormatOfInput(String formatOfInput) {
        this.formatOfInput = formatOfInput;
    }

    /**
     * @return the formatOfOutput
     */
    public String getFormatOfOutput() {
        return formatOfOutput;
    }

    /**
     * @param formatOfOutput the formatOfOutput to set
     */
    public void setFormatOfOutput(String formatOfOutput) {
        this.formatOfOutput = formatOfOutput;
    }

    /**
     * @return the typeOfInput
     */
    public String getTypeOfInput() {
        return typeOfInput;
    }

    /**
     * @param typeOfInput the typeOfInput to set
     */
    public void setTypeOfInput(String typeOfInput) {
        this.typeOfInput = typeOfInput;
    }

    /**
     * @return the typeOfOutput
     */
    public String getTypeOfOutput() {
        return typeOfOutput;
    }

    /**
     * @param typeOfOutput the typeOfOutput to set
     */
    public void setTypeOfOutput(String typeOfOutput) {
        this.typeOfOutput = typeOfOutput;
    }

    /**
     * @return the debugMode
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * @param debugMode the debugMode to set
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

	

}
