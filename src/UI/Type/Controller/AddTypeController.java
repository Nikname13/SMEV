package UI.Type.Controller;

import Model.Parameter.ParameterModel;
import Presenter.TypePresenter;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AddTypeController extends BaseTypeController {

    private ObservableList<ParameterModel> mParametersList;


    @FXML
    private TreeTableView<ParameterModel> mTreeTableViewParameters;

    @FXML
    private TreeTableColumn<ParameterModel,String> mNameColumn;

    @FXML
    private JFXTextField mNameType;

    @FXML
    private StackPane mStackPaneAddType;

    @FXML
    public void initialize(){

        mParametersList=FXCollections.observableArrayList();
        setTreeTableViewParameters(mTreeTableViewParameters);
        setNameColumn(mNameColumn);
        setParametersList(mParametersList);
        setDialogPane(mStackPaneAddType);
        initTextField(mNameType);
        initTableView();
        updateTableView(mParametersList);
    }

    @Override
    protected void resizeHeightStage(double prefHeight) {
        if (mParametersList.size() >= 1) {
            getStage().setHeight(getStage().getMinHeight() + (prefHeight - mTreeTableViewParameters.getMinHeight()));
        } else {
            getStage().setHeight(getStage().getMinHeight());
        }
    }

    @FXML
    private void onClickAdd(){
        if (getBaseValidator().validate()) {
            TypePresenter.get().addType(mNameType.getText(),mParametersList);
            close(mStackPaneAddType);
        }
    }

    @FXML
    private void onClickCancel(){
        close(mStackPaneAddType);
    }

    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneAddType.getScene().getWindow();    }
}
