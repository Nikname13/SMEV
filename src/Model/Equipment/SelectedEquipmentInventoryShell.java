package Model.Equipment;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SelectedEquipmentInventoryShell extends EquipmentInventoryModel {

    private transient boolean mChosen;

    public SelectedEquipmentInventoryShell(EquipmentInventoryModel equipmentModel) {
        super(equipmentModel.getId(), equipmentModel.getInventoryNumber(), equipmentModel.getGuaranty(),
                equipmentModel.getDescription(), equipmentModel.getDepartmentModel(), equipmentModel.getEquipmentModel(), equipmentModel.getStateModel());
        mChosen = false;
    }

    public boolean isChosen() {
        return mChosen;
    }

    public void setChosen(boolean chosen) {
        mChosen = chosen;
    }

    public BooleanProperty chosenProperty() {
        return new SimpleBooleanProperty(mChosen);
    }
}
