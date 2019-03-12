package UI.Type.Controller;

import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Presenter.TypePresenter;
import Service.ListenersService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

public class EditTypeController extends BaseTypeController {

    private TypeModel mTypeModel;
    @FXML
    private TreeTableView<ParameterModel> mTreeTableViewParameter;
    @FXML
    private TreeTableColumn<ParameterModel, String> mNameColumn;
    @FXML
    private JFXTextField mTextFieldName;
    @FXML
    private JFXButton mButtonUpdate;

    public EditTypeController() {
        ListenersService.get().addListenerUI(this);
        setTypeModel(TypePresenter.get().getTypeModel());
    }

    @FXML
    public void initialize(){
        setTreeTableViewParameters(mTreeTableViewParameter);
        setNameColumn(mNameColumn);
        setDialogPane(getParentStackPane());
        initTextField(mTextFieldName);
        initTableView();
    }

    @Override
    protected void initTextField(JFXTextField textField) {
        super.initTextField(textField);
        mTextFieldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mTextFieldName.focusedProperty().get()) {
                    setVisibleEditButton();
                }
            }
        });
    }

    @Override
    protected boolean editType(ObservableList<ParameterModel> parametersList) {
        TypePresenter.get().setTypeModel(getTypeModel());
        TypePresenter.get().editType(parametersList);
        return true;
    }

    private void setVisibleEditButton() {
        mButtonUpdate.setVisible(true);
    }

    private void setInvisibleEditButton() {
        mButtonUpdate.setVisible(false);
    }

    @FXML
    private void onClickAdd() {
        if (getBaseValidator().validate()) {
            setInvisibleEditButton();
            TypePresenter.get().setTypeModel(getTypeModel());
            TypePresenter.get().editType(mTextFieldName.getText());
        }
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(TypeModel.class.getName())) {
            mTypeModel = TypePresenter.get().getTypeModel();
            mTextFieldName.setText(mTypeModel.getName());
            setParametersList(mTypeModel.getObservableEntityList());
            updateTableView(mTypeModel.getObservableEntityList());
            setInvisibleEditButton();
        }
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
