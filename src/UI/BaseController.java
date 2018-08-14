package UI;

import Model.Area.AreaModel;
import Model.Department.DepartmentModel;
import Model.Location.LocationModel;
import Model.Post.PostModel;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class BaseController {

    private boolean mSelectedLocation;
    private boolean mSelectedPost;

    public boolean isSelectedLocation() {
        return mSelectedLocation;
    }

    public void setSelectedLocation(boolean selectedLocation) {
        mSelectedLocation = selectedLocation;
    }

    protected void initComboBoxLocation(JFXComboBox<LocationModel> comboBoxLocation) {

        comboBoxLocation.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(LocationModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBoxLocation.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocationModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public LocationModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new LocationModel(-1, string.trim());
                else {
                    comboBoxLocation.getEditor().clear();
                    return null;
                }
            }
        });
        comboBoxLocation.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mSelectedLocation = false;
            }
        });

    }

    protected void initComboBoxArea(JFXComboBox<AreaModel> comboBoxArea) {
        comboBoxArea.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(AreaModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBoxArea.setConverter(new StringConverter<>() {
            @Override
            public String toString(AreaModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public AreaModel fromString(String string) {
                if (!string.isEmpty()) return new AreaModel(-1, string.trim());
                return null;
            }
        });

    }

    public boolean isSelectedPost() {
        return mSelectedPost;
    }

    public void setSelectedPost(boolean selectedPost) {
        mSelectedPost = selectedPost;
    }

    protected void initComboBoxDepartment(JFXComboBox<DepartmentModel> comboBoxDepartment) {
        comboBoxDepartment.setCellFactory(p -> new ListCell<DepartmentModel>() {
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
    }

    protected void initComboBoxPost(JFXComboBox<PostModel> comboBoxPost) {

        comboBoxPost.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(PostModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
        comboBoxPost.setConverter(new StringConverter<PostModel>() {
            @Override
            public String toString(PostModel object) {
                if (object != null)
                    return object.getName();
                else return null;
            }

            @Override
            public PostModel fromString(String string) {
                if (!string.trim().isEmpty()) {
                    return new PostModel(0, string.trim());
                } else {
                    return null;
                }
            }
        });
        comboBoxPost.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                mSelectedPost = false;
            }
        });
    }

    protected void close(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}

