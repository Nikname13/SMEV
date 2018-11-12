package Presenter;

import Iteractor.IteractorArea;
import Model.Area.AreaModel;
import Service.ListenersService;

public class AreaPresenter extends BasePresenter {

    private static AreaModel sAreaModel;
    private static AreaPresenter sAreaPresenter;

    public static AreaPresenter get(){
        if(sAreaPresenter==null){
            sAreaPresenter=new AreaPresenter();
        }
        return sAreaPresenter;
    }

    private AreaPresenter(){
        ListenersService.get().addListenerData(this);
    }

    public static AreaModel getArea() {
        return sAreaModel;
    }

    public void setArea(AreaModel area) {
        sAreaModel = area;
        setSelectedObject(area);
    }

    public void addArea(String name){
        new IteractorArea().addNew(new AreaModel(0,name));
        ListenersService.get().updateControl(AreaModel.class);
    }

    public void editArea(String name){
        ListenersService.get().updateData(new IteractorArea().edit(new AreaModel(sAreaModel.getId(), name)));
    }

    public void editArea(AreaModel area) {
        ListenersService.get().updateData(new IteractorArea().edit(area));
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null && getSelectedObject().equals(sAreaModel)) {
                if (new IteractorArea().delete(sAreaModel.getId())) {
                    ListenersService.get().updateControl(AreaModel.class);
                }
        }
    }
}
