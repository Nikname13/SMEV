package Presenter;

import Iteractor.IteractorSupply;
import Model.Provider.ProviderModel;
import Model.Supply.SupplyModel;
import Model.Supply.Supplys;

import java.time.LocalDate;

public class SupplyPresenter extends BasePresenter {

    private static SupplyModel sSupplyModel;
    private static SupplyPresenter sSupplyPresenter;

    private SupplyPresenter() {
    }

    public static SupplyPresenter get() {
        if (sSupplyPresenter == null) sSupplyPresenter = new SupplyPresenter();
        return sSupplyPresenter;
    }

    public SupplyModel getSupplyModel() {
        return sSupplyModel;
    }

    public void setSupplyModel(Object supplyModel) {
        sSupplyModel = (SupplyModel) supplyModel;
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

    @Override
    void loadEntity(int id) {

    }
}
