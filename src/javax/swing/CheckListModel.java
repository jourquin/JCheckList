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

package javax.swing;

import java.util.Collection;

public interface CheckListModel<E> extends ListModel<E> {

  public void addItem(E router);

  public E checkItem(int index);

  public E checkItem(E item);

  public void clear();

  public Collection<E> getCheckedItems();

  @Override
  public E getElementAt(int index);

  public boolean isChecked(int index);

  public E troggleItem(int index);
}
