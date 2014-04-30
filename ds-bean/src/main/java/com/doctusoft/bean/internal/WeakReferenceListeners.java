package com.doctusoft.bean.internal;

import java.util.Map;

import com.doctusoft.bean.ListenerRegistration;
import com.doctusoft.bean.ValueChangeListener;
import com.google.common.collect.MapMaker;

/**
 * PropertyListeners should normally be private fields of the model objects.
 * Now it's technically easier to keep property listeners on the static ObservableProperty instances,
 * so we keep the propertylisteners with weak references on the target object 
 */
public class WeakReferenceListeners<Holder, T> {
	
	Map<Holder, PropertyListeners<T>> listeners = new MapMaker().weakKeys().makeMap();
	
	public ListenerRegistration addListener(final Holder object, final ValueChangeListener<T> listener) {
		PropertyListeners<T> propertyListeners = listeners.get(object);
		if (propertyListeners == null) {
			propertyListeners = new PropertyListeners<T>();
			listeners.put(object, propertyListeners);
		}
		return propertyListeners.addListener(listener);
	}

	public void fireListeners(final Holder object, final T newValue) {
		PropertyListeners<T> propertyListeners = listeners.get(object);
		if (propertyListeners != null) {
			propertyListeners.fireListeners(newValue);
		}
	}

}
