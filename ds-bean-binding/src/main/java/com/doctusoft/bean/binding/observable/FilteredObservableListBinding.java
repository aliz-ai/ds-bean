package com.doctusoft.bean.binding.observable;

import java.util.List;

import com.google.common.base.Predicate;

/**
 * Provides an observable filtered view on an observable list binding. For example, given an {@link ObservableList} of Person entities
 * you can create a view of retired people by giving a predicate that filters for them. You will receive inserted and removed events
 * only for those that match the given predicate 
 */
public abstract class FilteredObservableListBinding<T> {
	
	public FilteredObservableListBinding(ObservableValueBinding<? extends List<T>> listBinding, final Predicate<T> filterPredicate) {
		new ListBindingListener<T>(listBinding) {
			@Override
			public void inserted(ObservableList<T> list, int index, T element) {
				if (filterPredicate.apply(element)) {
					FilteredObservableListBinding.this.inserted(list, index, element);
				}
			}
			@Override
			public void removed(ObservableList<T> list, int index, T element) {
				if (filterPredicate.apply(element)) {
					FilteredObservableListBinding.this.removed(list, index, element);
				}
			}
		};
	}

	protected abstract void inserted(ObservableList<T> list, int index, T element);

	protected abstract void removed(ObservableList<T> list, int index, T element);

}
