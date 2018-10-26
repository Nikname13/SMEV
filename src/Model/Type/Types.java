package Model.Type;

import Iteractor.IteractorType;
import Model.GenericList;
import javafx.collections.ObservableList;

public class Types extends GenericList<TypeModel> {

    private static Types sTypes;

    public static Types get(){
        if(sTypes==null){
            sTypes=new Types();
        }
        return sTypes;
    }

    @Override
    public ObservableList<TypeModel> getObsEntityList() {
        new IteractorType().loadData();
        return super.getObsEntityList();
    }

    @Override
    public void update() {
        clear();
        new IteractorType().loadData();
    }

    @Override
    public void replace(TypeModel entity) {
        TypeModel type=Types.get().getEntity(entity.getId());
        type.setName(entity.getName());
        type.setLoad(entity.isLoad());
        type.setEntityList(entity.getEntityList());
    }

}
