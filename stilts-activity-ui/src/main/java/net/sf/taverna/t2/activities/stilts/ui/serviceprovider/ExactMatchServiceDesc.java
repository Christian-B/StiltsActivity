package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.ExactMatchActivity;
import net.sf.taverna.t2.activities.stilts.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.ExactMatchInterface;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;


public class ExactMatchServiceDesc extends ServiceDescription<ExactMatchBean> 
        implements ExactMatchInterface{

    private String formatOfOutput;
    private List<String> typesOfInputs;
    private String typeOfOutput;
    private boolean debugMode;
    private int numberOfInputs;
    private boolean fixedNumberOfInputs;   
    private int numberOfColumnsToMatch;
    private String findValue;
    private String joinValue;
    private List<String> formatsOfInputs;
    private String fixcolsValue;
    
    /**
      * The subclass of Activity which should be instantiated when adding a service
      * for this description 
      */
    @Override
    public Class<? extends Activity<ExactMatchBean>> getActivityClass() {
        return ExactMatchActivity.class;
    }

    /**
      * The configuration bean which is to be used for configuring the instantiated activity. 
      * Making this bean will typically require some of the fields set on this service
      * description, like an endpoint URL or method name. 
      * 
      */
    @Override
    public ExactMatchBean getActivityConfiguration() {
        return new ExactMatchBean(this);
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
        return "Exact Match tool";
    }

    /**
      * The path to this service description in the service palette. Folder * will be created for each element of the returned path.
      */
    @Override
    public List<String> getPath() {
        // For deeper paths you may return several strings
        return Arrays.asList("Stilts Tools","ExactMatch");
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
    @Override
    public String getFormatOfOutput() {
        return formatOfOutput;
    }

    /**
     * @param formatOfOutput the formatOfOutput to set
     */
    @Override
    public void setFormatOfOutput(String formatOfOutput) {
        this.formatOfOutput = formatOfOutput;
    }

    /**
     * @return the typesOfInputs
     */
    @Override
    public List<String> getTypesOfInputs() {
        return typesOfInputs;
    }

    /**
     * @param typesOfInputs the typeOfInput to set
     */
    @Override
    public void setTypesOfInputs(List<String> typesOfInputs) {
        this.typesOfInputs = typesOfInputs;
    }

    /**
     * @return the typeOfOutput
     */
    @Override
    public String getTypeOfOutput() {
        return typeOfOutput;
    }

    /**
     * @param typeOfOutput the typeOfOutput to set
     */
    @Override
    public void setTypeOfOutput(String typeOfOutput) {
        this.typeOfOutput = typeOfOutput;
    }

    /**
     * @return the debugMode
     */
    @Override
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * @param debugMode the debugMode to set
     */
    @Override
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    @Override
    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    @Override
    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    @Override
    public boolean isFixedNumberOfInputs() {
        return fixedNumberOfInputs;
    }

    @Override
    public void setFixedNumberOfInputs(boolean fixedNumberOfInputs) {
        this.fixedNumberOfInputs = fixedNumberOfInputs;
    }

    @Override
    public int getNumbertOfColumnsToMatch() {
        return numberOfColumnsToMatch;
    }

    @Override
    public void setNumbertOfColumnsToMatch(int numberOfColumnsToMatch) {
        this.numberOfColumnsToMatch = numberOfColumnsToMatch;
    }

    @Override
    public String getFindValue() {
        return findValue;
    }

    @Override
    public void setFindValue(String findValue) {
        this.findValue = findValue;
    }

    @Override
    public String getJoinValue() {
        return joinValue;
    }

    @Override
    public void setJoinValue(String joinValue) {
        this.joinValue = joinValue;
    }

    @Override
    public String getFixcolsValue() {
        return fixcolsValue;
    }

    @Override
    public void setFixcolsValue(String fixcolsValue) {
        this.fixcolsValue = fixcolsValue;
    }

    @Override
    public List<String> getFormatsOfInputs() {
        return formatsOfInputs;
    }

    @Override
    public void setFormatsOfInputs(List<String> formatsOfInputs) {
        this.formatsOfInputs = formatsOfInputs;
    }

}
