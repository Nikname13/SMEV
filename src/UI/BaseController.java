package UI;

import Model.AbstractModel;
import Service.ErrorService;
import Service.IUpdateUI;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class BaseController implements IUpdateUI {

    private static StackPane sParentStackPane;

    protected static StackPane getParentStackPane() {
        return sParentStackPane;
    }

    protected static void setParentStackPane(StackPane parentStackPane) {
        sParentStackPane = parentStackPane;
    }

    protected abstract Stage getStage();

    protected <T extends AbstractModel> JFXComboBox initJFXComboBox(T entity, JFXComboBox<T> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(T object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public T fromString(String string) {
                if (!string.isEmpty()) return (T) entity.create(string);
                else {
                    comboBox.getEditor().clear();
                    return null;
                }
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
        return comboBox;
    }

    protected void initPromptText(TextInputControl control , String promptText, String label){
        control.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    control.setPromptText(label);
                }else{
                    if(control.getText().trim().isEmpty()) {
                        control.setPromptText(promptText);
                    }else{
                        control.setPromptText(label);
                    }
                }
            }
        });
    }

    private void initPromptText(JFXComboBox control , String promptText, String label){
        control.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    control.setPromptText(label);
                }else{
                    if (control.getSelectionModel().isEmpty() && control.getEditor().getText().trim().isEmpty()) {
                        control.setPromptText(promptText);
                    }else{
                        control.setPromptText(label);
                    }
                }
            }
        });
    }

    protected void setAvatar(String path, BorderPane imageContainer) {
        try {
            setImage(new Image(new FileInputStream(path), imageContainer.getMinWidth(), imageContainer.getHeight(), true,
                    true), imageContainer);
        } catch (FileNotFoundException ex) {
            System.out.println("path is empty");
            setImage(new Image("/Resource/not_photo.jpg"), imageContainer);
        }
    }

    private void setImage(Image image, BorderPane imageContainer) {
        ImageView imageView = new ImageView(image);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
        imageContainer.setCenter(imageView);

    }

    protected void close(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        ErrorService.get().overrideError("updateUI", this.getClass());
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        ErrorService.get().overrideError("refreshControl", this.getClass());
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        ErrorService.get().overrideError("updateControl", this.getClass());
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {
        ErrorService.get().overrideError("updateControl", this.getClass());
    }

    protected void onlyNumber(TextInputControl textControl, int maxNumber) {
        textControl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                if (!newValue.isEmpty()) {
                    if (!newValue.matches("^[1-9]+([0-9]+$)?")) {
                        textControl.setText(oldValue);
                        return;
                    }
                    int number = Integer.parseInt(newValue);
                    if (number <= maxNumber) {
                        return;
                    }
                    textControl.setText(oldValue);
                }
            }
        });
    }


    public void destroy() {
        ErrorService.get().overrideError("destroy", this.getClass());
    }

    public void createGallery() {

    }
}

