package javax.swing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JCheckList.CheckableItem;

public class DefaultCheckListModel<Type> extends AbstractListModel implements CheckListModel<Type> {

	private static final long serialVersionUID = 1L;
	private List<CheckableItem<Type>> list;

	public DefaultCheckListModel() {
		list = new ArrayList<>();
	}

	public DefaultCheckListModel(List<Type> list) {
		List<CheckableItem<Type>> checkableList = new ArrayList<>();
		for (Type item : list) {
			checkableList.add(new CheckableItem<>(item));
		}
		this.list = checkableList;
	}

	@Override
	public void addItem(Type item) {
		list.add(new CheckableItem<>(item));
		fireContentsChanged(this, list.indexOf(item), list.indexOf(item));
		fireIntervalAdded(this, list.indexOf(item), list.indexOf(item));
	}

	@Override
	public Type checkItem(int index) {
		CheckableItem<Type> item = list.get(index);
		item.setSelected(true);
		fireContentsChanged(this, list.indexOf(item), list.indexOf(item));
		return item.getValue();
	}

	@Override
	public Type checkItem(Type item) {
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
	public Collection<Type> getCheckedItems() {
		Collection<Type> checkedItems = new ArrayList<>();
		for (CheckableItem<Type> item : list) {
			if (item.isSelected()) {
				checkedItems.add(item.getValue());
			}
		}
		return checkedItems;
	}

	@Override
	public Type getElementAt(int index) {
		CheckableItem<Type> type = list.get(index);
		return type == null ? null : type.getValue();
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public boolean isChecked(int index) {
		CheckableItem<Type> item = list.get(index);
		return item.isSelected();
	}

	@Override
	public Type troggleItem(int index) {
		CheckableItem<Type> item = list.get(index);
		item.setSelected(!item.isSelected());
		fireContentsChanged(this, list.indexOf(item), list.indexOf(item));
		return item.getValue();
	}

}
