package com.doctusoft.common.core.bean.internal;

/*
 * #%L
 * ds-bean
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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;

public class AttributeListeners<T> implements Serializable {
	
	protected transient List<ValueChangeListener<T>> listeners = null;
	
	public ListenerRegistration addListener(final ValueChangeListener<T> listener) {
		if (listeners == null) {
			listeners = new ArrayList<ValueChangeListener<T>>();
		}
		listeners.add(listener);
		class AttributeListenerRegistration implements ListenerRegistration {
			
			@Override
			public void removeHandler() {
				listeners.remove(listener);
			}
		};
		return new AttributeListenerRegistration();
	}
	
	public void fireListeners(final T newValue) {
		if (listeners != null) {
			// value change listeners might remove themselves, so we prevent ConcurrentModificationExceptions by copying the list first
			for (ValueChangeListener<T> listener : new ArrayList<ValueChangeListener<T>>(listeners)) {
				if (listener != null) {
					listener.valueChanged(newValue);
				}
			}
		}
	}

}
