package UI.Equipment.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.cells.editors.base.EditorNodeBuilder;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

public class ComboBoxEditorBuilder<T> implements EditorNodeBuilder<T> {

    private JFXComboBox mComboBox;

    public ComboBoxEditorBuilder(JFXComboBox textField) {
        this.mComboBox = textField;
    }

    @Override
    public void startEdit() {
        System.out.println("startEdit");
    }

    @Override
    public void cancelEdit() {
        System.out.println("cancelEdit");
    }

    @Override
    public void updateItem(T t, boolean b) {
        System.out.println("updateItem");
    }

    @Override
    public Region createNode(T t, EventHandler<KeyEvent> eventHandler, ChangeListener<Boolean> changeListener) {
        System.out.println("createNode");
        mComboBox.setOnKeyPressed(eventHandler);
        return this.mComboBox;
    }

    @Override
    public T getValue() {
        System.out.println("getValue " + mComboBox.getValue());
        return (T) mComboBox.getValue();
    }

    @Override
    public void setValue(T t) {
        System.out.println("setValue");
    }

    @Override
    public void validateValue() {

    }
}
