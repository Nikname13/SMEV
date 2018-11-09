package Presenter;

import Iteractor.IteractorProvider;
import Model.Provider.ProviderModel;
import Service.ListenersService;

public class ProviderPresenter extends BasePresenter {

    private static ProviderModel sProviderModel;
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
        return sProviderModel;
    }

    public void setProviderModel(ProviderModel providerModel) {
        sProviderModel = providerModel;
        setSelectedObject(providerModel);

    }


    public void addProvider(String name, String description) {
        new IteractorProvider().addNew(new ProviderModel(0,name,description));
        ListenersService.get().updateControl(ProviderModel.class);
    }

    public void editProvider(String name, String description) {
        ListenersService.get().updateData(new IteractorProvider().edit(new ProviderModel(sProviderModel.getId(), name, description)));
    }

    public void editProvider(ProviderModel provider) {
        ListenersService.get().updateData(new IteractorProvider().edit(provider));
    }

    @Override
    public void delete() {
        if (getSelectedObject() != null) {
            if (getSelectedObject().equals(sProviderModel)) {
                if (new IteractorProvider().delete(sProviderModel.getId())) {
                    ListenersService.get().updateControl(ProviderModel.class);
                }
            }
        }
    }
}
