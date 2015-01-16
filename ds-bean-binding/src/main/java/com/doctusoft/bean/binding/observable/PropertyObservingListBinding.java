package com.doctusoft.bean.binding.observable;

import java.util.List;

import com.doctusoft.bean.ListenerRegistration;
import com.doctusoft.bean.ObservableProperty;
import com.doctusoft.bean.ValueChangeListener;

public abstract class PropertyObservingListBinding<T> extends AbstractObservingListBinding<T> {
	
	private ObservableProperty<T, ?> property;

	public PropertyObservingListBinding(ObservableValueBinding<? extends List<T>> listBinding, ObservableProperty<T, ?> property) {
		super(listBinding);
		this.property = property;
		init();
	}
	
	@Override
	protected ListenerRegistration listenToItemChanges(final ObservableList<T> list, final T element) {
		return property.addChangeListener(element, (ValueChangeListener) new ValueChangeListener<Object>() {
			@Override
			public void valueChanged(Object newValue) {
				itemChanged(list, element);
			}
		});
	}

}
