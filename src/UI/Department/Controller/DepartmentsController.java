package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Presenter.DepartmentPresenter;
import Service.IOnMouseClick;
import Service.IUpdateUI;
import Service.ListenersService;
import Service.TabControllerService;
import UI.Coordinator;
import UI.Popup.BasePopup;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DepartmentsController implements IUpdateUI, IOnMouseClick {

    public DepartmentsController(){
        ListenersService.get().addListenerUI(this);
    }

    private BasePopup mPopup;

    @FXML
    private TableView<DepartmentModel> mDepartmentTableView;

    @FXML
    private TableColumn<DepartmentModel,String> numberColumn, nameColumn, areaColumn;

    @FXML
    private JFXListView<DepartmentModel> mDepartmentListView;

    @FXML
    private StackPane mStackPane;

    private Stage getStage() {
        return (Stage) mStackPane.getScene().getWindow();
    }

    @FXML
    public void initialize(){
        System.out.println("init department");
        initListViewDepartments();
        initPopup();
    }

    private void initListViewDepartments() {
        mDepartmentListView.setItems(DepartmentPresenter.get().getObservableDepartment());
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
                    DepartmentPresenter.get().setSelectedObject(mDepartmentListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void initPopup() {
        mPopup = new BasePopup(mDepartmentListView, BasePopup.getDepartmentListPopup(), this);

    }

    private void selectedDepartment(DepartmentModel department) {
        if(department!=null) {
            //DepartmentPresenter.get().setBasePopup(mPopup);
            DepartmentPresenter.get().setDepartmentModel(department);
            DepartmentPresenter.get().setSelectedObject(department);
            DepartmentPresenter.get().loadEntity(department.getId());
            TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditDepartmentResource()));
            ListenersService.get().updateUI(DepartmentModel.class);
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
        if (updateClass.getName().equals(DepartmentModel.class.getName()))
            mDepartmentListView.setItems(DepartmentPresenter.get().getObservableDepartment());
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
        switch (id) {
            case "purchase":
                new Coordinator().goToPurchasesWindow(getStage());
                break;
        }
    }
}
