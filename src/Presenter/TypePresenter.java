package Presenter;

import Iteractor.IteractorType;
import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Service.ListenersService;
import UI.Type.Controller.TypesController;

import java.util.List;

public class TypePresenter extends BasePresenter {

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

    public void editType(String name) {
        sTypeModel.setName(name);
        new IteractorType().edit(sTypeModel);
        ListenersService.get().updateControl(TypeModel.class);
    }

    public void editType(List<ParameterModel> parametersList) {
        sTypeModel.setEntityList(parametersList);
        new IteractorType().edit(sTypeModel);
        ListenersService.get().updateControl(TypeModel.class);
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sTypeModel)) {
                if (new IteractorType().delete(sTypeModel.getId())) {
                    ListenersService.get().updateUI(TypesController.class);
                }
            }
        }
    }
}
