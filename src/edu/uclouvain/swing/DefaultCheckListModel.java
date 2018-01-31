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

import edu.uclouvain.swing.JCheckList.CheckableItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;

public class DefaultCheckListModel<E> extends AbstractListModel<E> implements CheckListModel<E> {

  private static final long serialVersionUID = 726416792816178211L;

  private List<CheckableItem<E>> list;

  /** Default constructor. */
  public DefaultCheckListModel() {
    list = new ArrayList<>();
  }

  /**
   * Constructor.
   *
   * @param list List of checkable items.
   */
  public DefaultCheckListModel(List<E> list) {
    List<CheckableItem<E>> checkableList = new ArrayList<>();
    for (E item : list) {
      checkableList.add(new CheckableItem<>(item));
    }
    this.list = checkableList;
  }

  @Override
  public void addItem(E item) {
    list.add(new CheckableItem<>(item));
    fireContentsChanged(this, list.indexOf(item), list.indexOf(item));
    fireIntervalAdded(this, list.indexOf(item), list.indexOf(item));
  }

  @Override
  public E checkItem(int index) {
    CheckableItem<E> item = list.get(index);
    item.setSelected(true);
    fireContentsChanged(this, list.indexOf(item), list.indexOf(item));
    return item.getValue();
  }

  @Override
  public E checkItem(E item) {
    int index = list.indexOf(new JCheckList.CheckableItem<>(item));
    if (index == -1) {
      return null;
    }
    return checkItem(index);
  }

  @Override
  public void clear() {
    int end = list.size();
    list.clear();
    fireContentsChanged(this, 0, end);
    fireIntervalRemoved(this, 0, end);
  }

  @Override
  public Collection<E> getCheckedItems() {
    Collection<E> checkedItems = new ArrayList<>();
    for (CheckableItem<E> item : list) {
      if (item.isSelected()) {
        checkedItems.add(item.getValue());
      }
    }
    return checkedItems;
  }

  @Override
  public E getElementAt(int index) {
    CheckableItem<E> type = list.get(index);
    return type == null ? null : type.getValue();
  }

  @Override
  public int getSize() {
    return list.size();
  }

  @Override
  public boolean isChecked(int index) {
    CheckableItem<E> item = list.get(index);
    return item.isSelected();
  }

  @Override
  public E toggleItem(int index) {
    CheckableItem<E> item = list.get(index);
    item.setSelected(!item.isSelected());
    fireContentsChanged(this, list.indexOf(item), list.indexOf(item));
    return item.getValue();
  }
}
