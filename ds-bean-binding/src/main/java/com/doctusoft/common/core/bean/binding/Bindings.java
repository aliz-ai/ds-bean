package com.doctusoft.common.core.bean.binding;

/*
 * #%L
 * ds-bean-binding
 * %%
 * Copyright (C) 2014 Doctusoft Ltd.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
