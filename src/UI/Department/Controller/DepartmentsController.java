package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Presenter.DepartmentPresenter;
import Service.IUpdateUI;
import Service.TabControllerService;
import Service.UpdateService;
import UI.Coordinator;
import UI.Popup.BasePopup;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DepartmentsController implements IUpdateUI {

    public DepartmentsController(){
        UpdateService.get().addListener(this);
    }

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
        initPopup();
    }

    private void initPopup() {
        new BasePopup(mDepartmentListView, BasePopup.getDepartmentsListPopup());
    }

    private void selectedDepartment(DepartmentModel department) {
        if(department!=null) {
            System.out.println("select department");
            DepartmentPresenter.get().setDepartmentModel(department);
            TabControllerService.get().getListenerFirstTabPane().nextTab(TabControllerService.get().getNextTab(TabControllerService.get().getEditDepartmentResource()));
            System.out.println(" listener " + TabControllerService.get().getListenerFirstTabPane().getClass().getName());
            UpdateService.get().updateUI(DepartmentModel.class);
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

    }

    @Override
    public void updateControl(Class<?> updateClass) {

    }
}
