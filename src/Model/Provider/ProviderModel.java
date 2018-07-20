package Model.Provider;

import Model.AbstractModel;
import Model.GenericModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProviderModel extends AbstractModel {

    private String mDescription;

    public ProviderModel(int id, String name, String description) {
        super(id,name);
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

  }
