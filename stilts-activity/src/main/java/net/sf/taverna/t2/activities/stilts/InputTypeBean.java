package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;

/**
 * Stilts activity configuration bean.
 * 
 */
public class InputTypeBean extends OutputTypeBean 
        implements InputTypeInterface, Serializable {
    private String formatOfInput;
    private String typeOfInput;
      
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

}
