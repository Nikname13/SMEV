package Presenter;

import Iteractor.IteractorType;
import Model.Parameter.ParameterModel;
import Model.Type.TypeModel;
import Model.Type.Types;

import java.util.ArrayList;
import java.util.List;

public class TypePresenter extends BasePresenter {

    private static TypeModel sTypeModel;

    public TypeModel getTypeModel() {
        return sTypeModel;
    }

    public void setTypeModel(Object type) {
        sTypeModel = (TypeModel) type;
    }

    public void addType(String name, List<Object> parameters){
        new IteractorType().addNew(new TypeModel(0,name,parametersList(parameters)));
    }

    public void editType(String name, List<Object> parameters){
        new IteractorType().edit(new TypeModel(sTypeModel.getId(),name,sTypeModel.getEntityList()));
        update();
    }

    private List<ParameterModel> parametersList(List<Object> parameters){
        if(!parameters.isEmpty()) {
            List<ParameterModel> parameterModels = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                parameterModels.add((ParameterModel) parameters.get(i));
            }
            return parameterModels;
        }else {
            return null;
        }
    }

    public void deleteType(int id){
        new IteractorType().delete(id);
        update();
    }

    public void deleteParameter(Object entity){
        sTypeModel.deleteEntity((ParameterModel)entity);
        update();
    }

    public void update(){
        Types.get().update();
    }

    @Override
    void loadEntity(int id) {

    }
}
