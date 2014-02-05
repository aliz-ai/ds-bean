package com.doctusoft.common.core.bean.binding;

import com.doctusoft.common.core.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.observable.ObservableChainedValueBindingBuilder;
import com.doctusoft.common.core.bean.binding.observable.ObservableValueBinding;

public class Bindings {
	
	/**
	 * Creates a normal (non-observable) binding builder on the specified root object 
	 */
	public static <R> ValueBindingBuilder<R> on(R root) {
		return ValueBindingBuilder.on(root);
	}
	
	/**
	 * Creates an observable binding builder on the specified root object 
	 */
	public static <R> ObservableChainedValueBindingBuilder<R> obs(R root) {
		return ObservableChainedValueBindingBuilder.on(root);
	}

	/**
	 * Sets the target binding value to that of the source binding and ensures that later changes on the source binding
	 * are also propagated to the target. 
	 */
	public static <T> void bind(final ObservableValueBinding<T> sourceBinding, final ValueBinding<T> targetBinding) {
		// set current value
		targetBinding.setValue(sourceBinding.getValue());
		// listen to changes
		sourceBinding.addValueChangeListener(new ValueChangeListener<T>() {
			@Override
			public void valueChanged(T value) {
				targetBinding.setValue(value);
			}
		});
		
	}
}
