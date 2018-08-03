package UI.Parameter.Controller;

import Model.Parameter.ParameterModel;
import Model.Parameter.ValueParameterModel;
import Presenter.ParametersPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditParameterController {

    private static ParameterModel mParameter;
    private static List<Object> mEditValues, mdeleteValue;

    public EditParameterController() {
        mParameter = new ParametersPresenter().getParameter();
        mEditValues = new ArrayList<>();
        mdeleteValue = new ArrayList<>();
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

        nameParameter.setText(mParameter.getName());
        //первое обращение к базе для загрузки списка параметров
        if (!mParameter.getEntityList().isEmpty()) {
            System.out.println("UI getValueList != null");
            tableViewEditParameters.setVisible(true);
            valuesTextArea.setVisible(false);
            firstColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            tableViewEditParameters.setItems(mParameter.getObservableEntityList());
            tableViewEditParameters.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> editParameter(newValue.getId()));
            isValue.setSelected(mParameter.isValue());
        } else {
            tableViewEditParameters.setVisible(false);
            valuesTextArea.setVisible(true);
        }
    }

    @FXML
    private void onClickOk() {
        if(isValue.isSelected()) {
            if (!mParameter.isValue()) {
                String s[] = valuesTextArea.getText().split("\n");
                if (s.length > 0) {
                    for (int i = 0; i < s.length; i++) {
                        mEditValues.add(s[i]);
                    }
                } else {
                    isValue.setSelected(false);
                }
            }
        }
        new ParametersPresenter().editParameter(nameParameter.getText(), isValue.isSelected(), mEditValues, mdeleteValue);
    }

    private void editParameter(int value) {
        System.out.println("editParameter "+value);
        Set<Integer> id = new HashSet<>();
        id.add(value);
        new ParametersPresenter().deleteValueParameter(id);
    }

    @FXML
    private void isValueChange() {
        System.out.println("isValueChange");
    }

    @FXML
    private void onDelete() {
        new ParametersPresenter().deleteParameters(mParameter.getId());
    }

}


