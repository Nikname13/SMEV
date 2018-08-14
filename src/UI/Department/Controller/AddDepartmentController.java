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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class AddDepartmentController extends BaseController {

    private AreaModel mAreaModel;
    private boolean mFlagLocation;
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
        mFlagLocation=false;
        mBaseValidator.setJFXTextFields(mTextFieldName, mTextFieldNumber);
        mBaseValidator.setValidationFacades(new Pair(mFacadeArea, mErrorArea), new Pair(mFacadeLocation, mErrorLocation));
        initComboBoxArea(mComboBoxArea);
        initComboBoxLocation(mComboBoxLocation);

    }

    @Override
    protected void initComboBoxLocation(JFXComboBox<LocationModel> comboBoxLocation) {
        super.initComboBoxLocation(comboBoxLocation);
        comboBoxLocation.setItems(DepartmentPresenter.get().getObservableLocation());
        mComboBoxLocation.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedLocation()));
    }

    @Override
    protected void initComboBoxArea(JFXComboBox<AreaModel> comboBoxArea) {
        super.initComboBoxArea(comboBoxArea);
        comboBoxArea.setItems(DepartmentPresenter.get().getObservableArea());
    }

    private void selectedLocation(){
        if(mComboBoxLocation.getSelectionModel().getSelectedIndex()!=-1){
            setSelectedLocation(true);
            mLocation = mComboBoxLocation.getValue();
        }
    }

    @FXML
    private void onClickAdd(){
        if (mBaseValidator.validate()) {
            if (isSelectedLocation()) {
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
            close(mAnchorPaneAddDepartment);
        }

    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneAddDepartment);
    }
/*
    private void close() {
        Stage stage = (Stage) mAnchorPaneAddDepartment.getScene().getWindow();
        stage.close();
    }*/

}
