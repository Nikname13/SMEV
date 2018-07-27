package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Model.Location.LocationModel;
import Presenter.DepartmentPresenter;
import Presenter.LocationPresenter;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.ValidationFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AddLocationController {

    private DepartmentModel mDepartment;
    private BaseValidator mBaseValidator = new BaseValidator();

    public AddLocationController() {
        mDepartment = DepartmentPresenter.get().getDepartmentModel();
    }

    @FXML
    private ValidationFacade mFacadeLocation;
    @FXML
    private JFXComboBox<LocationModel> mComboBoxLocation;
    @FXML
    private Label mErrorLocation;
    @FXML
    private AnchorPane mAnchorPaneLocation;

    private LocationModel mLocation;

    private boolean mSelectedFlag;

    @FXML
    public void initialize() {
        mSelectedFlag = false;
        mBaseValidator.setValidationFacades(new Pair(mFacadeLocation, mErrorLocation));
        mComboBoxLocation.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(LocationModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        mComboBoxLocation.setConverter(new StringConverter<LocationModel>() {
            @Override
            public String toString(LocationModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public LocationModel fromString(String string) {
                if (!string.trim().isEmpty()) {
                    return new LocationModel(-1, string.trim());
                }
                else {
                    mComboBoxLocation.getEditor().clear();
                    return null;
                }
            }
        });
        mComboBoxLocation.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mSelectedFlag = false;
            }
        });
        mComboBoxLocation.setItems(DepartmentPresenter.get().getObservableLocation());
        mComboBoxLocation.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedLocation()));
    }

    private void selectedLocation() {
        System.out.println("selected " + mComboBoxLocation.getSelectionModel().getSelectedIndex());
        if (mComboBoxLocation.getSelectionModel().getSelectedIndex() != -1) {
            mSelectedFlag = true;
            mLocation = mComboBoxLocation.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void onClickAdd() {
        System.out.println(mSelectedFlag);
        if (mBaseValidator.validate()) {
            if (mSelectedFlag) {
                System.out.println(mLocation.getName());
                mLocation.addLocation(mDepartment);
                LocationPresenter.get().editLocation(mLocation);
            } else {
                System.out.println(mComboBoxLocation.getEditor().getText());
                LocationPresenter.get().addLocation(mComboBoxLocation.getEditor().getText(), mDepartment);
            }
            close();
        }
    }

    @FXML
    private void onClickCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) mAnchorPaneLocation.getScene().getWindow();
        stage.close();
    }
}
