package UI.Equipment.Equipment_inventory.Equipment_move.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Worker.WorkerModel;
import Presenter.EquipmentPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import UI.Validator.Pair;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.ValidationFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class MoveEquipmentInventoryController extends BaseController {

    private EquipmentInventoryModel mEquipment;
    private DepartmentModel mDepartment;
    private BaseValidator mBaseValidator = new BaseValidator();

    @FXML
    private JFXTextField mTextFieldDepartment;
    @FXML
    private JFXComboBox<DepartmentModel> mComboBoxDepartment;
    @FXML
    private JFXComboBox<WorkerModel> mComboBoxWorkerTo, mComboBoxWorkerFrom;
    @FXML
    private JFXTextArea mTextAreaBase;
    @FXML
    private ValidationFacade mFacadeWorkerFrom, mFacadeWorkerTo, mFacadeDepartmentTo;
    @FXML
    private Label mErrorWorkerFrom, mErrorWorkerTo, mErrorDepartmentTo;
    @FXML
    private AnchorPane mAnchorPaneMoveEquipment;

    public MoveEquipmentInventoryController() {
        mEquipment = EquipmentPresenter.get().getEquipmentInventoryModel();
        mDepartment = EquipmentPresenter.get().getDepartmentModel();
    }

    @FXML
    public void initialize() {
        mBaseValidator.setValidationFacades(
                new Pair(mFacadeWorkerFrom, mErrorWorkerFrom),
                new Pair(mFacadeWorkerTo, mErrorWorkerTo),
                new Pair(mFacadeDepartmentTo, mErrorDepartmentTo));
        mBaseValidator.setJFXTextAreas(mTextAreaBase);
        mTextFieldDepartment.setText(mEquipment.getDepartmentModel().getName());
        mComboBoxDepartment.setCellFactory(p -> new ListCell<DepartmentModel>() {
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        mComboBoxDepartment.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
                this.setVisible(!empty);
            }
        });
        mComboBoxDepartment.setConverter(new StringConverter<DepartmentModel>() {
            @Override
            public String toString(DepartmentModel object) {
                if (object != null) return object.getName();
                return null;
            }

            @Override
            public DepartmentModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new DepartmentModel(-1, string);
                return null;
            }
        });
        mComboBoxDepartment.setItems(EquipmentPresenter.get().getObservableDepartment());
        mComboBoxDepartment.getSelectionModel().select(EquipmentPresenter.get().getDepartmentModel());
        mComboBoxDepartment.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment(newValue)));
        mComboBoxWorkerFrom.setCellFactory((p -> new ListCell<WorkerModel>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        }));
        mComboBoxWorkerTo.setCellFactory(p -> new ListCell<WorkerModel>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        mComboBoxWorkerTo.setConverter(new StringConverter<WorkerModel>() {
            @Override
            public String toString(WorkerModel object) {
                if (object != null) object.getName();
                return null;
            }

            @Override
            public WorkerModel fromString(String string) {
                if (!string.trim().isEmpty()) return new WorkerModel(-1, string);
                return null;
            }
        });
        setButtonCellWorkerTo();
        mComboBoxWorkerTo.setItems(mDepartment.getObsWorkerList());
        mComboBoxWorkerTo.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            mBaseValidator.validateFacade(mFacadeWorkerTo);
        }));
        setWorkerTo();
        mComboBoxWorkerFrom.setCellFactory(p -> new ListCell<WorkerModel>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }

            }
        });
        mComboBoxWorkerFrom.setConverter(new StringConverter<WorkerModel>() {
            @Override
            public String toString(WorkerModel object) {
                if (object != null) object.getName();
                return null;
            }

            @Override
            public WorkerModel fromString(String string) {
                if (!string.trim().isEmpty()) return new WorkerModel(-1, string);
                return null;
            }
        });
        mComboBoxWorkerFrom.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
                this.setVisible(!empty);
            }
        });

        mComboBoxWorkerFrom.setItems(mEquipment.getDepartmentModel().getObsWorkerList());
        mComboBoxWorkerFrom.getSelectionModel().selectFirst();
    }

    private void setWorkerTo() {
        mComboBoxWorkerTo.getSelectionModel().selectFirst();

    }

    private void setButtonCellWorkerTo() {
        mComboBoxWorkerTo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                System.out.println("item= " + item + " empty= " + empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
                this.setVisible(!empty);
            }
        });
    }

    private void selectedDepartment(DepartmentModel department) {
        System.out.println("select department");
        mComboBoxWorkerTo.setItems(department.getObsWorkerList());
        mComboBoxWorkerTo.getEditor().requestFocus();
        setButtonCellWorkerTo();
        setWorkerTo();
    }

    @FXML
    private void onClickMove() {
        if (mBaseValidator.validate()) {
            EquipmentPresenter.get().moveEquipmentInventory(
                    mEquipment,
                    mDepartment,
                    mComboBoxWorkerFrom.getValue(),
                    mComboBoxWorkerTo.getValue(),
                    mTextAreaBase.getText());
            close(mAnchorPaneMoveEquipment);
        }
    }

    @FXML
    private void onClickCancel() {
        EquipmentPresenter.get().cancel();
        close(mAnchorPaneMoveEquipment);
    }

    @Override
    protected Stage getStage() {
        return null;
    }
}
