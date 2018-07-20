package Iteractor;

import Model.Parameter.Parameters;
import Model.Parameter.ValueParameterModel;
import Net.Connector;
import Net.URLBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Set;

public class IteractorValueParameter{

    private static String sURI="/valueParameter_servlet";

    public void loadData(int idParameter) {
/*        if (Parameters.get().getParameter(idParameter).isValue()) {
            ObservableList<ValueParameterModel> values = FXCollections.observableArrayList();
            for (int y = 0; y < 5; y++) {
                values.add(new ValueParameterModel(y, "Name Value" + y));
            }
            Parameters.get().getParameter(idParameter).setValuesList(values);
        }*/
    }

    public void addNew(Object o, int idParameter) {
       /* Parameters.get().getParameter(idParameter).addValueParameter((ValueParameterModel)o);*/
    }

    public void edit(Object o, int idParameter) {
        /*Parameters.get().getParameter(idParameter).getValueParameter(((ValueParameterModel)o).getId()).setName(((ValueParameterModel)o).getName());*/
    }

    public void delete(Set<Integer> idList) {
        URLBuilder url=new URLBuilder(sURI);
        for(int id:idList){
            url.withParam("id",String.valueOf(id));
        }
        if(Connector.delete(url.build())==200){

        } else{
            System.out.println();
        }
    }
}
