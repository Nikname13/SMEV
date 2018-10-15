package Presenter;

import Iteractor.IteractorArea;
import Model.Area.AreaModel;
import Model.Area.Areas;
import Service.IUpdateData;
import Service.IUpdateUI;
import Service.ListenersService;

public class AreaPresenter extends BasePresenter implements IUpdateData {

    private static AreaModel mArea;
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

    public void setArea(Object area){
        mArea = (AreaModel) area;
    }

    public static AreaModel getArea() {
        return mArea;
    }

    public void addArea(String name){
        new IteractorArea().addNew(new AreaModel(0,name));
        ListenersService.get().updateUI(AreaModel.class);
    }

    public void editArea(String name){
        new IteractorArea().edit(new AreaModel(mArea.getId(),name));
    }

    @Override
    void loadEntity(int id) {

    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {

    }
}
