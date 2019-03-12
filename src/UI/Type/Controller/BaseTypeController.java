package UI.Type.Controller;

import Model.Parameter.ParameterModel;
import Model.Parameter.SelectedParameterShell;
import Model.Type.TypeModel;
import Presenter.ParameterPresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BaseTypeController extends BaseController {

    private TreeTableView<ParameterModel> mTreeTableViewParameters;
    private TreeTableColumn<ParameterModel, String> mNameColumn;
    private ObservableList<ParameterModel> mParametersList;
    private StackPane mDialogPane;
    private TableView<SelectedParameterShell> mTableViewParameterDialog;
    private JFXDialog mJFXDialog;
    private BaseValidator mBaseValidator = new BaseValidator();
    private TypeModel mTypeModel;

    public void setTreeTableViewParameters(TreeTableView<ParameterModel> treeTableViewParameters) {
        mTreeTableViewParameters = treeTableViewParameters;
    }

    public void setNameColumn(TreeTableColumn<ParameterModel, String> nameColumn) {
        mNameColumn = nameColumn;
    }

    public void setParametersList(ObservableList<ParameterModel> parametersList) {
        mParametersList = parametersList;
    }

    public void setDialogPane(StackPane dialogPane) {
        mDialogPane = dialogPane;
    }

    public BaseValidator getBaseValidator() {
        return mBaseValidator;
    }

    protected void initTextField(JFXTextField textField) {
        mBaseValidator.setJFXTextFields(textField);
        initPromptText(textField, "Введите тип оборудования", "Тип оборудования");
    }

    protected void updateTableView(ObservableList<ParameterModel> parametersList) {
        TreeItem<ParameterModel> rootItem = new TreeItem<>();
        if (parametersList != null) {
            for (ParameterModel parameter : parametersList) {
                rootItem.getChildren().add(new TreeItem<>(parameter));
            }
        }
        rootItem.getChildren().add(new TreeItem<>(new ParameterModel(-1, "Добавить параметр")));
        mTreeTableViewParameters.setRoot(rootItem);
        mTreeTableViewParameters.setShowRoot(false);
        mTreeTableViewParameters.setFixedCellSize(50);
        mTreeTableViewParameters.prefHeightProperty().bind(Bindings.size(mTreeTableViewParameters.getRoot().getChildren())
                .multiply(mTreeTableViewParameters.getFixedCellSize()).add(55));
    }

    protected void initTableView() {
        mNameColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        mTreeTableViewParameters.setTooltip(new Tooltip("ПКМ для удаления"));
        mTreeTableViewParameters.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedParameter(newValue)));
        mTreeTableViewParameters.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    ParameterModel parameter = mTreeTableViewParameters.getSelectionModel().getSelectedItem().getValue();
                    if (parameter.getId() != -1) {
                        mParametersList.remove(parameter);
                        if (editType(mParametersList)) {
                            updateTableView(mParametersList);
                            resizeHeightStage(mTreeTableViewParameters.getPrefHeight());
                        }
                    }
                }
            }
        });
    }

    private void selectedParameter(TreeItem<ParameterModel> newValue) {
        if (newValue != null && newValue.getValue().getId() == -1) {
            showDialog();
        }
    }

    private void showDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Выберите параметр"));
        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);
        content.setBody(initDialogTableView());
        content.setActions(button);
        mJFXDialog = new JFXDialog(mDialogPane, content, JFXDialog.DialogTransition.CENTER);
        mJFXDialog.show();
        mJFXDialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                mTreeTableViewParameters.getSelectionModel().clearSelection();
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (SelectedParameterShell equipment : mTableViewParameterDialog.getItems()) {
                    if (equipment.isChosen()) {
                        mParametersList.add(equipment);
                    }
                }
                if (editType(mParametersList)) {
                    updateTableView(mParametersList);
                    if (mTreeTableViewParameters.getPrefHeight() <= mTreeTableViewParameters.getMaxHeight()) {
                        resizeHeightStage(mTreeTableViewParameters.getPrefHeight());
                    } else {
                        resizeHeightStage(mTreeTableViewParameters.getMaxHeight());
                    }
                }
                mJFXDialog.close();
            }
        });
    }

    protected boolean editType(ObservableList<ParameterModel> parametersList) {
        return true;
    }

    private TableView<SelectedParameterShell> initDialogTableView() {
        mTableViewParameterDialog = new TableView<>();
        TableColumn<SelectedParameterShell, Boolean> checkColumn = new TableColumn<>();
        checkColumn.setMinWidth(40);
        mTableViewParameterDialog.getColumns().add(checkColumn);

        TableColumn<SelectedParameterShell, String> nameColumn = new TableColumn<>("Параметер");
        nameColumn.setMinWidth(150);
        mTableViewParameterDialog.getColumns().add(nameColumn);
        checkColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        checkColumn.setCellValueFactory(cellData -> {
            SelectedParameterShell entity = cellData.getValue();
            BooleanProperty property = entity.chosenProperty();
            property.addListener(((observable, oldValue, newValue) -> entity.setChosen(newValue)));
            return property;
        });
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mTableViewParameterDialog.setItems(ParameterPresenter.get().getParameterList(mParametersList));
        mTableViewParameterDialog.setMaxWidth(210);
        mTableViewParameterDialog.setMaxHeight(200);
        mTableViewParameterDialog.setEditable(true);
        return mTableViewParameterDialog;
    }

    protected void resizeHeightStage(double prefHeight) {
    }

    public TypeModel getTypeModel() {
        return mTypeModel;
    }

    public void setTypeModel(TypeModel typeModel) {
        mTypeModel = typeModel;
    }

    @Override
    protected Stage getStage() {
        return null;
    }

}
