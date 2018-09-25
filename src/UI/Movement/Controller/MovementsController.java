package UI.Movement.Controller;

import Model.Movement.MovementModel;
import Presenter.MovementPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.format.TextStyle;
import java.util.Locale;

public class MovementsController extends BaseController implements IUpdateUI {

    @FXML
    private TreeTableView<MovementModel> mTreeTableMovement;

    @FXML
    private TreeTableColumn<MovementModel, String> mDateColumn, mBaseColumn;

    @FXML
    private StackPane mStackPaneMovement;

    public MovementsController() {
        ListenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        initTreeTable();
    }

    private void initTreeTable() {
        mDateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().dateToString());
        mBaseColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mTreeTableMovement.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedMovement(newValue)));
    }

    private void selectedMovement(TreeItem<MovementModel> movementItem) {
        if (movementItem != null) {
            MovementModel movement = movementItem.getValue();
            if (movement != null && movement.getId() != -1) {
                MovementPresenter.get().setMovementModel(movement);
                MovementPresenter.get().setSelectedObject(movement);
                MovementPresenter.get().loadEntity(movement.getId());
                TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getDetailsMovementResource()));
                ListenersService.get().updateUI(MovementModel.class);
            }
        }
    }


    private void updateTreeTable() {
        TreeItem<MovementModel> rootItem = new TreeItem<>();
        boolean yearFlag = false;
        boolean monthFlag = false;
        for (MovementModel movementModel : MovementPresenter.get().getObservableMovement()) {
            for (TreeItem<MovementModel> rootYearItem : rootItem.getChildren()) {
                if (rootYearItem.getValue().getDate().getYear() == movementModel.getDate().getYear()) {
                    for (TreeItem<MovementModel> monthItem : rootYearItem.getChildren()) {
                        if (monthItem.getValue().getDate().getMonth().getValue() == movementModel.getDate().getMonth().getValue()) {
                            monthFlag = true;
                            monthItem.getChildren().add(new TreeItem<>(movementModel));
                            break;
                        }
                    }
                    if (!monthFlag) {
                        TreeItem<MovementModel> treeItemSecond = new TreeItem<>(getEmptyMonthItem(movementModel));
                        treeItemSecond.getChildren().add(new TreeItem<>(movementModel));
                        rootYearItem.getChildren().add(treeItemSecond);
                    } else {
                        monthFlag = false;
                    }
                    yearFlag = true;
                    break;
                }
            }
            if (!yearFlag) {
                TreeItem<MovementModel> treeItemFirst = new TreeItem<>(getEmptyRootEquipment(movementModel));
                TreeItem<MovementModel> treeItemSecond = new TreeItem<>(getEmptyMonthItem(movementModel));
                treeItemSecond.getChildren().add(new TreeItem<>(movementModel));
                treeItemFirst.getChildren().add(treeItemSecond);
                rootItem.getChildren().add(treeItemFirst);

            } else {
                yearFlag = false;
            }
        }
        mTreeTableMovement.setRoot(rootItem);
        mTreeTableMovement.setShowRoot(false);
    }

    private MovementModel getEmptyRootEquipment(MovementModel movementModel) {
        MovementModel movement = new MovementModel(-1, movementModel.getDate(), "");
        movement.setDisplayDate(movementModel.getDate().getYear() + " год");
        return movement;
    }

    private MovementModel getEmptyMonthItem(MovementModel movementModel) {
        MovementModel movement = new MovementModel(-1, movementModel.getDate(), "");
        movement.setDisplayDate(movementModel.getDate().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru")));
        return movement;
    }

    @FXML
    private void onClickAdd() {
        new Coordinator().goToAddMovementWindow(getStage());
    }

    @Override
    public void updateUI(Class<?> updateClass) {

    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(MovementModel.class.getName())) {
            updateTreeTable();
        }

    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    protected Stage getStage() {
        return (Stage) mStackPaneMovement.getScene().getWindow();
    }
}
