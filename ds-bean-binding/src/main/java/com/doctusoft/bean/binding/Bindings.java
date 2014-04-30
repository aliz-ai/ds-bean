package com.doctusoft.bean.binding;

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


import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference1;
import com.doctusoft.bean.binding.observable.ObservableChainedValueBindingBuilder;
import com.doctusoft.bean.binding.observable.ObservableValueBinding;
import com.google.common.base.Function;

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
	 * Propagates the source binding value to the target binding. If the source binding is observable,
	 * then any change on it will be propagated to the target binding. If the target binding is also observable,
	 * a two-way binding will be created, and changes on any side will be propagated to the other, avoiding
	 * infinite loops by not propagating values that are already present. Values are compared with their .equals methods.
	 * 
	 * @return a {@link BindingRegistration} that can be used to unbind (remove propagating change listeners), if necessary
	 */
	public static <T> BindingRegistration bind(final ValueBinding<T> sourceBinding, final ValueBinding<T> targetBinding) {
		// set current value
		targetBinding.setValue(sourceBinding.getValue());
		// listen to changes
		if (sourceBinding instanceof ObservableValueBinding<?> && !(targetBinding instanceof ObservableValueBinding<?>)) {
			return new OneWayBindingImpl<T>((ObservableValueBinding<T>) sourceBinding, targetBinding);
		}
		if (sourceBinding instanceof ObservableValueBinding<?> && (targetBinding instanceof ObservableValueBinding<?>)) {
			return new TwoWayBindingImpl<T>((ObservableValueBinding<T>) sourceBinding, (ObservableValueBinding<T>) targetBinding);
		}
		// return an empty registration by default
		return new BindingRegistration() {
			@Override
			public void unbind() {
				// do nothing
			}
		};
	}

	public static <Target, Value> Function<Target, Value> functionOf(final ObjectMethodReference1<?, Value, Target> objectMethodRef) {
		return new Function<Target, Value>() {
			@Override
			public Value apply(Target target) {
				return objectMethodRef.apply(target);
			}
		};
	}
}
