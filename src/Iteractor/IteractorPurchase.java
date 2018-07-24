package Iteractor;

import Model.Department.Departments;
import Model.Department.PurchaseModel;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class IteractorPurchase extends GenericIteractor<PurchaseModel> {

    private static String sURL="/purchase_servlet";

    public IteractorPurchase() {
        super(sURL,PurchaseModel.class, new TypeToken<ArrayList<PurchaseModel>>(){}.getType());
    }

    @Override
    public String getGson(PurchaseModel entity) {
        return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(Expose.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create().toJson(entity);
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
