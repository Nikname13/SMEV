package UI.Department.Controller;

import Model.Area.AreaModel;
import Model.Location.LocationModel;
import Presenter.DepartmentPresenter;
import UI.Validator.ControllerValidator;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.util.List;

public class AddDepartmentController {

    private AreaModel mAreaModel;
    private List<JFXTextField> mValidText;
    private boolean mFlagLocation;
    private LocationModel mLocation;

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
    private ValidationFacade mValidationArea, mValidationLocation;

    @FXML
    private AnchorPane mAnchorPaneAddDepartment;



    @FXML
    public void initialize(){
        mFlagLocation=false;
        RequiredFieldValidator validator=new RequiredFieldValidator();
        validator.setMessage("Необходимо выбрать значение");
        mValidationArea.getValidators().add(validator);
        mValidationLocation.getValidators().add(validator);
        mValidText=ControllerValidator.setTextFieldValidator(mTextFieldName, mTextFieldNumber);
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
                if(!string.isEmpty())return new AreaModel(-1,mComboBoxArea.getEditor().getText());
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
        mComboBoxLocation.setButtonCell(new ListCell<>(){
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
                return new LocationModel(-1,mComboBoxLocation.getEditor().getText());
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
        boolean flag=true;
      for(JFXTextField textField:mValidText){
         if(!textField.validate()) flag=false;
      }
      if(flag && ControllerValidator.validationFacade(mValidationArea,mValidationLocation)){
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
      }
/*        DepartmentPresenter.get().addDepartment(
                mTextFieldNumber.getText(),
                mTextFieldName.getText(),
                mRadioButtonElQ.isSelected(),
                mRadioButtonRenting.isSelected(),
                mTextAreaDescription.getText(),
                mAreaModel,
                mTextFieldLocation.getText());*/
    }

}
