package UI.Movement.Controller;

import Model.Movement.MovementModel;
import Presenter.MovementPresenter;
import Service.IUpdateUI;
import Service.LisenersService;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.time.format.TextStyle;
import java.util.Locale;

public class MovementsController implements IUpdateUI {

    @FXML
    private TreeTableView<MovementModel> mTreeTableMovement;

    @FXML
    private TreeTableColumn<MovementModel, String> mDateColumn, mBaseColumn;

    public MovementsController() {
        LisenersService.get().addListenerUI(this);
    }

    @FXML
    public void initialize() {
        initTreeTable();
    }

    private void initTreeTable() {
        mDateColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().dateToString());
        mBaseColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        // updateTreeTable();
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
}
