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

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCheckListModel;
import javax.swing.JCheckList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Very simple example that creates a list of checkable items. The user can check/uncheck the items
 * he wants. When the window is closed, the state of each item is printed on the standard output.
 *
 * @author Bart Jourquin
 */
public class Example {

  /** Constructor. */
  public Example() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create a list containing CheckboxListItem's
    final DefaultCheckListModel<String> myModel = new DefaultCheckListModel<String>();
    JCheckList<String> myCheckList = new JCheckList<>(myModel);

    // Fill it
    for (int i = 0; i < 10; i++) {
      myModel.addItem("Item " + i);
    }

    // Just print the state of each item at closing time
    frame.addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            for (int i = 0; i < myModel.getSize(); i++) {
              if (myModel.isChecked(i)) {
                System.out.println("Item " + i + " is checked");
              } else {
                System.out.println("Item " + i + " is unchecked");
              }
            }
            e.getWindow().dispose();
          }
        });

    frame.getContentPane().add(new JScrollPane(myCheckList));
    frame.setSize(new Dimension(150, 250));
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new Example();
  }
}
