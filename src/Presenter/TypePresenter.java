package Presenter;

import Iteractor.IteractorType;
import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Service.IUpdateData;
import Service.ListenersService;

import java.util.List;

public class TypePresenter extends BasePresenter implements IUpdateData {

    private static TypeModel sTypeModel;
    private static TypePresenter sTypePresenter;

    public static TypePresenter get(){
        if(sTypePresenter==null){
            sTypePresenter=new TypePresenter();
        }
        return sTypePresenter;
    }

    private TypePresenter(){
        ListenersService.get().addListenerData(this);
    }

    public TypeModel getTypeModel() {
        return sTypeModel;
    }

    public void setTypeModel(TypeModel type) {
        sTypeModel = type;
        setSelectedObject(type);

    }

    public void addType(String name, List<ParameterModel> parameters){
        new IteractorType().addNew(new TypeModel(0,name,parameters));
        ListenersService.get().updateControl(TypeModel.class);
    }

    public void editType(String name, List<Object> parameters){
        new IteractorType().edit(new TypeModel(sTypeModel.getId(),name,sTypeModel.getEntityList()));
    }

    public void editType(TypeModel type) {
        new IteractorType().edit(type);
    }

    public void update(){
        // Types.get().update();
    }

    @Override
    void loadEntity(int id) {

    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sTypeModel)) {
                if (new IteractorType().delete(sTypeModel.getId())) {
                    ListenersService.get().updateControl(TypeModel.class);
                }
            }
        }
    }
}
