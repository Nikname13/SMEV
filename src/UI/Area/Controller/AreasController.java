package UI.Area.Controller;

import Model.Area.AreaModel;
import Presenter.AreaPresenter;
import Presenter.BasePresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AreasController extends BaseController implements IUpdateUI {

    public AreasController(){
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private TableView<AreaModel> tableViewArea;

    @FXML
    private TableColumn<AreaModel,String> firstColumn;

    @FXML
    private AnchorPane mAnchorPaneArea;

    @FXML
    public void initialize(){
        initTable();
    }

    private void initTable() {
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewArea.setItems(AreaPresenter.get().getObservableArea());
        tableViewArea.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedArea(newValue)));
    }

    private void selectedArea(AreaModel newValue) {

    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddAreaWindow(getStage());
    }

    @Override
    public void updateUI(Class<?> updateClass) {

    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPaneArea.getScene().getWindow();
    }
}
