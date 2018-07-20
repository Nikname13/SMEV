package Presenter;

import Iteractor.IteractorSupply;
import Model.Provider.ProviderModel;
import Model.Provider.Providers;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class SupplyPresenter {

    private static SupplyModel sSupplyModel;

    public SupplyModel getSupplyModel() {
        return sSupplyModel;
    }

    public void setSupplyModel(Object supplyModel) {
        sSupplyModel = (SupplyModel) supplyModel;
    }

    public ObservableList<SupplyModel> getObservableSupply(){
        return Supplys.get().getEntityList();
    }

    public ObservableList<ProviderModel> getObservableProvider(){
        return Providers.get().getEntityList();
    }

    public void addSupply(String number, String typeSupply, LocalDate dateSupply, String description, String documentation, Object provider ){
        new IteractorSupply().addNew(new SupplyModel(0,number,typeSupply,dateSupply,description,documentation, (ProviderModel)provider));
    }

    public void editSupply(String number, String typeSupply, LocalDate dateSupply, String description, String documentation,ProviderModel provider){
        new IteractorSupply().edit(new SupplyModel(sSupplyModel.getId(),number,typeSupply,dateSupply,description,documentation,provider));
    }

    public void deleteSupply(int id){
        new IteractorSupply().delete(id);
    }

    public void update(){
        Supplys.get().update();
    }
}