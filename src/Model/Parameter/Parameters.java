package Model.Parameter;

import Iteractor.IteractorParameter;
import Model.GenericList;

public class Parameters extends GenericList<ParameterModel> {

    private static Parameters sParameters;

    public static Parameters get() {
        if(sParameters==null) {
            sParameters = new Parameters();
            new IteractorParameter().loadData();
        }
        return sParameters;
    }

    @Override
    public void update() {
        clear();
        new IteractorParameter().loadData();
    }

    @Override
    public void replace(ParameterModel entity) {
        ParameterModel param = Parameters.get().getEntity(entity.getId());
        param.setName(entity.getName());
        param.setLoad(entity.isLoad());
        param.setEntityList(entity.getEntityList());
        param.setValue(!entity.getEntityList().isEmpty());
    }
}
