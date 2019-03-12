package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Presenter.DepartmentPresenter;
import Service.IOnMouseClick;
import Service.ListenersService;
import Service.TabControllerService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import UI.TabPane.Controller.TabPaneSecondLvlTabController;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DepartmentsController extends BaseController implements IOnMouseClick {
    @FXML
    private JFXListView<DepartmentModel> mDepartmentListView;
    @FXML
    private StackPane mStackPane;
    @FXML
    private JFXTextField mTextFieldSearch;
    private ObservableList<DepartmentModel> mModelList;
    private FilteredList<DepartmentModel> mFilteredList;

    public DepartmentsController() {
        ListenersService.get().addListenerUI(this);
    }

    @Override
    public Stage getStage() {
        return (Stage) mStackPane.getScene().getWindow();
    }

    @FXML
    public void initialize(){
        System.out.println("init department");
        mModelList = FXCollections.observableArrayList();
        initTextView();
        initListViewDepartments();
        initPopup();
    }

    private void initTextView() {
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        mTextFieldSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            mFilteredList.setPredicate(department -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (department.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return department.getNumber().toLowerCase().contains(lowerCaseFilter);
            });
        }));
    }

    private void initListViewDepartments() {
        updateTable(DepartmentPresenter.get().getObservableDepartment());
        mDepartmentListView.setCellFactory(p->new ListCell<>(){
            @Override
            protected void updateItem(DepartmentModel item, boolean empty) {
                super.updateItem(item, empty);
                if(item!=null && !empty){
                    setText(item.getNumber()+" - "+item.getName());
                }else {
                    setText(null);
                }
            }
        });
        mDepartmentListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedDepartment(newValue)));
        mDepartmentListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!oldValue)
                    DepartmentPresenter.get().setDepartmentModel(mDepartmentListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void initPopup() {
        new BasePopup(mDepartmentListView, BasePopup.getDepartmentListPopup(), this);

    }

    private void selectedDepartment(DepartmentModel department) {
        if(department!=null) {
            DepartmentPresenter.get().setDepartmentModel(department);
            DepartmentPresenter.get().loadEntity(department.getId());
            TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditDepartmentResource()));
            ListenersService.get().updateUI(DepartmentModel.class);
        } else {
            DepartmentPresenter.get().setDepartmentModel(null);
        }
    }

    @FXML
    private void onClickAdd(){
        new Coordinator().goToAddDepartmentWindow((Stage) mStackPane.getScene().getWindow());
    }

    @Override
    public void updateUI(Class<?> updateClass) {
        if (updateClass.getName().equals(this.getClass().getName())) {
            mDepartmentListView.getSelectionModel().clearSelection();
            ListenersService.get().updateControl(TabPaneSecondLvlTabController.class);
        }
    }

    @Override
    public void refreshControl(Class<?> updateClass) {
        if (updateClass.getClass().getName().equals(Departments.class.getName())) {
            mDepartmentListView.refresh();
        }
    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(DepartmentModel.class.getName())) {
            updateTable(DepartmentPresenter.get().getObservableDepartment());
        }
    }

    private void updateTable(ObservableList<DepartmentModel> observableDepartment) {
        mModelList = observableDepartment;
        mFilteredList = new FilteredList<>(mModelList, p -> true);
        SortedList<DepartmentModel> sortedList = new SortedList<>(mFilteredList);
        mDepartmentListView.setItems(sortedList);
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {
        if (updateClass.getName().equals(DepartmentModel.class.getName())) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mDepartmentListView.getSelectionModel().select((DepartmentModel) currentItem);
                    mDepartmentListView.getFocusModel().getFocusedIndex();
                }
            });
        }
    }

    @Override
    public void primaryClick(String id) {
        if (id.equals("purchase")) {
                new Coordinator().goToPurchasesWindow(getStage());
        }
    }
}
