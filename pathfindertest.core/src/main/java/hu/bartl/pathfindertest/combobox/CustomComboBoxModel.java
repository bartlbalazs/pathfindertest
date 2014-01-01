package hu.bartl.pathfindertest.combobox;

import java.util.List;

import javax.swing.ComboBoxModel;

/**
 * Custom Implementation of {@code ComboBoxModel} to allow adding a list of
 * elements to the list.
 */
public interface CustomComboBoxModel<T> extends ComboBoxModel {

	void add(List<T> elementsToAdd);

	List<T> getElements();

}
