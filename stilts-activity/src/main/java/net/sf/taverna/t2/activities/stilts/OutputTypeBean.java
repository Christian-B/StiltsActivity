package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;

/**
 * Stilts activity configuration bean.
 * 
 */
public class OutputTypeBean implements OutputTypeInterface, Serializable {
    private String formatOfOutput;
    private String typeOfOutput;
    private boolean debugMode = true;
     
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
