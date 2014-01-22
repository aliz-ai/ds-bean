package com.doctusoft.common.core.bean.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;

public class AttributeListeners<T> implements Serializable {
	
	protected transient List<ValueChangeListener<T>> listeners = null;
	
	public ListenerRegistration addListener(final ValueChangeListener<T> listener) {
		if (listeners == null) {
			listeners = new ArrayList<ValueChangeListener<T>>();
		}
		listeners.add(listener);
		class AttributeListenerRegistration implements ListenerRegistration {
			
			@Override
			public void removeHandler() {
				listeners.remove(listener);
			}
		};
		return new AttributeListenerRegistration();
	}
	
	public void fireListeners(final T newValue) {
		if (listeners != null) {
			// value change listeners might remove themselves, so we prevent ConcurrentModificationExceptions by copying the list first
			for (ValueChangeListener<T> listener : new ArrayList<ValueChangeListener<T>>(listeners)) {
				if (listener != null) {
					listener.valueChanged(newValue);
				}
			}
		}
	}

}
