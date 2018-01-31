/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uclouvain.swing;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/** A JList with checkable items. */
public class JCheckList<E> extends JList<E> {

  /**
   * Inner class representing a checkable item.
   *
   * @param <T> Item value
   */
  public static class CheckableItem<T> {
    private boolean isSelected;
    private T value;

    /**
     * Constructor.
     *
     * @param value Item value
     */
    public CheckableItem(T value) {
      this.value = value;
      isSelected = false;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (obj instanceof CheckableItem) {
        CheckableItem<?> other = (CheckableItem<?>) obj;
        if (value == null) {
          if (other.value != null) {
            return false;
          }
        } else {
          if (value == other.value) {
            return true;
          }
          if (value.equals(other.value)) {
            return true;
          }
        }
      }
      return false;
    }

    /**
     * Returns the item value.
     *
     * @return Item value
     */
    public T getValue() {
      return value;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (value == null ? 0 : value.hashCode());
      return result;
    }

    /**
     * Tests if the item is selected.
     *
     * @return True if selected
     */
    public boolean isSelected() {
      return isSelected;
    }

    /**
     * Selects the item.
     *
     * @param newValue True to select, false to unselect
     */
    public void setSelected(boolean newValue) {
      isSelected = newValue;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /** Inner class to render the check list. */
  protected class CheckListRenderer extends JCheckBox implements ListCellRenderer<Object> {
    private static final long serialVersionUID = 1L;

    /** Constructor. */
    public CheckListRenderer() {
      setBackground(UIManager.getColor("List.textBackground"));
      setForeground(UIManager.getColor("List.textForeground"));
    }

    @Override
    public JComponent getListCellRendererComponent(
        JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
      setEnabled(list.isEnabled());
      setSelected(isChecked(index));
      setFont(list.getFont());
      setText(value.toString());
      return this;
    }
  }

  private static final long serialVersionUID = 1L;
  private List<CheckListSelectionListener<E>> listeners = new Vector<>();

  private CheckListModel<E> model;

  private final transient ListDataListener myDataListener;

  /**
   * Constructor.
   *
   * @param model A CheckListModel
   */
  public JCheckList(CheckListModel<E> model) {
    super(model);
    this.model = model;
    setCellRenderer(new CheckListRenderer());
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            int index = locationToIndex(e.getPoint());
            if (index == -1) {
              return;
            }
            E item = getModel().toggleItem(index);
            Rectangle rect = getCellBounds(index, index);
            repaint(rect);
            fireCheckListSelectionChanged(item);
          }
        });
    myDataListener =
        new ListDataListener() {
          @Override
          public void contentsChanged(ListDataEvent e) {
            fireCheckListSelectionChanged(null);
          }

          @Override
          public void intervalAdded(ListDataEvent e) {
            fireCheckListSelectionChanged(null);
          }

          @Override
          public void intervalRemoved(ListDataEvent e) {
            fireCheckListSelectionChanged(null);
          }
        };
  }

  /**
   * Adds a selection listener.
   *
   * @param listener The CheckListSelectionListener to add
   */
  public void addCheckListSelectionListener(CheckListSelectionListener<E> listener) {
    listeners.add(listener);
  }

  /**
   * Checks an item.
   *
   * @param element The item to check.
   */
  public void checkItem(E element) {
    E item = getModel().checkItem(element);
    fireCheckListSelectionChanged(item);
  }

  /**
   * Check an item in the list.
   *
   * @param index The index of the item in the list
   * @return The checked item
   */
  public E checkItem(int index) {
    E item = getModel().checkItem(index);
    fireCheckListSelectionChanged(item);
    return item;
  }

  protected void fireCheckListSelectionChanged(E item) {
    for (CheckListSelectionListener<E> listener : listeners) {
      listener.selectionChanged(item);
    }
  }

  /**
   * Gets an item.
   *
   * @param index The index of gthe item to get.
   * @return The item.
   */
  public E getItem(int index) {
    return model.getElementAt(index);
  }

  @Override
  public CheckListModel<E> getModel() {
    return model;
  }

  /**
   * Tests if an item is checked.
   *
   * @param index The index of the item to check
   * @return The item
   */
  public boolean isChecked(int index) {
    return model.isChecked(index);
  }

  /**
   * Removes the selection listener.
   *
   * @param listener The listener to remove
   */
  public void removeCheckListSelectionListener(CheckListSelectionListener<E> listener) {
    listeners.remove(listener);
  }

  /**
   * Set the CheckListModel.
   *
   * @param model The model to set.
   */
  public void setModel(CheckListModel<E> model) {
    super.setModel(model);
    this.model.removeListDataListener(myDataListener);
    this.model = model;
    this.model.addListDataListener(myDataListener);
    fireCheckListSelectionChanged(null);
  }

  @Override
  public void setModel(ListModel<E> model) {
    throw new IllegalArgumentException("This checklist expects a CheckListModel");
  }
}
