package UI.Equipment.Controller;

import com.jfoenix.controls.cells.editors.base.EditorNodeBuilder;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

public class EditableTreeTableCell<S, T> extends JFXTreeTableCell<S, T> {
    protected EditorNodeBuilder builder;
    protected Region editorNode;

    public EditableTreeTableCell(EditorNodeBuilder builder) {
        this.builder = builder;
    }

    protected Object getValue() {
        return getItem() == null ? "" : getItem();
    }

    @Override
    public void startEdit() {
        if (isEditable()) {
            super.startEdit();
            if (editorNode == null) {
                createEditorNode();
            } else {
                // set current value if the editor is already created
                builder.setValue(getValue());
            }
            builder.startEdit();
            setGraphic(editorNode);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    @Override
    public void commitEdit(T newValue) {
        super.commitEdit(newValue);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
    }

    protected void commitHelper(boolean losingFocus) {
        if (editorNode == null) {
            return;
        }
        try {
            builder.validateValue();
            commitEdit((T) builder.getValue());
        } catch (Exception ex) {
            //Most of the time we don't mind if there is a parse exception as it
            //indicates duff user data but in the case where we are losing focus
            //it means the user has clicked away with bad data in the cell. In that
            //situation we want to just cancel the editing and show them the old
            //value.
            if (losingFocus) {
                cancelEdit();
            }
        }

    }

    private void createEditorNode() {
        EventHandler<KeyEvent> keyEventsHandler = t -> {
            if (t.getCode() == KeyCode.ENTER) {
                commitHelper(false);
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            } else if (t.getCode() == KeyCode.TAB) {
                commitHelper(false);

                TreeTableColumn nextColumn = getNextColumn(!t.isShiftDown());
                if (nextColumn != null) {
                    getTreeTableView().edit(getIndex(), nextColumn);
                }
            }
        };

        ChangeListener<Boolean> focusChangeListener = (observable, oldValue, newValue) -> {
            //This focus listener fires at the end of cell editing when focus is lost
            //and when enter is pressed (because that causes the text field to lose focus).
            //The problem is that if enter is pressed then cancelEdit is called before this
            //listener runs and therefore the text field has been cleaned up. If the
            //text field is null we don't commit the edit. This has the useful side effect
            //of stopping the double commit.
            if (!newValue && editorNode != null) {
                commitHelper(true);
            }
        };
        editorNode = builder.createNode(getValue(), keyEventsHandler, focusChangeListener);
    }

    private TreeTableColumn<S, ?> getNextColumn(boolean forward) {
        List<TreeTableColumn<S, ?>> columns = new ArrayList<>();
        for (TreeTableColumn<S, ?> column : getTreeTableView().getColumns()) {
            columns.addAll(getLeaves(column));
        }
        //There is no other column that supports editing.
        if (columns.size() < 2) {
            return null;
        }
        int nextIndex = columns.indexOf(getTableColumn());
        if (forward) {
            nextIndex++;
            if (nextIndex > columns.size() - 1) {
                nextIndex = 0;
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
            }
        }
        return columns.get(nextIndex);
    }

    private List<TreeTableColumn<S, ?>> getLeaves(TreeTableColumn<S, ?> root) {
        List<TreeTableColumn<S, ?>> columns = new ArrayList<>();
        if (root.getColumns().isEmpty()) {
            //We only want the leaves that are editable.
            if (root.isEditable()) {
                columns.add(root);
            }
            return columns;
        } else {
            for (TreeTableColumn<S, ?> column : root.getColumns()) {
                columns.addAll(getLeaves(column));
            }
            return columns;
        }
    }
}
