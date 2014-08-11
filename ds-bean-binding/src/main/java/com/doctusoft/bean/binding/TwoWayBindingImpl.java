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


import com.doctusoft.bean.ListenerRegistration;
import com.doctusoft.bean.ValueChangeListener;
import com.doctusoft.bean.binding.observable.ObservableValueBinding;

public class TwoWayBindingImpl<T> implements BindingRegistration {

	private ListenerRegistration sourceListener;
	private ListenerRegistration targetListener;
	private boolean ignoreSourceValue; 
	private T sourceValueToIgnore;
	private boolean ignoreTargetValue;
	private T targetValueToIgnore;
	private T lastSourceValue;
	private T lastTargetValue;
	public TwoWayBindingImpl(final ObservableValueBinding<T> source, final ObservableValueBinding<T> target) {
		sourceValueToIgnore = source.getValue();
		targetValueToIgnore = sourceValueToIgnore;
		lastSourceValue = sourceValueToIgnore;
		lastTargetValue = sourceValueToIgnore;
		target.setValue(sourceValueToIgnore);
		sourceListener = source.addValueChangeListener(new ValueChangeListener<T>() {
			@Override
			public void valueChanged(T newValue) {
				lastSourceValue = newValue;
				if (newValue == sourceValueToIgnore && ignoreSourceValue) {
					// this is just a back hook notification
					sourceValueToIgnore = null;
					ignoreSourceValue = false;
				} else {
					if (lastTargetValue != newValue) {
						targetValueToIgnore = newValue;
						ignoreTargetValue = true;
						lastTargetValue = newValue;
						target.setValue(newValue);
					}
				}
			}
		});
		targetListener = target.addValueChangeListener(new ValueChangeListener<T>() {
			@Override
			public void valueChanged(T newValue) {
				lastTargetValue = newValue;
				if (newValue == targetValueToIgnore && ignoreTargetValue) {
					// this is just a back hook notification
					targetValueToIgnore = null;
					ignoreTargetValue = false;
				} else {
					if (lastSourceValue != newValue) {
						sourceValueToIgnore = newValue;
						ignoreSourceValue = true;
						lastSourceValue = newValue;
						source.setValue(newValue);
					}
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
