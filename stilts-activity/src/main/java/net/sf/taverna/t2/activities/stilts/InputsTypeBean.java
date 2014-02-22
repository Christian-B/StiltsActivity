package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;
import java.util.List;

/**
 * Stilts activity configuration bean.
 * 
 */
public class InputsTypeBean extends OutputTypeBean 
        implements InputsTypeInterface, Serializable {
    private int numberOfInputs;
    private List<String> formatOfInputs;
    private List<String> typeOfInputs;

    @Override
    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    @Override
    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    @Override
    public List<String> getFormatOfInputs() {
        return formatOfInputs;
    }

    @Override
    public void setFormatOfInputs(List<String> formatOfInput) {
        this.formatOfInputs = formatOfInput;
    }

    @Override
    public List<String> getTypeOfInputs() {
        return typeOfInputs;
    }

    @Override
    public void setTypeOfInputs(List<String> typeOfInput) {
        this.typeOfInputs = typeOfInput;
    }
      
}
