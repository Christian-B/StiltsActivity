package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.OutputTypeBean;
import net.sf.taverna.t2.activities.stilts.OutputTypeInterface;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;

public abstract class OutputTypeServiceDesc<OutputType extends OutputTypeBean> 
        extends ServiceDescription<OutputTypeBean> 
        implements OutputTypeInterface  {

    private String formatOfOutput;
    private String typeOfOutput;
    private boolean debugMode;
    
    /**
      * The configuration bean which is to be used for configuring the instantiated activity. 
      * Making this bean will typically require some of the fields set on this service
      * description, like an endpoint URL or method name. 
      * 
      */ 
    public void setActivityConfiguration() {
        OutputTypeBean bean;
        bean = new OutputTypeBean();
        bean.setFormatOfOutput(getFormatOfOutput());
        bean.setTypeOfOutput(getTypeOfOutput());
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
