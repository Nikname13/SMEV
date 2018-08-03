package UI.Type.Controller;

import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Presenter.TypePresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EditTypeController {

    private static TypeModel sTypeModel;

    public EditTypeController() {
        sTypeModel=new TypePresenter().getTypeModel();
    }

    @FXML
    private TableView<ParameterModel> tableViewEditParameters;

    @FXML
    private TableColumn<ParameterModel,String> firstColumn;

    @FXML
    private TextField nameType;

    @FXML
    public void initialize(){
        nameType.setText(sTypeModel.getName());
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewEditParameters.setItems(sTypeModel.getObservableEntityList());
        tableViewEditParameters.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->editEntity(newValue) );
    }

    private void editEntity(Object entity){
        new TypePresenter().deleteParameter(entity);
    }

    @FXML
    private void onClickOk(){
        new TypePresenter().editType(nameType.getText(),null);
    }

    @FXML
    private void onClickDelete(){
        new TypePresenter().deleteType(sTypeModel.getId());
    }
}
