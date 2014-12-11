package net.sf.taverna.t2.activities.table.process;

import java.util.List;

import net.sf.taverna.t2.activities.table.configuration.TableParameterConfiguration;
import net.sf.taverna.t2.activities.table.input.TwoInputsBean;
import net.sf.taverna.t2.activities.table.utils.TableFind;
import net.sf.taverna.t2.activities.table.utils.TableFixcols;
import net.sf.taverna.t2.activities.table.utils.TableJoin;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Base class for applying a Stilts Stilts Match/ join of two tables.
 * <p>
 * Based on {$link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#tmatch2}
 * <p>
 * Allows Workflow designers to specify match/join criteria such as find, fixcols and join.
 * <p>
 * Super classes will define how the matching is done.
 * @author christian
 */
public abstract class TMatch2Bean extends AbstractProcessBean {
 
    /**
     * Determines what happens when a row in one table can be matched by more than one row in the other table
     */
    private TableFind findValue;
    private final String FIND_NAME = "How to handle multiple matches";
            
    /**
     * Determines which rows are included in the output table. 
     */
    private TableJoin joinValue;
    private final String JOIN_NAME = "Which rows to include";
    
    /**
     * Determines how input columns are renamed before use in the output table
     */
    private TableFixcols fixcolsValue;
    private final String FIX_COLS_NAME = "How to rename columns";

    /**
     * Serialization constructor
     */
    TMatch2Bean(){
    }
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param findEnum
     * @param fixcolsEnum
     * @param joinEnum
     * @param inputBean 
     */
    TMatch2Bean(TableFind findEnum, TableFixcols fixcolsEnum, TableJoin joinEnum, TwoInputsBean inputBean){
        setInputs(inputBean);
        this.findValue = findEnum;
        this.fixcolsValue = fixcolsEnum;
        this.joinValue = joinEnum;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        super.checkValid();
        if (findValue == null){
             throw new ActivityConfigurationException("Find parameter not set.");
        }
        if (joinValue == null){
             throw new ActivityConfigurationException("Join Paramter not set.");
        }
        if (fixcolsValue == null){
             throw new ActivityConfigurationException("Fixcols parameter not set.");
        }
    }

    /**
     * @return the findValue
     */
    public TableFind getFindValue() {
        return findValue;
    }

    /**
     * @param findValue the findValue to set
     */
    public void setFindValue(TableFind findValue) {
        this.findValue = findValue;
    }

    /**
     * @return the joinValue
     */
    public TableJoin getJoinValue() {
        return joinValue;
    }

    /**
     * @param joinValue the joinValue to set
     */
    public void setJoinValue(TableJoin joinValue) {
        this.joinValue = joinValue;
    }

    /**
     * @return the fixcolsValue
     */
    public TableFixcols getFixcolsValue() {
        return fixcolsValue;
    }

    /**
     * @param fixcolsValue the fixcolsValue to set
     */
    public void setFixcolsValue(TableFixcols fixcolsValue) {
        this.fixcolsValue = fixcolsValue;
    }

    @Override
    public String retrieveStilsCommand() {
        //Currently the remaining parameters are applied in TableActivity.createProcessParameters
        //this should idealy be moved here.
        return "tmatch2";
    }

    @Override
    public List<TableParameterConfiguration> configurations() {
        List<TableParameterConfiguration> configurations = super.configurations();
        configurations.add(new TableParameterConfiguration (FIND_NAME,  findValue));
        configurations.add(new TableParameterConfiguration (JOIN_NAME,  joinValue));
        configurations.add(new TableParameterConfiguration (FIX_COLS_NAME,  fixcolsValue));
        return configurations;        
    }

}
   