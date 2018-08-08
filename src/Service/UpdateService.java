package Service;

import java.util.ArrayList;
import java.util.List;

public class UpdateService {

    private List<IUpdateUI> mListenerUI = new ArrayList<>();
    private List<IUpdateData> mListenerData = new ArrayList<>();
    private List<IUpdatePopup> mListenerPopup = new ArrayList<>();
    private IErrorMessage mErrorListener;



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

    public List<IUpdatePopup> getListenerPopup() {
        return mListenerPopup;
    }

    public void addListenerData(IUpdateData listener) {
        mListenerData.add(listener);
    }

    public void addListenerUI(IUpdateUI listener) {
        mListenerUI.add(listener);
    }

    public void addListenerPopup(IUpdatePopup listener) {
        mListenerPopup.add(listener);
    }

    public IErrorMessage getErrorListener() {
        return mErrorListener;
    }

    public void setErrorListener(IErrorMessage errorListener) {
        mErrorListener = errorListener;
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

    public void updateControl(Class<?> updateClass) {
        for (IUpdateUI listener : getListenerUI()) {
            listener.updateControl(updateClass);
        }

    }

    public void updateData(Object object) {
        for (IUpdateData listener : getListenerData()) {
            listener.update(object);
        }
    }

    public void delete() {
        for (IUpdateData listener : getListenerData()) {
            listener.delete();
        }
    }

    public void hidePopup() {
        for (IUpdatePopup listener : mListenerPopup) {
            listener.hide();
        }
    }

    public void showError(String errorMessage) {
        mErrorListener.showError(errorMessage);
    }
}
