package Iteractor;

import Model.Department.Departments;
import Model.Department.PurchaseModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class IteractorPurchase extends GenericIteractor<PurchaseModel> {

    private static String sURL="/purchase_servlet";

    public IteractorPurchase() {
        super(sURL,PurchaseModel.class, new TypeToken<ArrayList<PurchaseModel>>(){}.getType());
    }

    @Override
    public void setEntity(PurchaseModel entity) {
        if(Departments.get().getEntity(entity.getDepratment().getId()).getEntity(entity.getId())!= null){
            Departments.get().getEntity(entity.getDepratment().getId()).replace(entity);
        }else{
            Departments.get().getEntity(entity.getDepratment().getId()).addEntity(entity);
        }
    }

    @Override
    public void deleteEntity(PurchaseModel entity) {
        entity.getDepratment().deleteEntity(entity);
    }
}
