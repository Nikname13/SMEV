package Presenter;

public abstract class BasePresenter {

    private static Object sSelectedObject;

    public static Object getSelectedObject() {
        return sSelectedObject;
    }

    public void setSelectedObject(Object selectedObject) {
        sSelectedObject = selectedObject;
    }

    abstract void loadEntity(int id);
}
