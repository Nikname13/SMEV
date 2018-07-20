package Model.Equipment;

import Model.AbstractModel;
import Model.State.StateModel;
import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class EquipmentStateModel extends AbstractModel {

    private String mDescription;
    private StateModel mStateModel;
    private LocalDate mDate;

    public EquipmentStateModel(int id, String description, StateModel stateModel, LocalDate date) {
        super(id);
        mDescription = description;
        mStateModel = stateModel;
        mDate = date;
    }

    public EquipmentStateModel(int id, StateModel stateModel){
        super(id);
        mStateModel=stateModel;
    }

    public EquipmentStateModel(){}

    public String getDescription() {
        return mDescription;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public StateModel getStateModel() {
        return mStateModel;
    }

    public void setStateModel(StateModel stateModel) {
        mStateModel = stateModel;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public StringProperty dateState(){
        SimpleDateFormat format= new SimpleDateFormat("dd.MM.yyyy");
        return new SimpleStringProperty(format.format(mDate));
    }
}
