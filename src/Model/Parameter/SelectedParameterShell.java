package Model.Parameter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SelectedParameterShell extends ParameterModel {

    private transient boolean mChosen;

    public SelectedParameterShell(ParameterModel parameter) {
        super(parameter.getId(), parameter.getName());
        mChosen = false;
    }

    public boolean isChosen() {
        return mChosen;
    }

    public void setChosen(boolean chosen) {
        mChosen = chosen;
    }

    public BooleanProperty chosenProperty(){
        return new SimpleBooleanProperty(mChosen);
    }
}
