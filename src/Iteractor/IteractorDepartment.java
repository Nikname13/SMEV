package Iteractor;

import Model.Department.DepartmentModel;
import Model.Department.Departments;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class IteractorDepartment extends GenericIteractor<DepartmentModel> {

   private static String sURL="/department_servlet";
   private static String sLoadFileURL ="/load_department_servlet";

   public IteractorDepartment() {
      super(sURL, DepartmentModel.class, new TypeToken<ArrayList<DepartmentModel>>(){}.getType());
   }

   @Override
   public void setList(ObservableList<DepartmentModel> list) {
      Departments.get().setEntityList(list);
   }

   @Override
   public void setEntity(DepartmentModel entity) {
      if(Departments.get().getEntity(entity.getId())!=null){
         entity.setLoad(true);
         Departments.get().replace(entity);
      }else {
         Departments.get().addEntity(entity);
      }
   }

   @Override
   public String getLoadFileURL() {
      return sLoadFileURL;
   }

   @Override
   public void deleteEntity(int id) {
      Departments.get().deleteEntity(id);
   }
}
