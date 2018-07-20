package Model.Provider;

import Iteractor.IteractorProvider;
import Model.GenericList;

public class Providers extends GenericList<ProviderModel> {

    private static Providers sProviders;

    public static Providers get(){
        if(sProviders==null){
            sProviders=new Providers();
            new IteractorProvider().loadData();
        }
        return sProviders;
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
