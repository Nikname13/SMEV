package Service;

import java.util.ArrayList;
import java.util.List;

public class UpdateService {

    private List<IUpdateUI> mListeners=new ArrayList<>();
    private static UpdateService sUpdateService;

    public static UpdateService get(){
        if(sUpdateService ==null){
            sUpdateService =new UpdateService();

        }
        return sUpdateService;
    }

    public List<IUpdateUI> getListeners() {
        return mListeners;
    }

    public void addListener(IUpdateUI listener){
        mListeners.add(listener);
    }

    public void updateUI(Class<?> updateClass){
        for(IUpdateUI listener : UpdateService.get().getListeners()){
            listener.updateUI(updateClass);
        }
    }
}
