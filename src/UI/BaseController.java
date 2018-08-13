package UI;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class BaseController {

    public void nextTab(Tab nextTab , TabPane tabPane){
        tabPane.getTabs().add(nextTab);
        tabPane.getSelectionModel().select(nextTab);
        if(tabPane.getSelectionModel().getSelectedIndex()-1 !=0){
            tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex()-1);
        }
    }


}
