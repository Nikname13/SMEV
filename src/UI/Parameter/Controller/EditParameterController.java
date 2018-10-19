package UI.Parameter.Controller;

import Model.Parameter.ValueParameterModel;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class EditParameterController extends BaseController implements IUpdateUI {

    public EditParameterController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    private TableView<ValueParameterModel> tableViewEditParameters;

    @FXML
    private TableColumn<ValueParameterModel, String> firstColumn;

    @FXML
    private TextField nameParameter;

    @FXML
    private RadioButton isValue;

    @FXML
    private TextArea valuesTextArea;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onClickOk() {
    }

    private void editParameter(int value) {
        System.out.println("editParameter "+value);
        Set<Integer> id = new HashSet<>();
        id.add(value);
    }

    @FXML
    private void isValueChange() {
        System.out.println("isValueChange");
    }

    @FXML
    private void onDelete() {
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
        return null;
    }
}


