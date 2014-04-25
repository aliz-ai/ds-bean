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


import com.doctusoft.bean.ListenerRegistration;
import com.doctusoft.bean.ValueChangeListener;
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
