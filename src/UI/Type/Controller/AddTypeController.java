package UI.Type.Controller;

import Model.Parameter.ParameterModel;
import Model.Parameter.SelectedParameterShell;
import Presenter.TypePresenter;
import UI.BaseController;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddTypeController extends BaseController {

    private ObservableList<ParameterModel> mParametersList;
    private TableView<SelectedParameterShell> mPrameterDialog;
    private BaseValidator mBaseValidator=new BaseValidator();
    private JFXDialog mJFXDialog;

    @FXML
    private TreeTableView<ParameterModel> mTreeTableViewParameters;

    @FXML
    private TreeTableColumn<ParameterModel,String> mNameColumn;

    @FXML
    private JFXTextField mNameType;

    @FXML
    private StackPane mStackPaneAddType;

    @FXML
    public void initialize(){
        mBaseValidator.setJFXTextFields(mNameType);
        mParametersList=FXCollections.observableArrayList();
        initTextField(mNameType, "Введите тип оборудования", "Тип оборудования");
        initTableView();
        updateTableView(mParametersList);
    }

    private void updateTableView(ObservableList<ParameterModel> parametersList) {
        TreeItem<ParameterModel> rootItem=new TreeItem<>();
        if(parametersList != null){
            for(ParameterModel parameter : parametersList){
                rootItem.getChildren().add(new TreeItem<>(parameter));
            }
        }
        rootItem.getChildren().add(new TreeItem<>(new ParameterModel(-1,"Добавить параметр")));
        mTreeTableViewParameters.setRoot(rootItem);
        mTreeTableViewParameters.setShowRoot(false);
        mTreeTableViewParameters.setFixedCellSize(50);
        mTreeTableViewParameters.prefHeightProperty().bind(Bindings.size(mTreeTableViewParameters.getRoot().getChildren())
                .multiply(mTreeTableViewParameters.getFixedCellSize()).add(55));
    }

    private void initTableView() {
        mNameColumn.setCellValueFactory(cellData->cellData.getValue().getValue().nameProperty());
        mTreeTableViewParameters.setTooltip(new Tooltip("ПКМ для удаления"));
        mTreeTableViewParameters.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedParameter(newValue)));
    }

    private void selectedParameter(TreeItem<ParameterModel> newValue) {
        if(newValue!=null){
            if(newValue.getValue().getId()==-1){
                showDialog();
            }
        }
    }

    private void showDialog(){
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Выберите параметр"));
        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);
        content.setBody(initDialogTableView());
        content.setActions(button);
        mJFXDialog = new JFXDialog(mStackPaneAddType, content, JFXDialog.DialogTransition.CENTER);
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
                for (SelectedParameterShell equipment : mPrameterDialog.getItems()) {
                    if (equipment.isChosen()) {
                        mParametersList.add(equipment);
                    }
                }
                updateTableView(mParametersList);
                if (mTreeTableViewParameters.getPrefHeight() <= mTreeTableViewParameters.getMaxHeight()) {
                    resizeHeightStage(mTreeTableViewParameters.getPrefHeight());
                } else {
                    resizeHeightStage(mTreeTableViewParameters.getMaxHeight());
                }
                mJFXDialog.close();
            }
        });
    }

    private void resizeHeightStage(double prefHeight) {
    }

    private TableView<SelectedParameterShell> initDialogTableView() {
    }

    @FXML
    private void onClickAdd(){
        if(mBaseValidator.validate()){

            close(mStackPaneAddType);
        }
    }

    @FXML
    private void onClickCancel(){
        close(mStackPaneAddType);
    }


    @Override
    protected Stage getStage() {
        return null;
    }
}
