package Presenter;

import Iteractor.IteractorArea;
import Model.Area.AreaModel;
import Model.Area.Areas;
import javafx.collections.ObservableList;

public class AreaPresenter {

    private static AreaModel mArea;

    public void setArea(Object area){
        this.mArea=(AreaModel) area;
    }

    public static AreaModel getArea() {
        return mArea;
    }

    public ObservableList<AreaModel> getObservableArea(){
        return Areas.get().getEntityList();
    }

    public void addArea(String name){
        new IteractorArea().addNew(new AreaModel(0,name));
    }

    public void editArea(String name){
        new IteractorArea().edit(new AreaModel(mArea.getId(),name));
    }

    public void delete(int id){
       new IteractorArea().delete(id);
    }

    public void update(){
       Areas.get().update();
    }
}
