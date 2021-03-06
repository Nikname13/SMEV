package Iteractor;

import Model.Department.DepartmentModel;
import Model.Department.Departments;
import Presenter.DepartmentPresenter;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class IteractorDepartment extends GenericIteractor<DepartmentModel> {

   private static String sURL="/department_servlet";
   private static String sLoadFileURL ="/load_department_servlet";

   public IteractorDepartment() {
      super(sURL, DepartmentModel.class, new TypeToken<ArrayList<DepartmentModel>>(){}.getType());
   }

   @Override
   public void setList(ObservableList<DepartmentModel> list) {
       Departments.get().setEntityList(list, Comparator.comparing(DepartmentModel::getNameToLowerCase));
   }

   @Override
   protected LocalDateTime getLastUpdate(DepartmentModel entity) {
      return entity.getLastFileDumpUpdate(DepartmentPresenter.get().getTypeDocuments());
   }

   @Override
   public void setEntity(DepartmentModel entity) {
      if(Departments.get().getEntity(entity.getId())!=null){
         entity.setLoad(true);
         Departments.get().replace(entity);
      }else {
         Departments.get().addEntity(entity, Comparator.comparing(DepartmentModel::getNameToLowerCase));
      }
   }

   @Override
   public String getLoadFileURL() {
      return sLoadFileURL;
   }

}
