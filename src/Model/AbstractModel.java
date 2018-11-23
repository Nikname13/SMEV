package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class AbstractModel<T extends AbstractModel<?>> extends RecursiveTreeObject<AbstractModel> implements IAbstractModel {

    private int mId;
    private String mName;
    private LocalDate mDate;
    private LocalDateTime mLastUpdate;

    private transient String mDisplayDate;
    private transient static String typeDoc="doc";
    private transient static String typeConfig="config";
    private transient static String typePhoto="photo";

    public AbstractModel(int id, String name) {
        mId = id;
        mName = name;
    }

    public AbstractModel(int id, String name, LocalDate date) {
        mId = id;
        mName = name;
        mDate = date;
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

    public LocalDate getDate() {
        return mDate;
    }

    public void setDate(LocalDate date) {
        mDate = date;
    }

    public LocalDateTime getLastUpdate() {
        return mLastUpdate;
    }

    public abstract T create(String name);

    @Override
    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(getId());
    }

    @Override
    public StringProperty nameProperty() {
        return new SimpleStringProperty(getName());
    }

    @Override
    public ObjectProperty<LocalDate> dateProperty() {
        return new SimpleObjectProperty<>(mDate);
    }

    @Override
    public StringProperty dateToString() {
        if (mDisplayDate == null) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return new SimpleStringProperty((mDate).format(format));
        } else {

            return new SimpleStringProperty(mDisplayDate);
        }
    }

    public String getDisplayDate() {
        return mDisplayDate;
    }

    public void setDisplayDate(String displayDate) {
        mDisplayDate = displayDate;
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
