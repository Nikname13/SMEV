package UI;

import Model.FileDumpModel;
import Presenter.FileDumpPresenter;
import Service.IOnMouseClick;
import UI.Popup.Controller.BasePopup;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public abstract class BaseFileController extends BaseController implements IOnMouseClick {

    private BaseValidator mBaseValidatorDialog = new BaseValidator();
    private String mTypeDocument;

    public String getTypeDocument() {
        return mTypeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        mTypeDocument = typeDocument;
    }

    protected void initPopup(JFXListView list) {
        new BasePopup(list, BasePopup.getFileDumpListPopup(), this, true);
    }

    protected void initListView(JFXListView<FileDumpModel> list) {
        list.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(FileDumpModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        list.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedFile(newValue)));
    }

    private void selectedFile(FileDumpModel newValue) {
        if (newValue != null) {
            FileDumpPresenter.get().setFileDumpModel(newValue);
        } else {
            FileDumpPresenter.get().setFileDumpModel(null);
        }
    }

    protected void createDialogEditFile(StackPane stackPane) {
        String oldFileName = FileDumpPresenter.get().getFileDumpModel().getName();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Редактирование"));

        JFXTextField text = new JFXTextField();
        text.setText(oldFileName.substring(0, oldFileName.lastIndexOf(".")));
        text.setPrefWidth(200);
        text.setLabelFloat(true);
        text.setPromptText("Наименование файла");
        text.setFocusColor(Paint.valueOf("#40a85f"));
        initPromptText(text, "Введите наименование файла", "Наименование файла");
        mBaseValidatorDialog.setJFXTextFields(text);

        JFXButton button = new JFXButton("Сохранить");
        button.setRipplerFill(Paint.valueOf("#40a85f"));
        button.setPrefHeight(35.0);

        content.setBody(text);
        content.setActions(button);
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mBaseValidatorDialog.validate()) {
                    editFile(text.getText());
                    dialog.close();
                }
            }
        });
        dialog.show();
    }


    protected void editFile(String name) {

    }
}
