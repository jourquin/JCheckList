package javax.swing;

import java.util.Collection;

public interface CheckListModel<Type> extends ListModel {

	public void addItem(Type router);

	public Type checkItem(int index);

	public Type checkItem(Type item);

	public void clear();

	public Collection<Type> getCheckedItems();

	@Override
	public Type getElementAt(int index);

	public boolean isChecked(int index);

	public Type troggleItem(int index);
}
