package UI.Department.Controller;

import Model.Area.AreaModel;
import Model.Location.LocationModel;
import Presenter.DepartmentPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddDepartmentController extends BaseController {

    private AreaModel mAreaModel;
    private boolean mLocationIsSelected;
    private LocationModel mLocation;
    private BaseValidator mBaseValidator = new BaseValidator();

    @FXML
    private JFXTextField mTextFieldNumber, mTextFieldName;

    @FXML
    private RadioButton mRadioButtonRenting, mRadioButtonElQ;

    @FXML
    private JFXTextArea mTextAreaDescription;

    @FXML
    private JFXComboBox<AreaModel> mComboBoxArea;

    @FXML
    private JFXComboBox<LocationModel> mComboBoxLocation;

    @FXML
    private ValidationFacade mFacadeArea, mFacadeLocation;

    @FXML
    private Label mErrorArea, mErrorLocation;

    @FXML
    private AnchorPane mAnchorPaneAddDepartment;



    @FXML
    public void initialize(){
        mLocationIsSelected = false;
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldNumber);
        mBaseValidator.setValidationFacades(new Pair(mFacadeArea, mErrorArea, mComboBoxArea), new Pair(mFacadeLocation, mErrorLocation, mComboBoxLocation));
        initComboBox();
    }

    private void initComboBox() {
        initJFXComboBox(new AreaModel(), mComboBoxArea, false, "Выберите район", "Район");
        initJFXComboBox(new LocationModel(), mComboBoxLocation, false, "Выберите или введите адрес", "Адрес");
        mComboBoxLocation.setItems(DepartmentPresenter.get().getObservableLocation());
        mComboBoxLocation.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedLocation()));
        mComboBoxLocation.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mLocationIsSelected = false;
            }
        });
        mComboBoxArea.setItems(DepartmentPresenter.get().getObservableArea());

    }

    @Override
    protected Stage getStage() {
        return null;
    }

    private void selectedLocation(){
        if(mComboBoxLocation.getSelectionModel().getSelectedIndex()!=-1){
            mLocationIsSelected = true;
            mLocation = mComboBoxLocation.getValue();
        }
    }

    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            close(mAnchorPaneAddDepartment);
            if (mLocationIsSelected) {
              DepartmentPresenter.get().addDepartment(mTextFieldNumber.getText(),
                      mTextFieldName.getText(),
                      mRadioButtonElQ.isSelected(),
                      mRadioButtonRenting.isSelected(),
                      mTextAreaDescription.getText(),
                      mComboBoxArea.getValue(),
                      mLocation);
          }else {
              DepartmentPresenter.get().addDepartment(mTextFieldNumber.getText(),
                      mTextFieldName.getText(),
                      mRadioButtonElQ.isSelected(),
                      mRadioButtonRenting.isSelected(),
                      mTextAreaDescription.getText(),
                      mComboBoxArea.getValue(),
                      mComboBoxLocation.getEditor().getText());
          }

        }

    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneAddDepartment);
    }
}
