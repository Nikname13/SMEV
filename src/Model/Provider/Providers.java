package Model.Provider;

import Iteractor.IteractorProvider;
import Model.GenericList;
import javafx.collections.ObservableList;

public class Providers extends GenericList<ProviderModel> {

    private static Providers sProviders;

    public static Providers get(){
        if(sProviders==null){
            sProviders=new Providers();
        }
        return sProviders;
    }

    @Override
    public ObservableList<ProviderModel> getObsEntityList() {
        new IteractorProvider().loadData();
        return super.getObsEntityList();
    }

    @Override
    public void update() {
        clear();
        new IteractorProvider();
    }

    @Override
    public void replace(ProviderModel entity) {
        ProviderModel provider=Providers.get().getEntity(entity.getId());
        provider.setName(entity.getName());
        provider.setDescription(entity.getDescription());
    }
}
