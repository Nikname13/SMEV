package Presenter;

import Iteractor.IteractorProvider;
import Model.Provider.ProviderModel;
import Service.IUpdateData;
import Service.ListenersService;

import java.util.Set;

public class ProviderPresenter extends BasePresenter implements IUpdateData {

    private static ProviderModel mProviderModel;
    private static ProviderPresenter sProviderPresenter;

    public static ProviderPresenter get(){
        if(sProviderPresenter==null){
            sProviderPresenter=new ProviderPresenter();
        }
        return sProviderPresenter;
    }

    private ProviderPresenter(){
        ListenersService.get().addListenerData(this);
    }

    public ProviderModel getProviderModel() {
        return mProviderModel;
    }

    public void setProviderModel(Object providerModel) {
        mProviderModel =(ProviderModel) providerModel;
    }


    public void addProvider(String name, String description) {
        new IteractorProvider().addNew(new ProviderModel(0,name,description));
    }

    public void editProvider(String name, String description) {
        ListenersService.get().updateData(new IteractorProvider().edit(new ProviderModel(mProviderModel.getId(), name, description)));
    }

    public void editProvider(ProviderModel provider) {
        ListenersService.get().updateData(new IteractorProvider().edit(provider));
    }

    public void delete(int id){
        new IteractorProvider().delete(id);
    }

    public void delete(Set<Integer> id){

    }

    public void update(){
        // Providers.get().update();
    }

    @Override
    void loadEntity(int id) {

    }

    @Override
    public void update(Object equipment) {

    }

    @Override
    public void delete() {

    }
}
