package Presenter;

import Iteractor.IteractorProvider;
import Model.Provider.ProviderModel;
import Model.Provider.Providers;

import java.util.Set;

public class ProviderPresenter extends BasePresenter {

    private static ProviderModel mProviderModel;

    public ProviderModel getProviderModel() {
        return mProviderModel;
    }

    public void setProviderModel(Object providerModel) {
        mProviderModel =(ProviderModel) providerModel;
    }


    public void addProvide(String name, String description){
        new IteractorProvider().addNew(new ProviderModel(0,name,description));
    }

    public void editProvide(String name, String description){
        new IteractorProvider().edit(new ProviderModel(mProviderModel.getId(),name, description));
    }

    public void delete(int id){
        new IteractorProvider().delete(id);
    }

    public void delete(Set<Integer> id){

    }

    public void update(){
        Providers.get().update();
    }

    @Override
    void loadEntity(int id) {

    }
}
