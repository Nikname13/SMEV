package UI.Department.Controller;

import Model.Department.DepartmentModel;
import Model.Department.PurchaseModel;
import Presenter.DepartmentPresenter;
import Service.IUpdateUI;
import Service.ListenersService;
import UI.BaseController;
import UI.Coordinator;
import UI.Popup.Controller.BasePopup;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PurchaseController extends BaseController implements IUpdateUI {

    private DepartmentModel mDepartmentModel;

    @FXML
    private JFXListView<PurchaseModel> mListViewPurchase;

    @FXML
    private AnchorPane mAnchorPanePurchase;

    public PurchaseController() {
        ListenersService.get().addListenerUI(this);
        mDepartmentModel = DepartmentPresenter.get().getDepartmentModel();
    }

    public void initialize() {
        initPurchaseListView();
        initPopup();
    }


    private void initPopup() {
        new BasePopup(mListViewPurchase, BasePopup.getBaseListPopup(), null);
    }


    private void initPurchaseListView() {
        mListViewPurchase.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(PurchaseModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getDate().toString() + " " + item.getDescription());
                } else setText(null);
            }
        });
        mListViewPurchase.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setDepartment(mDepartmentModel);
                DepartmentPresenter.get().setPurchaseModel(newValue);
            }
        }));
        mListViewPurchase.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
/*                if (newValue)
                    DepartmentPresenter.get().setPurchaseModel(mListViewPurchase.getSelectionModel().getSelectedItem());*/
            }
        });
        mListViewPurchase.setItems(mDepartmentModel.getObservableEntityList());
    }

    @FXML
    private void onClickAddPurchase() {
        DepartmentPresenter.get().setDepartmentModel(mDepartmentModel);
        new Coordinator().goToAddPurchaseWindow(getStage());
    }

    @Override
    public void updateUI(Class<?> updateClass) {

    }

    @Override
    public void refreshControl(Class<?> updateClass) {

    }

    @Override
    public void updateControl(Class<?> updateClass) {
        if (updateClass.getName().equals(PurchaseModel.class.getName())) {
            System.out.println("update Purchase");
            mListViewPurchase.setItems(mDepartmentModel.getObservableEntityList());
        }
    }

    @Override
    public void updateControl(Class<?> updateClass, Object currentItem) {

    }

    @Override
    protected Stage getStage() {
        return (Stage) mAnchorPanePurchase.getScene().getWindow();
    }

    @Override
    public void destroy() {
        System.out.println("destroy purchase");
        ListenersService.get().removeListenerUI(this);
    }
}
