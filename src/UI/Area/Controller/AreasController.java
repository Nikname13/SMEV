package UI.Area.Controller;

import Model.Area.AreaModel;
import Presenter.AreaPresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AreasController {

    private int mId;

    public AreasController(){
        //new AreaPresenter().update();
    }

    @FXML
    private TableView<AreaModel> tableViewArea;

    @FXML
    private TableColumn<AreaModel,String> firstColumn;

    @FXML
    private AnchorPane anchorPaneArea;

    @FXML
    public void initialize(){
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewArea.setItems(new AreaPresenter().getObservableArea());
        tableViewArea.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> deleteArea(newValue.getId())));
    }

    private void deleteArea(int id){
        mId=id;
    }

    @FXML
    private void onClickDelete(){
        if(mId != -1){
            new AreaPresenter().delete(mId);
            mId=-1;
        }
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddAreaWindow((Stage)anchorPaneArea.getScene().getWindow(),100.0,200.0);
    }
}
