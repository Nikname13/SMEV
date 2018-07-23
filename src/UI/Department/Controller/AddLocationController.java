package UI.Department.Controller;

import Iteractor.IteractorLocation;
import Model.Department.DepartmentModel;
import Model.Location.LocationModel;
import Presenter.DepartmentPresenter;
import Presenter.LocationPresenter;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class AddLocationController {

    private DepartmentModel mDepartment;

    public AddLocationController() {
        mDepartment = DepartmentPresenter.get().getDepartmentModel();
    }

    @FXML
    private JFXComboBox<LocationModel> mComboBoxLocation;

    private LocationModel mLocation;

    private boolean mSelectedFlag;

    @FXML
    public void initialize() {
        mSelectedFlag=false;
        mComboBoxLocation.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(LocationModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getName());
                }else setText(null);
            }
        });
        mComboBoxLocation.setConverter(new StringConverter<LocationModel>() {
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
                System.out.println(mComboBoxLocation.getEditor().getText());
                mSelectedFlag=false;
            }
        });
        mComboBoxLocation.setItems(DepartmentPresenter.get().getObservableLocation());
        mComboBoxLocation.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedLocation()));
    }

    private void selectedLocation() {
        System.out.println("selected "+mComboBoxLocation.getSelectionModel().getSelectedIndex());
        if(mComboBoxLocation.getSelectionModel().getSelectedIndex()!=-1) {
            mSelectedFlag = true;
            mLocation = mComboBoxLocation.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void onClickAdd() {
        System.out.println(mSelectedFlag);
        if(mSelectedFlag){
            System.out.println(mLocation.getName());
            mLocation.addLocation(mDepartment);
            LocationPresenter.get().editLocation(mLocation);
        }else{
            System.out.println(mComboBoxLocation.getEditor().getText());
            LocationPresenter.get().addLocation(mComboBoxLocation.getEditor().getText(),mDepartment);
        }
    }
}
