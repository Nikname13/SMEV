package UI;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public abstract class BaseTabController {

    public static void nextTab(Tab nextTab, TabPane tabPane) {
        boolean flag = false;
        for (Tab tab : tabPane.getTabs()) {
            System.out.println("TABS " + tab.getContent().getId());
            if (tab.getContent().getId().equals(nextTab.getContent().getId())) {
                flag = true;
                break;
            }
        }
        if (flag && !tabPane.getSelectionModel().getSelectedItem().getContent().getId().equals(nextTab.getContent().getId())) {
                tabPane.getSelectionModel().select(nextTab);
        } else {
            System.out.println("add tab");
            tabPane.getTabs().add(nextTab);
            tabPane.getSelectionModel().selectLast();
        }
    }


}
