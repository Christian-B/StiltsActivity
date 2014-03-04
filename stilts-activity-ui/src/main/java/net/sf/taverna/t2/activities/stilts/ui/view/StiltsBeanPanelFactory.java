package net.sf.taverna.t2.activities.stilts.ui.view;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.sf.taverna.t2.activities.stilts.AbstractStiltsBean;
import net.sf.taverna.t2.activities.stilts.MultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.ui.config.AbstractStiltsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.MultipleInputsConfigurationPanel;
import net.sf.taverna.t2.activities.stilts.ui.config.SingleFormatMultipleInputsConfigurationPanel;

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

    static void refreshView(SingleFormatMultipleInputsBean bean, StiltsBeanPanel mainFrame) {
        mainFrame.clear();
        refreshSingleFormatMultipleInputsBean(bean, mainFrame);
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
        JLabel numberOfInputsLabel = new JLabel(MultipleInputsConfigurationPanel.NUMBER_OF_INPUTS + ": " + bean.getNumberOfInputs());
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
}
