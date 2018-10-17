package UI.Type.Controller;

import Model.Type.TypeModel;
import Presenter.TypePresenter;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TypesController {

    public TypesController(){
        //new TypePresenter().updateData();
    }

    @FXML
    private TableView<TypeModel> tableViewTypes;

    @FXML
    private TableColumn<TypeModel,String> firstColumn;

    @FXML
    private AnchorPane mAnchorPaneTypes;

    @FXML
    public void initialize(){
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewTypes.setItems(TypePresenter.get().getObservableType());
        tableViewTypes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->editType(newValue));
    }

    private void editType(Object type){
        TypePresenter.get().setTypeModel(type);
        new Coordinator().goToEditTypeWindow((Stage) mAnchorPaneTypes.getScene().getWindow());
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddTypeWindow((Stage) mAnchorPaneTypes.getScene().getWindow());
    }
}
