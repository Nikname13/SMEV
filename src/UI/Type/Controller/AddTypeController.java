package UI.Type.Controller;

import Model.Parameter.ParameterModel;
import Presenter.TypePresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class AddTypeController {
    private List<Object> mParametersList;

    @FXML
    private TableView<ParameterModel> tableViewParameters;

    @FXML
    private TableColumn<ParameterModel,String> firstColumn;

    @FXML
    private TextField nameType;

    @FXML
    public void initialize(){
        mParametersList=new ArrayList<>();
        firstColumn.setCellValueFactory(cellData->cellData.getValue().nameProperty());
        tableViewParameters.setItems(new TypePresenter().getObservableParameter());
        tableViewParameters.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->selectedParameters(newValue)));
    }

    private void selectedParameters(Object parameter){
        System.out.println(parameter);
        mParametersList.add(parameter);
    }

    @FXML
    private void onClickOk(){
        new TypePresenter().addType(nameType.getText(),mParametersList);
    }
}
