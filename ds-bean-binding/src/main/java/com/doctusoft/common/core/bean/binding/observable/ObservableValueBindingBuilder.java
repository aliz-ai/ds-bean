package com.doctusoft.common.core.bean.binding.observable;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.Bindings;
import com.doctusoft.common.core.bean.binding.ValueBinding;

public class ObservableValueBindingBuilder<T> implements ObservableValueBinding<T> {

	protected ObservableValueBinding<T> source;
	
	@Override
	public ListenerRegistration addValueChangeListener(ValueChangeListener<T> listener) {
		return source.addValueChangeListener(listener);
	}
	
	public void bindTo(final ValueBinding<T> targetBinding) {
		Bindings.bind(this, targetBinding);
	}
	
	@Override
	public T getValue() {
		return source.getValue();
	}
	
	public void setValue(T value) {
		source.setValue(value);
	}
}
