package Model.Supply;

import Model.AbstractModel;
import Model.Provider.ProviderModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class SupplyModel extends AbstractModel {

    private LocalDate  mDateSupply;
    private String mDescription, mDocumentation, mTypeSupply;
    private ProviderModel mProviderModel;
    public SupplyModel(int id, String number, String typeSupply, LocalDate dateSupply, String description, String documentation, ProviderModel providerModel) {
        super(id,number);
        mDateSupply = dateSupply;
        mDescription = description;
        mDocumentation = documentation;
        mTypeSupply = typeSupply;
        mProviderModel = providerModel;
    }

    public SupplyModel(int id, String number, ProviderModel provider) {
        super(id, number);
        mProviderModel = provider;
    }

    public SupplyModel(){}


    public LocalDate getDateSupply() {
        return mDateSupply;
    }

    public void setDateSupply(LocalDate dateSupply) {
        mDateSupply = dateSupply;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDocumentation() {
        return mDocumentation;
    }

    public void setDocumentation(String documentation) {
        mDocumentation = documentation;
    }

    public String getTypeSupply() {
        return mTypeSupply;
    }

    public void setTypeSupply(String typeSupply) {
        mTypeSupply = typeSupply;
    }

    public StringProperty typeSupplyProperty() {
        return new SimpleStringProperty(mTypeSupply);
    }

    public StringProperty dateSupply(){
        SimpleDateFormat format= new SimpleDateFormat("dd.MM.yyyy");
        return new SimpleStringProperty(format.format(mDateSupply));
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(mDescription);
    }

    public StringProperty documentationProperty() {
        return new SimpleStringProperty(mDocumentation);
    }

    public ProviderModel getProviderModel() {
        return mProviderModel;
    }

    public void setProviderModel(ProviderModel providerModel) {
        mProviderModel = providerModel;
    }
}
