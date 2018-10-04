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
import Model.Type.TypeModel;
import Model.Worker.WorkerModel;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public abstract class BaseController {

    private boolean mSelectedLocation;
    private boolean mSelectedPost;

    protected abstract Stage getStage();

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

    protected void initComboBoxArea(JFXComboBox<AreaModel> comboBoxArea, boolean isSelectionItem) {

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

    public boolean isSelectedPost() {
        return mSelectedPost;
    }

    public void setSelectedPost(boolean selectedPost) {
        mSelectedPost = selectedPost;
    }

    protected void initComboBoxDepartment(JFXComboBox<DepartmentModel> comboBox, boolean isSelectionItem) {
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

    protected void initComboBoxType(JFXComboBox<TypeModel> comboBoxType, boolean isSelectionItem) {
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

    protected JFXComboBox initComboBoxParameter(JFXComboBox<ParameterModel> comboBox, boolean isSelectionItem) {
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

    protected JFXComboBox initComboBoxEquipmentParameter(JFXComboBox<EquipmentParameterModel> comboBox, boolean isSelectionItem) {
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

    protected void initComboBoxNumber(JFXComboBox<InventoryNumberModel> comboBox, boolean isSelectionItem) {
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

    protected void initComboBoxState(JFXComboBox<StateModel> comboBox, boolean isSelectionItem) {
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

    protected void initComboBoxWorker(JFXComboBox<WorkerModel> comboBox, boolean isSelectionItem) {
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

    protected void initComboBoxProvider(JFXComboBox<ProviderModel> comboBox, boolean isSelectionItem) {
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

    protected void close(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}

