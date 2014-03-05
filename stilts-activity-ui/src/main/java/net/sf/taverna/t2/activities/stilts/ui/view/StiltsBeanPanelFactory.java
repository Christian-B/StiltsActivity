package net.sf.taverna.t2.activities.stilts.ui.view;

import java.util.List;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.AbstractMatchActivity;
import net.sf.taverna.t2.activities.stilts.AbstractMatchBean;
import net.sf.taverna.t2.activities.stilts.AbstractStiltsBean;
import net.sf.taverna.t2.activities.stilts.ExactMatchBean;
import net.sf.taverna.t2.activities.stilts.MultipleFormatsBean;
import net.sf.taverna.t2.activities.stilts.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.ui.config.AbstractStiltsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.ExactMatchConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.MultipleFormatsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.MultipleInputsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.SingleFormatMultipleInputsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.SingleInputConfigurationPanel;

/**
 *
 * @author christian
 */
public class StiltsBeanPanelFactory {

    static StiltsBeanPanel getMainFrame(SingleFormatMultipleInputsBean bean) {
        StiltsBeanPanel mainFrame = new StiltsBeanPanel();
        refreshView(bean, mainFrame);
        return mainFrame;
    }

    static StiltsBeanPanel getMainFrame(SingleInputBean bean) {
        StiltsBeanPanel mainFrame = new StiltsBeanPanel();
        refreshView(bean, mainFrame);
        return mainFrame;
    }

    static StiltsBeanPanel getMainFrame(MultipleFormatsBean bean) {
        StiltsBeanPanel mainFrame = new StiltsBeanPanel();
        refreshView(bean, mainFrame);
        return mainFrame;
    }

    static StiltsBeanPanel getMainFrame(ExactMatchBean bean) {
        StiltsBeanPanel mainFrame = new StiltsBeanPanel();
        refreshView(bean, mainFrame);
        return mainFrame;
    }

    static void refreshView(ExactMatchBean bean, StiltsBeanPanel mainFrame) {
        mainFrame.clear();
        refreshExactMatchBean(bean, mainFrame);
    }
    
    static void refreshView(SingleFormatMultipleInputsBean bean, StiltsBeanPanel mainFrame) {
        mainFrame.clear();
        refreshSingleFormatMultipleInputsBean(bean, mainFrame);
    }
    
    static void refreshView(SingleInputBean bean, StiltsBeanPanel mainFrame) {
        mainFrame.clear();
        refreshSingleInputBean(bean, mainFrame);
    }

    static void refreshView(MultipleFormatsBean bean, StiltsBeanPanel mainFrame) {
        mainFrame.clear();
        refreshMultipleFormatsBean(bean, mainFrame);
    }

    static void refreshAbstractStiltsBean(AbstractStiltsBean bean, StiltsBeanPanel mainFrame) {
        JLabel formatOfOutputLabel = new JLabel(AbstractStiltsConfigurationPanel.OUTPUT_FORMAT_LABEL + ": " + bean.getFormatOfOutput());
        mainFrame.addOutput(formatOfOutputLabel);
        JLabel typeOfOutputLabel = new JLabel(AbstractStiltsConfigurationPanel.OUTPUT_TYPE_LABEL + ": " + bean.getTypeOfOutput());
        mainFrame.addOutput(typeOfOutputLabel);
        JLabel debugLabel = new JLabel(AbstractStiltsConfigurationPanel.DEBUG_LABEL + ": " + bean.isDebugMode());
        mainFrame.addMiscellaneous(debugLabel);
    }
    
    static void refreshMultipleInputsBean(MultipleInputsBean bean, StiltsBeanPanel mainFrame) {
        refreshAbstractStiltsBean(bean, mainFrame);
        JLabel numberOfInputsLabel;
        if (bean.isFixedNumberOfInputs()){
            numberOfInputsLabel = new JLabel("Fixed " + MultipleInputsConfigurationPanel.NUMBER_OF_INPUTS + ": " + bean.getNumberOfInputs());
        } else {
            numberOfInputsLabel = new JLabel("Changeable " + MultipleInputsConfigurationPanel.NUMBER_OF_INPUTS + ": " + bean.getNumberOfInputs());
        }
        mainFrame.addInput(numberOfInputsLabel);
        List<String> typesOfInputs = bean.getTypesOfInputs();
        for (int i = 0; i < typesOfInputs.size(); i++){
            JLabel typeOfInputLabel = new JLabel(MultipleInputsConfigurationPanel.INPUT_TYPE_LABEL + (i+1) + ": " + typesOfInputs.get(i));
            mainFrame.addInput(typeOfInputLabel);
        }
    }

    static void refreshSingleFormatMultipleInputsBean(SingleFormatMultipleInputsBean bean, StiltsBeanPanel mainFrame) {
        refreshMultipleInputsBean(bean, mainFrame);
        JLabel formatOfInputsLabel = new JLabel(SingleFormatMultipleInputsConfigurationPanel.INPUT_FORMAT_LABEL + ": " + bean.getFormatOfInputs());
        mainFrame.addInput(formatOfInputsLabel);
    }

    static void refreshMultipleFormatsBean(MultipleFormatsBean bean, StiltsBeanPanel mainFrame) {
        refreshMultipleInputsBean(bean, mainFrame);
        List<String> formatsOfInputs = bean.getFormatsOfInputs();
        for (int i = 0; i < formatsOfInputs.size(); i++){
            JLabel formatsOfInputLabel = new JLabel(MultipleFormatsConfigurationPanel.INPUT_FORMAT_LABEL + (i+1) + ": " + formatsOfInputs.get(i));
            mainFrame.addInput(formatsOfInputLabel);
        }
    }

    private static void refreshSingleInputBean(SingleInputBean bean, StiltsBeanPanel mainFrame) {
        refreshAbstractStiltsBean(bean, mainFrame);
        JLabel typeOfInputLabel = new JLabel(SingleInputConfigurationPanel.INPUT_TYPE_LABEL + ": " + bean.getTypeOfInput());
        mainFrame.addInput(typeOfInputLabel);
        JLabel formatOfInputLabel = new JLabel(SingleInputConfigurationPanel.INPUT_FORMAT_LABEL + ": " + bean.getFormatOfInput());
        mainFrame.addInput(formatOfInputLabel);
        //    private String formatOfInput;
        //private String typeOfInput;

    }
    
    private static void refreshExactMatchBean(ExactMatchBean bean, StiltsBeanPanel mainFrame) {
        refreshAbstractMatchBean(bean, mainFrame);
        JLabel numberOfColumnsToMatchLabel = new JLabel(ExactMatchConfigurationPanel.NUMBER_OF_COLUMNS + ": " + bean.getNumbertOfColumnsToMatch());
        mainFrame.addMiscellaneous(numberOfColumnsToMatchLabel);
    }

    private static void refreshAbstractMatchBean(AbstractMatchBean bean, StiltsBeanPanel mainFrame) {
        refreshMultipleFormatsBean(bean, mainFrame);
        JLabel findLabel = new JLabel(AbstractMatchActivity.FIND_PARAMETER_NAME + ": " + bean.getFindValue());
        mainFrame.addMiscellaneous(findLabel);
        JLabel joinLabel = new JLabel(AbstractMatchActivity.JOIN_PARAMETER_NAME + ": " + bean.getJoinValue());
        mainFrame.addMiscellaneous(joinLabel);
        JLabel fixcolsLabel = new JLabel(AbstractMatchActivity.FIXCOLS_PARAMETER_NAME + ": " + bean.getFixcolsValue());
        mainFrame.addMiscellaneous(fixcolsLabel);
     }


}
