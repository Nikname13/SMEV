package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Model.Location.LocationModel;
import Presenter.DepartmentPresenter;
import Presenter.LocationPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddLocationController extends BaseController {

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


    @FXML
    public void initialize() {
        mBaseValidator.setValidationFacades(new Pair(mFacadeLocation, mErrorLocation, mComboBoxLocation));
        initComboBoxLocation(mComboBoxLocation);
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    @Override
    protected void initComboBoxLocation(JFXComboBox<LocationModel> comboBoxLocation) {
        super.initComboBoxLocation(comboBoxLocation);
        comboBoxLocation.setItems(DepartmentPresenter.get().getObservableLocation());
        mComboBoxLocation.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> selectedLocation()));
    }

    private void selectedLocation() {
        if (mComboBoxLocation.getSelectionModel().getSelectedIndex() != -1) {
            setSelectedLocation(true);
            mLocation = mComboBoxLocation.getValue();
        }
    }

    @FXML
    private void onClickAdd() {
        if (mBaseValidator.validate()) {
            if (isSelectedLocation()) {
                mLocation.addLocation(mDepartment);
                LocationPresenter.get().editLocation(mLocation);
            } else {
                System.out.println(mComboBoxLocation.getEditor().getText());
                LocationPresenter.get().addLocation(mComboBoxLocation.getEditor().getText(), mDepartment);
            }
            close(mAnchorPaneLocation);
        }
    }

    @FXML
    private void onClickCancel() {
        close(mAnchorPaneLocation);
    }

}
