package Service;

import Model.Equipment.EquipmentInventoryModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateService {

    private List<IUpdateUI> mListenerUI = new ArrayList<>();
    private List<IUpdateData> mListenerData = new ArrayList<>();
    private static UpdateService sUpdateService;

    public static UpdateService get(){
        if(sUpdateService ==null){
            sUpdateService =new UpdateService();

        }
        return sUpdateService;
    }

    public List<IUpdateUI> getListenerUI() {
        return mListenerUI;
    }

    public List<IUpdateData> getListenerData() {
        return mListenerData;
    }

    public void addListenerData(IUpdateData listener) {
        mListenerData.add(listener);
    }

    public void addListener(IUpdateUI listener){
        mListenerUI.add(listener);
    }

    public void updateUI(Class<?> updateClass){
        for (IUpdateUI listener : getListenerUI()) {
            listener.updateUI(updateClass);
        }
    }

    public void refreshControl(Class<?> updateClass) {
        for (IUpdateUI listener : getListenerUI()) {
            listener.refreshControl(updateClass);
        }
    }

    public void updateData(EquipmentInventoryModel equipment) {
        for (IUpdateData listener : getListenerData()) {
            listener.updateEquipment(equipment);
        }
    }
}
