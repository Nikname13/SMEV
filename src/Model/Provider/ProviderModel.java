package Model.Provider;

import Model.AbstractModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProviderModel extends AbstractModel {

    private String mDescription;

    public ProviderModel(int id, String name, String description) {
        super(id,name);
        mDescription = description;
    }

    public ProviderModel(int id, String name) {
        super(id, name);
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
