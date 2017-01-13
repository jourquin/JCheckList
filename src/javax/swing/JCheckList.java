package javax.swing;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A JList with checkable items
 *  
 * @author Bart Jourquin (inspired by various sources)
 *
 */

public class JCheckList<E> extends JList {

	public static class CheckableItem<Type> {
		private boolean isSelected;
		private Type value;

		public CheckableItem(Type value) {
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

		public Type getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (value == null ? 0 : value.hashCode());
			return result;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean newValue) {
			isSelected = newValue;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
	protected class CheckListRenderer extends JCheckBox implements ListCellRenderer {
		private static final long serialVersionUID = 1L;

		public CheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		@Override
		public JComponent getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
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

	public JCheckList(CheckListModel<E> model) {
		super(model);
		this.model = model;
		setCellRenderer(new CheckListRenderer());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index == -1) {
					return;
				}
				E item = getModel().troggleItem(index);
				Rectangle rect = getCellBounds(index, index);
				repaint(rect);
				fireCheckListSelectionChanged(item);
			}
		});
		myDataListener = new ListDataListener() {
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

	public void addCheckListSelectionListener(CheckListSelectionListener<E> listener) {
		listeners.add(listener);
	}

	public void checkItem(E element) {
		E item = getModel().checkItem(element);
		fireCheckListSelectionChanged(item);
	}

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

	public E getItem(int index) {
		return model.getElementAt(index);
	}

	@Override
	public CheckListModel<E> getModel() {
		return model;
	}

	public boolean isChecked(int index) {
		return model.isChecked(index);
	}

	public void removeCheckListSelectionListener(CheckListSelectionListener<E> listener) {
		listeners.remove(listener);
	}

	public void setModel(CheckListModel<E> model) {
		super.setModel(model);
		this.model.removeListDataListener(myDataListener);
		this.model = model;
		this.model.addListDataListener(myDataListener);
		fireCheckListSelectionChanged(null);
	}

	@Override
	public void setModel(ListModel model) {
		throw new IllegalArgumentException("This checklist expects a CheckListModel");
	}

}
