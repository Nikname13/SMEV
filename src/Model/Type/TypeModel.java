package Model.Type;

import Iteractor.IteractorType;
import Model.GenericModel;
import Model.Parameter.ParameterModel;

import java.util.List;

public class TypeModel extends GenericModel<ParameterModel> {

    public TypeModel(int id, String name, List<ParameterModel> entityList) {
        super(id, name, entityList);
    }

    public TypeModel(int id, String name) {
        super(id, name);
    }

    public TypeModel() {
    }

    @Override
    public List<ParameterModel> getEntityList() {
        if(!isLoad()){
            new IteractorType().loadData(getId());
        }
        return  super.getEntityList();
    }
}
