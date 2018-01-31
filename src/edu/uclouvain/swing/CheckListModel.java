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

import java.util.Collection;

import javax.swing.ListModel;

/**
 * Extended list model.
 *
 * @param <E> Type of element in this model.
 */
public interface CheckListModel<E> extends ListModel<E> {

  /**
   * Add an item to the check list.
   *
   * @param router Item
   */
  public void addItem(E router);

  /**
   * Check an item.
   *
   * @param index Index of the item to check.
   * @return Checked item
   */
  public E checkItem(int index);

  /**
   * Check an item.
   *
   * @param item Item to check.
   * @return Checked item
   */
  public E checkItem(E item);

  /** Clear the list. */
  public void clear();

  /**
   * Returns a collection of checked item.
   *
   * @return Collection of checked items.
   */
  public Collection<E> getCheckedItems();

  @Override
  public E getElementAt(int index);

  /**
   * Tests if an item is checked.
   *
   * @param index Index of the item to test.
   * @return True if the item is checked
   */
  public boolean isChecked(int index);

  /**
   * Toggle the state of an item.
   *
   * @param index Index of the item to toggle
   * @return The item
   */
  public E toggleItem(int index);
}
