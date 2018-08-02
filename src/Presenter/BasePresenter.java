package Presenter;

public class BasePresenter {

    private static Object sSelectedObject;

    public Object getSelectedObject() {
        return sSelectedObject;
    }

    public void setSelectedObject(Object selectedObject) {
        sSelectedObject = selectedObject;
    }
}
