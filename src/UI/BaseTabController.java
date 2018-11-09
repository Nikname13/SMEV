package UI;

import Service.ErrorService;
import Service.IUpdateUI;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public abstract class BaseTabController implements IUpdateUI {

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

    @Override
    public void updateUI(Class<?> updateClass) {
        ErrorService.get().overrideError("updateUI", this.getClass());
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        ErrorService.get().overrideError("refreshControl", this.getClass());
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        ErrorService.get().overrideError("updateControl", this.getClass());
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {
        ErrorService.get().overrideError("updateControl", this.getClass());
    }
}
