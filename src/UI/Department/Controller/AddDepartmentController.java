package UI.Department.Controller;

import Model.Area.AreaModel;
import Model.Location.LocationModel;
import Presenter.DepartmentPresenter;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AddDepartmentController {

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
        mComboBoxArea.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(AreaModel item,boolean empty){
                super.updateItem(item,empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else setText(null);
            }
        });
        mComboBoxArea.setItems(DepartmentPresenter.get().getObservableArea());
        mComboBoxArea.setConverter(new StringConverter<>() {
            @Override
            public String toString(AreaModel object) {
                if(object!=null) return object.getName();
                else return null;
            }

            @Override
            public AreaModel fromString(String string) {
                if (!string.isEmpty()) return new AreaModel(-1, string.trim());
                return null;
            }
        });
        mComboBoxArea.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedWorker(newValue));
        mComboBoxLocation.setItems(DepartmentPresenter.get().getObservableLocation());
        mComboBoxLocation.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(LocationModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else setText(null);
            }
        });
        mComboBoxLocation.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocationModel object) {
                if(object!=null) return object.getName();
                else return null;
            }

            @Override
            public LocationModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new LocationModel(-1, string.trim());
                else {
                    mComboBoxLocation.getEditor().clear();
                    return null;
                }
            }
        });
        mComboBoxLocation.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mFlagLocation=false;
            }
        });
        mComboBoxLocation.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedLocation()));
    }

    private void selectedLocation(){
        if(mComboBoxLocation.getSelectionModel().getSelectedIndex()!=-1){
            mFlagLocation=true;
            mLocation=mComboBoxLocation.getSelectionModel().getSelectedItem();
        }
    }

    private void selectedWorker(AreaModel area){
        mAreaModel=area;
    }

    @FXML
    private void onClickAdd(){
        System.out.println(mBaseValidator.validate());
        if (mBaseValidator.validate()) {
          if(mFlagLocation){
              DepartmentPresenter.get().addDepartment(mTextFieldNumber.getText(),
                      mTextFieldName.getText(),
                      mRadioButtonElQ.isSelected(),
                      mRadioButtonRenting.isSelected(),
                      mTextAreaDescription.getText(),
                      mAreaModel,
                      mComboBoxLocation.getSelectionModel().getSelectedItem());
          }else {
              DepartmentPresenter.get().addDepartment(mTextFieldNumber.getText(),
                      mTextFieldName.getText(),
                      mRadioButtonElQ.isSelected(),
                      mRadioButtonRenting.isSelected(),
                      mTextAreaDescription.getText(),
                      mAreaModel,
                      mComboBoxLocation.getEditor().getText());
          }
            close();
        }

    }

    @FXML
    private void onClickCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) mAnchorPaneAddDepartment.getScene().getWindow();
        stage.close();
    }

}
