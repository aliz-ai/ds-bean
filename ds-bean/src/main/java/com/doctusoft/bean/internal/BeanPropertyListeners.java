package com.doctusoft.bean.internal;

import com.doctusoft.bean.BeanPropertyChangeListener;
import com.doctusoft.bean.GenericListeners;
import com.doctusoft.bean.Property;

public class BeanPropertyListeners<Bean> extends GenericListeners<BeanPropertyChangeListener<Bean>> {

	public <T> void fireListeners(final Bean bean, final Property<Bean, T> property, final T value) {
		forEachListener(new ListenerCallback<BeanPropertyChangeListener<Bean>>() {
			@Override
			public void apply(BeanPropertyChangeListener<Bean> listener) {
				listener.propertyChanged(bean, property, value);
			}
		});
	}


}
