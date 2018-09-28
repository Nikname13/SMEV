package UI;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public abstract class BaseTabController {

    public void nextTab(Tab nextTab, TabPane tabPane) {
        boolean flag = false;
        for (Tab tab : tabPane.getTabs()) {
            System.out.println("TABS " + tab.getContent().getId());
            if (tab.getContent().getId().equals(nextTab.getContent().getId())) {
                flag = true;
                break;
            }
        }
        if (flag) {
            tabPane.getSelectionModel().select(nextTab);
        } else {
            tabPane.getTabs().add(nextTab);
            tabPane.getSelectionModel().selectLast();
        }

/*        if (tabPane.getSelectionModel().getSelectedIndex() - 1 != 0) {
            tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex() - 1);
        }*/
    }


}
