package UI;

import Model.Area.AreaModel;
import Model.Department.DepartmentModel;
import Model.Equipment.EquipmentParameterModel;
import Model.Inventory_number.InventoryNumberModel;
import Model.Location.LocationModel;
import Model.Parameter.ParameterModel;
import Model.Post.PostModel;
import Model.Provider.ProviderModel;
import Model.State.StateModel;
import Model.Supply.SupplyModel;
import Model.Type.TypeModel;
import Model.Worker.WorkerModel;
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

    private boolean mSelectedLocation;
    private boolean mSelectedPost;
    private static StackPane sParentStackPane;

    protected static StackPane getParentStackPane() {
        return sParentStackPane;
    }

    protected static void setParentStackPane(StackPane parentStackPane) {
        sParentStackPane = parentStackPane;
    }

    protected abstract Stage getStage();

    protected boolean isSelectedLocation() {
        return mSelectedLocation;
    }

    protected void setSelectedLocation(boolean selectedLocation) {
        mSelectedLocation = selectedLocation;
    }

    protected void initComboBoxLocation(JFXComboBox<LocationModel> comboBoxLocation,String promptText, String label) {
        initPromptText(comboBoxLocation,promptText,label);
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

    protected void initComboBoxArea(JFXComboBox<AreaModel> comboBoxArea, boolean isSelectionItem, String promptText, String label ) {
        initPromptText(comboBoxArea,promptText,label);
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
        if (isSelectionItem) {
            comboBoxArea.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(AreaModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
    }

    protected boolean isSelectedPost() {
        return mSelectedPost;
    }

    protected void setSelectedPost(boolean selectedPost) {
        mSelectedPost = selectedPost;
    }

    protected void initComboBoxDepartment(JFXComboBox<DepartmentModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<DepartmentModel>() {
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
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(DepartmentModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public DepartmentModel fromString(String string) {
                if (!string.isEmpty()) return new DepartmentModel(-1, string.trim());
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(DepartmentModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
    }

    protected void initComboBoxPost(JFXComboBox<PostModel> comboBoxPost, String promptText, String label) {
        initPromptText(comboBoxPost,promptText,label);
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

    protected void initComboBoxType(JFXComboBox<TypeModel> comboBoxType, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBoxType,promptText,label);
        comboBoxType.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(TypeModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBoxType.setConverter(new StringConverter<>() {
            @Override
            public String toString(TypeModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public TypeModel fromString(String string) {
                if (!string.isEmpty()) return new TypeModel(-1, string.trim());
                return null;
            }
        });
        if (isSelectionItem) {
            comboBoxType.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(TypeModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
    }

    protected JFXComboBox initComboBoxParameter(JFXComboBox<ParameterModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(ParameterModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ParameterModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public ParameterModel fromString(String string) {
                if (!string.isEmpty()) return new ParameterModel(-1, string.trim());
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(ParameterModel item, boolean empty) {
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

    protected JFXComboBox initComboBoxEquipmentParameter(JFXComboBox<EquipmentParameterModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(EquipmentParameterModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getParameterModel().getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(EquipmentParameterModel object) {
                if (object != null) return object.getParameterModel().getName();
                else return null;
            }

            @Override
            public EquipmentParameterModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new EquipmentParameterModel(-1, "", new ParameterModel(-1, string.trim()));
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(EquipmentParameterModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getParameterModel().getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
        return comboBox;
    }

    protected void initComboBoxNumber(JFXComboBox<InventoryNumberModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(InventoryNumberModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(InventoryNumberModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public InventoryNumberModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new InventoryNumberModel(-1, "");
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(InventoryNumberModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
    }

    protected void initComboBoxState(JFXComboBox<StateModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(StateModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(StateModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public StateModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new StateModel(-1, "");
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(StateModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
    }

    protected void initComboBoxWorker(JFXComboBox<WorkerModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(WorkerModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(WorkerModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public WorkerModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new WorkerModel(-1, "");
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(WorkerModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
    }

    protected void initComboBoxProvider(JFXComboBox<ProviderModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(ProviderModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ProviderModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public ProviderModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new ProviderModel(-1, "");
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(ProviderModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
    }

    protected void initComboBoxSupply(JFXComboBox<SupplyModel> comboBox, boolean isSelectionItem, String promptText, String label) {
        initPromptText(comboBox,promptText,label);
        comboBox.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(SupplyModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else setText(null);
            }
        });
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(SupplyModel object) {
                if (object != null) return object.getName();
                else return null;
            }

            @Override
            public SupplyModel fromString(String string) {
                if (!string.trim().isEmpty())
                    return new SupplyModel(-1, "");
                return null;
            }
        });
        if (isSelectionItem) {
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(SupplyModel item, boolean empty) {
                    if (item != null && !empty) {
                        setText(item.getName());
                    } else {
                        setText(null);
                    }
                }
            });
        }
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
       /* imageView.setFitHeight(imageContainer.getPrefHeight());
        imageView.setFitWidth(imageContainer.getPrefWidth());*/
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


    public void destroy() {
        ErrorService.get().overrideError("destroy", this.getClass());
    }

    public void createGallery() {

    }
}

