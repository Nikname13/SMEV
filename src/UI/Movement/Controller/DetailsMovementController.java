package UI.Movement.Controller;

import Model.Movement.MovementEquipment;
import Model.Movement.MovementModel;
import Presenter.MovementPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

import java.util.List;

public class DetailsMovementController extends BaseController implements IUpdateUI {

    private MovementModel mMovementModel;
    @FXML
    private JFXTextField mTextFieldDepartmentFrom, mTextFieldDepartmentTo, mTextFieldWorkerFrom, mTextFieldWorkerTo;
    @FXML
    private JFXTextArea mTextAreaBase;
    @FXML
    private TreeTableView<MovementEquipment> mTreeTableEquipment;
    @FXML
    private TreeTableColumn<MovementEquipment, String> mNameColumn, mTypeColumn, mInventoryNumberColumn;

    public DetailsMovementController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        initTable();
    }

    private void initTable() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().typeProperty());
        mInventoryNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().inventoryNumberProperty());
    }

    @Override
    protected Stage getStage() {
        return null;
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(MovementModel.class.getName())) {
            mMovementModel = MovementPresenter.get().getMovementModel();
            mTextFieldDepartmentFrom.setText(mMovementModel.getDepartmentList().get(0).getName());
            mTextFieldDepartmentTo.setText(mMovementModel.getDepartmentList().get(1).getName());
            mTextFieldWorkerFrom.setText(mMovementModel.getWorkerList().get(0).getName());
            mTextFieldWorkerTo.setText(mMovementModel.getWorkerList().get(1).getName());
            mTextAreaBase.setText(mMovementModel.getName());
            updateTable(mMovementModel.getEntityList());
        }
    }

    private void updateTable(List<MovementEquipment> entityList) {
        TreeItem<MovementEquipment> rootItem = new TreeItem<>();
        for (MovementEquipment equipment : entityList) {
            rootItem.getChildren().add(new TreeItem<>(equipment));
        }
        mTreeTableEquipment.setRoot(rootItem);
        mTreeTableEquipment.setShowRoot(false);
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }
}
