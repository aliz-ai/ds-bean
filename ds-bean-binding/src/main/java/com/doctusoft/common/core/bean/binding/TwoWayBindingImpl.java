package com.doctusoft.common.core.bean.binding;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.observable.ObservableValueBinding;
import com.google.common.base.Objects;

public class TwoWayBindingImpl<T> implements BindingRegistration {

	private ListenerRegistration sourceListener;
	private ListenerRegistration targetListener;
	private T lastSourceValue;
	private T lastTargetValue;
	public TwoWayBindingImpl(final ObservableValueBinding<T> source, final ObservableValueBinding<T> target) {
		lastSourceValue = source.getValue();
		target.setValue(lastSourceValue);
		lastTargetValue = lastSourceValue;
		sourceListener = source.addValueChangeListener(new ValueChangeListener<T>() {
			@Override
			public void valueChanged(T newValue) {
				lastSourceValue = newValue;
				if (!Objects.equal(lastTargetValue, newValue)) {
					target.setValue(newValue);
					lastTargetValue = newValue;
				}
			}
		});
		targetListener = target.addValueChangeListener(new ValueChangeListener<T>() {
			@Override
			public void valueChanged(T newValue) {
				lastTargetValue = newValue;
				if (!Objects.equal(lastSourceValue, newValue)) {
					source.setValue(newValue);
					lastSourceValue = newValue;
				}
			}
		});
	}
	@Override
	public void unbind() {
		sourceListener.removeHandler();
		targetListener.removeHandler();
	}
}
