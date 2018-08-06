package UI.Equipment.Controller;

import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentInventoryModel;
import Model.Equipment.EquipmentModel;
import Model.Equipment.EquipmentStateModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Parameter.ParameterModel;
import Model.State.StateModel;
import Model.Type.TypeModel;
import Presenter.EquipmentPresenter;
import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.Coordinator;
import UI.TabPane.Controller.TabPaneSecondLvlController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditEquipmentController implements IUpdateUI {

    private static EquipmentModel sEquipmentModel;

    public EditEquipmentController() {
        UpdateService.get().addListenerUI(this);
        sEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
    }

    @FXML
    private TextField textFieldName, textFieldNameFact;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private Button buttonConfig;

    @FXML
    private ComboBox<TypeModel> comboBoxType;

    @FXML
    private TableView<ParameterModel> tableViewParameter;

    @FXML
    private TableColumn<ParameterModel, String> columnNameType;

/*    @FXML
    private TableView<EquipmentInventoryModel> tableViewEquipment;

    @FXML
    private TableColumn<EquipmentInventoryModel, String> columnNumber, columnDepartment, columnState;*/

    @FXML
    private Label labelConfig;

    @FXML
    private TreeTableView<EquipmentInventoryModel> mTreeTableEquipmentInventory;

    @FXML
    private TreeTableColumn<EquipmentInventoryModel, String> mDepartmentEquipmentColumn, mNumberEquipmentColumn, mStateEquipmentColumn, mDescriptionEquipmentColumn;

    @FXML
    private AnchorPane anchorPaneEditEquipment;

    @FXML
    public void initialize() {
        columnNameType.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
/*        columnNumber.setCellValueFactory(cellData -> cellData.getValue().getInventoryNumber().nameProperty());
        columnState.setCellValueFactory(cellData -> cellData.getValue().getLastEntity().getStateModel().nameProperty());
        columnDepartment.setCellValueFactory(cellData -> cellData.getValue().getDepartmentList().nameProperty());
        tableViewEquipment.getSelectionModel().selectedItemProperty().addListenerUI((observable, oldValue, newValue) -> selectedEquipment(newValue));*/

        mDepartmentEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getDepartmentModel().nameProperty());
        mNumberEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getInventoryNumber().nameProperty());
        mStateEquipmentColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getLastEntity().getStateModel().nameProperty());
        mTreeTableEquipmentInventory.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedEquipment(newValue)));
    }

    private void selectedEquipment(TreeItem<EquipmentInventoryModel> treeEquipment) {
        if(treeEquipment!=null) {
            EquipmentInventoryModel equipment=treeEquipment.getValue();
            if (equipment != null && equipment.getId()!=-1) {
                EquipmentPresenter.get().setEquipmentInventoryModel(equipment);
                TabControllerService.get().getListenerThirdTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEquipmentInventoryResource()));
                UpdateService.get().updateUI(EquipmentInventoryModel.class);
            }
            //new Coordinator().goToEquipentInventoryWindow((Stage) anchorPaneEditEquipment.getScene().getWindow());
        }
    }

    @FXML
    private void onClickAddEquipmentInventory() {
        new Coordinator().goToAddEquipmentInventoryWindow((Stage) anchorPaneEditEquipment.getScene().getWindow(),100.0,200.0);
    }

    private void updateEuipmentTable(ObservableList<EquipmentInventoryModel> equipmentList) {
        TreeItem<EquipmentInventoryModel> rootItem = new TreeItem<>();
        boolean flag = false;
        for (EquipmentInventoryModel equipment : equipmentList) {
            for (TreeItem<EquipmentInventoryModel> treeEquipment : rootItem.getChildren()) {
                if (treeEquipment.getValue().getInventoryNumber().getId() == equipment.getInventoryNumber().getId()) {
                    treeEquipment.getChildren().add(new TreeItem<>(equipment));
                    flag = true;
                }
            }
            if (!flag) {
                EquipmentInventoryModel emptyEquipment = new EquipmentInventoryModel(
                        -1,
                        new InventoryNumberModel(equipment.getInventoryNumber().getId(), equipment.getInventoryNumber().getName()),
                        null,
                        new DepartmentModel(-1,""));
                emptyEquipment.getEntityList().add(new EquipmentStateModel(-1, new StateModel(-1, "")));
                TreeItem<EquipmentInventoryModel> treeItemFirst = new TreeItem<>(emptyEquipment);
                treeItemFirst.getChildren().add(new TreeItem<>(equipment));
                rootItem.getChildren().add(treeItemFirst);

            } else {
                flag = false;
            }
        }
        mTreeTableEquipmentInventory.setRoot(rootItem);
        mTreeTableEquipmentInventory.setShowRoot(false);
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(EquipmentModel.class.getName())) {
            sEquipmentModel = EquipmentPresenter.get().getEquipmentModel();
            sEquipmentModel.getEntityList(); // первое обращение в базу для загрузки оборудования
            textFieldName.setText(sEquipmentModel.getName());
            textFieldNameFact.setText(sEquipmentModel.getNameFact());
            textAreaDescription.setText(sEquipmentModel.getDescription());
            labelConfig.setText(sEquipmentModel.getConfig());
            //tableViewEquipment.setItems(sEquipmentModel.getObservableEqInventoryList());
            updateEuipmentTable(sEquipmentModel.getObservableEqInventoryList());
            UpdateService.get().updateUI(TabPaneSecondLvlController.class);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }

}
