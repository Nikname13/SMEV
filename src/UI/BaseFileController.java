package UI;

import Model.AbstractModel;
import Model.FileDumpModel;
import Presenter.FileDumpPresenter;
import Service.IOnMouseClick;
import UI.Popup.Controller.BasePopup;
import UI.Validator.BaseValidator;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    protected ImageView createImageView(FileDumpModel file) {
        ImageView imageView = null;
        File imageFile = getTempFile(file.getPath());
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 150, true,
                    true);
            double h=image.getHeight();
            double w=image.getWidth();
            if(h!=0.0 && w!=0.0) {
                imageView = new ImageView(image);
                imageView.setFitWidth(150);
                //imageView.setFitHeight(150);
                imageView.setId("imageViewGallery");
                imageView.focusedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) System.out.println("image focus");
                    }
                });
                new BasePopup(imageView, BasePopup.getBaseListPopup(), this, true);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                            if (mouseEvent.getClickCount() == 2) {
                                try {
                                    Desktop desktop = null;
                                    if (Desktop.isDesktopSupported()) {
                                        desktop = Desktop.getDesktop();
                                    }
                                    desktop.open(imageFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }else{
                return null;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }

    protected void openImage() {

    }

    @Override
    public void createGallery() {
        if (mTypeDocument.equals(AbstractModel.getTypePhoto())) {
            javafx.scene.control.ScrollPane root = new javafx.scene.control.ScrollPane();
            TilePane tile = new TilePane();
            tile.setPadding(new Insets(15, 15, 15, 15));
            tile.setHgap(15);
            tile.setVgap(15);
            root.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
            root.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
            root.setFitToWidth(true);
            root.setContent(tile);

            JFXButton addButton=new JFXButton();
            addButton.setPrefHeight(150);
            addButton.setPrefWidth(150);
            //addButton.setStyle("-fx-background-color: #a8f1b3;");
            //addButton.setRipplerFill(Color.web("#40a85f"));
            tile.getChildren().add(addButton);
            addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.print("click click click");
                }
            });
            for (FileDumpModel file : getFileList()) {
                ImageView imageView;
                FileDumpPresenter.get().setFileDumpModel(file);
                imageView = createImageView(file);
                if(imageView!=null) {
                    Pane pane = new Pane();
                    VBox vBox = new VBox();
                    vBox.getChildren().add(imageView);
                    javafx.scene.control.Label label = new Label(file.getName());
                    label.setMaxWidth(150);
                    label.setWrapText(true);
                    vBox.getChildren().add(label);
                    pane.getChildren().add(vBox);
                    new BasePopup(pane, BasePopup.getBaseListPopup(), this);
                    //pane.setStyle("-fx-background-color: #ffffff;");
                    tile.getChildren().addAll(pane);
                }
            }
            getStage().setWidth(600);
            getStage().setHeight(400);
            Scene scene = new Scene(root);
            getStage().setScene(scene);
            getStage().show();
        }
    }

    protected File getTempFile(String path) {
        return null;
    }

    protected ObservableList<FileDumpModel> getFileList() {
        return null;
    }
    protected void editFile(String name) {

    }
}
