/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.taverna.t2.activities.stilts.preprocess;

/**
 *
 * @author christian
 */
public class DeleteColumnPreProcessorBean extends StiltsPreProcessBean{
    
    private String column;

    public DeleteColumnPreProcessorBean(){  
    }

    public DeleteColumnPreProcessorBean(String column){  
        this.column = column;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

      
}
