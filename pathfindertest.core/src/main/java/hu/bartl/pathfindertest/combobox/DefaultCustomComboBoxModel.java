package hu.bartl.pathfindertest.combobox;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * Default Implementation of CustomComboBoxModel - untested.
 */
public class DefaultCustomComboBoxModel<T> extends AbstractListModel
		implements
			CustomComboBoxModel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6869762151205939276L;
	List<T> objects;
	T selectedObject;

	/**
	 * Constructs an empty DefaultCustomComboBoxModel object.
	 */
	public DefaultCustomComboBoxModel() {
		objects = new ArrayList<T>();
	}

	/**
	 * Constructs a DefaultCustomComboBoxModel object initialized with an array
	 * of objects.
	 * 
	 * @param items
	 *            an array of Object objects
	 */
	public DefaultCustomComboBoxModel(final T items[]) {
		objects = new ArrayList<T>();

		int i, c;
		for (i = 0, c = items.length; i < c; i++) {
			objects.add(items[i]);
		}

		if (getSize() > 0) {
			selectedObject = objects.get(0);
		}
	}

	// implements javax.swing.ComboBoxModel
	/**
	 * Set the value of the selected item. The selected item may be null. Make
	 * sure {@code anObject} is an instance of T otherwise a ClassCastException
	 * will be thrown.
	 * <p>
	 * 
	 * @param anObject
	 *            The combo box value or null for no selection.
	 */
	@Override
	public void setSelectedItem(Object anObject) {
		if ((selectedObject != null && !selectedObject.equals(anObject))
				|| selectedObject == null && anObject != null) {
			selectedObject = (T) anObject;
			fireContentsChanged(this, -1, -1);
		}
	}

	// implements javax.swing.ComboBoxModel
	@Override
	public T getSelectedItem() {
		return selectedObject;
	}

	// implements javax.swing.ListModel
	@Override
	public int getSize() {
		return objects.size();
	}

	// implements javax.swing.ListModel
	@Override
	public T getElementAt(int index) {
		if (index >= 0 && index < objects.size()) {
			return objects.get(index);
		} else {
			return null;
		}
	}

	/**
	 * Returns the index-position of the specified object in the list.
	 * 
	 * @param anObject
	 * @return an int representing the index position, where 0 is the first
	 *         position
	 */
	public int getIndexOf(T anObject) {
		return objects.indexOf(anObject);
	}

	// implements javax.swing.MutableComboBoxModel
	public void addElement(T anObject) {
		objects.add(anObject);
		fireIntervalAdded(this, objects.size() - 1, objects.size() - 1);
		if (objects.size() == 1 && selectedObject == null && anObject != null) {
			setSelectedItem(anObject);
		}
	}

	// implements javax.swing.MutableComboBoxModel
	public void insertElementAt(T anObject, int index) {
		objects.add(index, anObject);
		fireIntervalAdded(this, index, index);
	}

	// implements javax.swing.MutableComboBoxModel
	public void removeElementAt(int index) {
		if (getElementAt(index) == selectedObject) {
			if (index == 0) {
				setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
			} else {
				setSelectedItem(getElementAt(index - 1));
			}
		}

		objects.remove(index);

		fireIntervalRemoved(this, index, index);
	}

	// implements javax.swing.MutableComboBoxModel
	public void removeElement(T anObject) {
		int index = objects.indexOf(anObject);
		if (index != -1) {
			removeElementAt(index);
		}
	}

	/**
	 * Empties the list.
	 */
	public void removeAllElements() {
		if (objects.size() > 0) {
			int firstIndex = 0;
			int lastIndex = objects.size() - 1;
			objects.clear();
			selectedObject = null;
			fireIntervalRemoved(this, firstIndex, lastIndex);
		} else {
			selectedObject = null;
		}
	}

	@Override
	public void add(List<T> elementsToAdd) {
		objects.addAll(elementsToAdd);
		fireContentsChanged(this, -1, -1);

	}

	@Override
	public List<T> getElements() {
		return objects;
	}
}
