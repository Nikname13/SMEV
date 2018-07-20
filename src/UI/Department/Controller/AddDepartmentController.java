package UI.Department.Controller;

import Model.Area.AreaModel;
import Model.Location.LocationModel;
import Presenter.DepartmentPresenter;
import UI.Validator.TextValidator;
import com.google.gson.annotations.Expose;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class AddDepartmentController {

    private AreaModel mAreaModel;
    private List<JFXTextField> mValidText;

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
        RequiredFieldValidator validator=new RequiredFieldValidator();
        validator.setMessage("Необходимо выбрать значение");
        mValidationArea.getValidators().add(validator);
        mValidationLocation.getValidators().add(validator);
        mValidText=TextValidator.setTextFieldValidator(mTextFieldName, mTextFieldNumber);
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
        mComboBoxArea.setConverter(new StringConverter<AreaModel>() {
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
      flag=TextValidator.validationFacade(mValidationArea,mValidationLocation);
      System.out.println(flag);

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
