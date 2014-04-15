package com.doctusoft.common.core.bean.binding;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.observable.ObservableValueBinding;

public class OneWayBindingImpl<T> implements BindingRegistration {
	
	private ListenerRegistration sourceListener;

	public OneWayBindingImpl(ObservableValueBinding<T> sourceBinding, final ValueBinding<T> targetBinding) {
		sourceListener = sourceBinding.addValueChangeListener(new ValueChangeListener<T>() {
			@Override
			public void valueChanged(T newValue) {
				targetBinding.setValue(newValue);
			}
		});
	}
	
	@Override
	public void unbind() {
		sourceListener.removeHandler();
	}

}
