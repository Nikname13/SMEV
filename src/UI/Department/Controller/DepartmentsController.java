package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Presenter.DepartmentPresenter;
import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.Coordinator;
import UI.Popup.BasePopup;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DepartmentsController implements IUpdateUI {

    public DepartmentsController(){
        UpdateService.get().addListenerUI(this);
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

    @FXML
    public void initialize(){
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

    private static void primaryClickDepartment() {
        TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditDepartmentResource()));
        UpdateService.get().updateUI(DepartmentModel.class);
    }

    private void initPopup() {
        mPopup = new BasePopup(mDepartmentListView, BasePopup.getBaseListPopup(), DepartmentsController::primaryClickDepartment);

    }

    private void selectedDepartment(DepartmentModel department) {
        if(department!=null) {
            DepartmentPresenter.get().setBasePopup(mPopup);
            DepartmentPresenter.get().setDepartmentModel(department);
            DepartmentPresenter.get().setSelectedObject(department);

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
}
