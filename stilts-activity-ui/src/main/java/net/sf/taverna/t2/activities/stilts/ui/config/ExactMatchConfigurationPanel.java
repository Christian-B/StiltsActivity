package net.sf.taverna.t2.activities.stilts.ui.config;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.ExactMatchActivity;
import net.sf.taverna.t2.activities.stilts.ExactMatchBean;

@SuppressWarnings("serial")
public class ExactMatchConfigurationPanel 
        <BoundedActivity extends ExactMatchActivity, 
        BoundedBean extends ExactMatchBean>  extends
        AbstractMatchConfigurationPanel<BoundedActivity, BoundedBean> {

    private JTextField  numberOfColumnsToMatchField;
    protected int  numberOfColumnsToMatch;
            
    public static final String NUMBER_OF_COLUMNS = "Numbert Of Columns To Match";
            
    public ExactMatchConfigurationPanel(BoundedActivity activity) {
        super(activity);
    }

    protected void initGui() {
        super.initGui();

        numberOfColumnsToMatch = configBean.getNumbertOfColumnsToMatch();
        JLabel numberOfColumnsToMatchLabel = new JLabel(NUMBER_OF_COLUMNS  + ": ");
        miscellaneousPanel.add(numberOfColumnsToMatchLabel, 0);
        numberOfColumnsToMatchField = new JTextField(numberOfColumnsToMatch+"");
        miscellaneousPanel.add(numberOfColumnsToMatchField, 1);
        numberOfColumnsToMatchLabel.setLabelFor(numberOfColumnsToMatchField);
    }
       
    /**
      * Check that user values in UI are valid
      */
    @Override
    public boolean checkValues() {
        if (!super.checkValues()){
            return false;
        }
        String numberOfColumnsToMatchString = numberOfColumnsToMatchField.getText();
        if (numberOfColumnsToMatchString == null || numberOfColumnsToMatchString.isEmpty()){
            String message = NUMBER_OF_COLUMNS + " must be specified";
            JOptionPane.showMessageDialog(this, message, "Missing " + NUMBER_OF_COLUMNS, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int  newNumberOfColumnsToMatch;
        try{
             newNumberOfColumnsToMatch = Integer.parseInt(numberOfColumnsToMatchString);
        } catch (NumberFormatException ex){
            String message = NUMBER_OF_COLUMNS + " is not a valid possitive integer.";
            JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_COLUMNS, JOptionPane.ERROR_MESSAGE);
            return false;            
        }
        if (newNumberOfColumnsToMatch < 1){
            String message = NUMBER_OF_COLUMNS + " should be 1 or more.";
            JOptionPane.showMessageDialog(this, message, "Invalid " + NUMBER_OF_COLUMNS, JOptionPane.ERROR_MESSAGE);
            return false;                        
        }
         // All valid, return true
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (configBean.getNumbertOfColumnsToMatch() != numberOfColumnsToMatch){
            return true;
        }
        return false;
    }
    
    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        noteConfiguration(new ExactMatchBean());
    }

    protected void noteConfiguration(ExactMatchBean bean) {
    	super.noteConfiguration(bean);
    
        numberOfColumnsToMatch = Integer.parseInt(numberOfColumnsToMatchField.getText());
        configBean.setNumbertOfColumnsToMatch(numberOfColumnsToMatch);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();
        numberOfColumnsToMatch = configBean.getNumbertOfColumnsToMatch();
        numberOfColumnsToMatchField.setText(numberOfColumnsToMatch +"");
     }
    
}
