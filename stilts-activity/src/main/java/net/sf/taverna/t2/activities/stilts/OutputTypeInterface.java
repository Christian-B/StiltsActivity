package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;

/**
 * Stilts activity configuration bean.
 * 
 */
public interface OutputTypeInterface extends Serializable {
     /**
     * @return the formatOfOutput
     */
    public String getFormatOfOutput();

    /**
     * @param formatOfOutput the formatOfOutput to set
     */
    public void setFormatOfOutput(String formatOfOutput);

    /**
     * @return the typeOfOutput
     */
    public String getTypeOfOutput();

    /**
     * @param typeOfOutput the typeOfOutput to set
     */
    public void setTypeOfOutput(String typeOfOutput);

    /**
     * @return the debugMode
     */
    public boolean isDebugMode();
    /**
     * @param debugMode the debugMode to set
     */
    public void setDebugMode(boolean debugMode);

}
