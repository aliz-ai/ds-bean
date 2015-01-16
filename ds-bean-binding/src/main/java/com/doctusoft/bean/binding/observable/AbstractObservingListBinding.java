package com.doctusoft.bean.binding.observable;

import java.util.List;
import java.util.Map;

import com.doctusoft.bean.ListenerRegistration;
import com.google.common.collect.Maps;

public abstract class AbstractObservingListBinding<T> {
	
	private Map<T, ListenerRegistration> listeners = Maps.newIdentityHashMap();
	private ObservableValueBinding<? extends List<T>> listBinding;
	
	public AbstractObservingListBinding(ObservableValueBinding<? extends List<T>> listBinding) {
		this.listBinding = listBinding;
	}
	
	protected void init() {
		new ListBindingListener<T>(listBinding) {
			@Override
			public void inserted(ObservableList<T> list, int index, T element) {
				if (listeners.containsKey(element))	// if this item is already in the list, we already listen to its changes
					return;
				if (element == null)
					return;
				ListenerRegistration registration = listenToItemChanges(list, element);
				if (registration != null) {
					listeners.put(element, registration);
				}
			}
			@Override
			public void removed(ObservableList<T> list, int index, T element) {
				if (listeners.containsKey(element)) {
					listeners.get(element).removeHandler();
					listeners.remove(element);
				}
			}
		};
	}
	
	protected abstract void itemChanged(ObservableList<T> list, T element);
	
	protected abstract ListenerRegistration listenToItemChanges(ObservableList<T> list, T element);
	
}
