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

public class OneWayBindingImpl<T> implements ListenerRegistration {
	
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
	public void removeHandler() {
		sourceListener.removeHandler();
	}

}
