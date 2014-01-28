package com.doctusoft.common.core.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericListeners<Listener> implements Serializable {

	protected transient List<Listener> listeners = null;

	public ListenerRegistration addListener(final Listener listener) {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		listeners.add(listener);
		class GenericListenerRegistration implements ListenerRegistration {
			
			@Override
			public void removeHandler() {
				listeners.remove(listener);
			}
		};
		return new GenericListenerRegistration();
	}
	
	protected interface ListenerCallback<T> {
		void apply(T listener);
	}
	
	protected void forEachListener(final ListenerCallback<Listener> callback) {
		if (listeners != null) {
			// value change listeners might remove themselves, so we prevent ConcurrentModificationExceptions by copying the list first
			for (Listener listener : new ArrayList<Listener>(listeners)) {
				if (listener != null) {
					callback.apply(listener);
				}
			}
		}
	}
	
}
