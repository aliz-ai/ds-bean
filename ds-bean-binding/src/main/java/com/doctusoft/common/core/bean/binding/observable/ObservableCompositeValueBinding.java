package com.doctusoft.common.core.bean.binding.observable;

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


import java.util.List;

import com.doctusoft.bean.ListenerRegistration;
import com.doctusoft.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.CompositeValueBinding;
import com.google.common.collect.Lists;

public abstract class ObservableCompositeValueBinding<Source, Target> extends CompositeValueBinding<Source, Target> implements ObservableValueBinding<Target>{
	
	protected final List<ValueChangeListener<Target>> valueChangeListeners = Lists.newArrayList();
	
	public ObservableCompositeValueBinding(ObservableValueBinding<? extends Source> sourceBinding) {
		super(sourceBinding);
	}

	protected void fireListeners(Target value) {
		for (ValueChangeListener<Target> listener : valueChangeListeners) {
			listener.valueChanged(value);
		}
	}
	
	@Override
	public ListenerRegistration addValueChangeListener(final ValueChangeListener<Target> listener) {
		class BindingListenerRegistration implements ListenerRegistration {
			@Override
			public void removeHandler() {
				valueChangeListeners.remove(listener);
			}
		}
		valueChangeListeners.add(listener);
		return new BindingListenerRegistration();
	}
}
