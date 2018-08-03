package Presenter;

import UI.Popup.BasePopup;

public class BasePresenter {

    private static Object sSelectedObject;
    private static BasePopup sBasePopup;

    public BasePopup getBasePopup() {
        return sBasePopup;
    }

    public void setBasePopup(BasePopup basePopup) {
        sBasePopup = basePopup;
    }

    public Object getSelectedObject() {
        return sSelectedObject;
    }

    public void setSelectedObject(Object selectedObject) {
        sSelectedObject = selectedObject;
    }
}
