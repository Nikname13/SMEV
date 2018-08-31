package Model.Parameter;

import Iteractor.IteractorParameter;
import Model.GenericModel;

import java.util.List;

public class ParameterModel extends GenericModel<ValueParameterModel> {


    private transient boolean mIsValue;

    public ParameterModel(int id, String name, List<ValueParameterModel> entityList, boolean isValue) {
        super(id, name, entityList);
        mIsValue = isValue;
    }

    public ParameterModel(int id, String name) {
        super(id, name);
    }

    public ParameterModel() {

    }

    @Override
    public List<ValueParameterModel> getEntityList() {
        if (!isLoad()) {
            new IteractorParameter().loadData(getId());
        }
        return super.getEntityList();
    }

    public boolean isValue() {
        return mIsValue;
    }

    public void setValue(boolean value) {
        mIsValue = value;
    }
}
