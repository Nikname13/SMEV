package Service;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabControllerService {

    private ITabSelected mListenerFirstTabPane, mListenerSecondTabPane;
    private static TabControllerService sTabControllerService;
    private ResourceModel mEditDepartmentResource, mEquipmentInventoryResource, mEditEquipmentResource, mEmptyTab, mDetailsMovementResource;
    private List<Tab> mTabList;

    public static TabControllerService get(){
        if(sTabControllerService ==null){
            sTabControllerService =new TabControllerService();
        }
        return sTabControllerService;
    }

    private TabControllerService(){
        mEditDepartmentResource=new ResourceModel("/UI/Department/editDepartment.fxml","anchorPaneEditDepartment");
        mEquipmentInventoryResource=new ResourceModel("/UI/Equipment/Equipment_inventory/equipmentInventory.fxml","anchorPaneEquipmentInventory");
        mEditEquipmentResource=new ResourceModel("/UI/Equipment/editEquipment.fxml", "anchorPaneEditEquipment");
        mEmptyTab=new ResourceModel("/UI/TabPane/emptyTab.fxml", "emptyTab.fxml");
        mDetailsMovementResource = new ResourceModel("/UI/Movement/detailsMovement.fxml", "mDetailsAnchorPane");
    }

    public ITabSelected getListenerFirstTabPane() {
        return mListenerFirstTabPane;
    }

    public void setListenerFirstTabPane(ITabSelected listenerFirstTabPane) {
        mListenerFirstTabPane = listenerFirstTabPane;
    }

    public ITabSelected getListenerThirdTabPane() {
        return mListenerSecondTabPane;
    }

    public void setListenerThirdTabPane(ITabSelected listenerThirdTabPane) {
        mListenerSecondTabPane = listenerThirdTabPane;
    }

    public Tab getNextTab(ResourceModel resource) {
        if(mTabList==null) mTabList=new ArrayList<>();
/*        for(Tab tab:mTabList){
            if(tab.getContent().getId().equals(resource.getId())) return tab;
        }*/
        try {
            Tab nextTab=new Tab();
            nextTab.setContent(FXMLLoader.load(getClass().getResource(resource.getURL())));
            nextTab.getContent().minHeight(1000.0);
           // mTabList.add(nextTab);
            return nextTab;
        }catch (IOException ex){
            System.out.println("ошибка доступа к fxml");
            return null;
        }
    }

    public  ResourceModel getEditDepartmentResource() {
        return mEditDepartmentResource;
    }

    public  ResourceModel getEquipmentInventoryResource() {
        return mEquipmentInventoryResource;
    }

    public ResourceModel getEditEquipmentResource() {
        return mEditEquipmentResource;
    }

    public ResourceModel getDetailsMovementResource() {
        return mDetailsMovementResource;
    }
}
