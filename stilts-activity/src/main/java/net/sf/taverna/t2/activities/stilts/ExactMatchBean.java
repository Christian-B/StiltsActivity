package net.sf.taverna.t2.activities.stilts;

import java.io.Serializable;

/**
 * Stilts activity configuration bean.
 * 
 */
public class ExactMatchBean extends AbstractMatchBean 
        implements ExactMatchInterface, Serializable {
 
    private int numbertOfColumnsToMatch;
    
    public ExactMatchBean(){
    }

    public ExactMatchBean(ExactMatchInterface other){
        super(other);
        this.numbertOfColumnsToMatch = other.getNumbertOfColumnsToMatch();
    }

    /**
     * @return the numbertOfColumnsToMatch
     */
    public int getNumbertOfColumnsToMatch() {
        return numbertOfColumnsToMatch;
    }

    /**
     * @param numbertOfColumnsToMatch the numbertOfColumnsToMatch to set
     */
    public void setNumbertOfColumnsToMatch(int numbertOfColumnsToMatch) {
        this.numbertOfColumnsToMatch = numbertOfColumnsToMatch;
    }

}
