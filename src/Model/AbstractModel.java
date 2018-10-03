package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class AbstractModel<T extends AbstractModel<?>> extends RecursiveTreeObject<AbstractModel> implements IAbstractModel {

    private int mId;
    private String mName;
    private transient static String typeDoc="doc";
    private transient static String typeConfig="config";
    private transient static String typePhoto="photo";

    public AbstractModel(int id, String name) {
        mId = id;
        mName = name;
    }

    public AbstractModel(int id){
        mName="not name";
        mId=id;
            }

    public AbstractModel() {
        mName="not name";
        mId=-1;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public String getNameToLowerCase() {
        return mName.toLowerCase();
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(getId());
    }

    @Override
    public StringProperty nameProperty() {
        return new SimpleStringProperty(getName());
    }

    @Override
    public int hashCode() {
        return getId();
    }

    public static String getTypeDoc() {
        return typeDoc;
    }

    public static String getTypeConfig() {
        return typeConfig;
    }

    public static String getTypePhoto() {
        return typePhoto;
    }
}
