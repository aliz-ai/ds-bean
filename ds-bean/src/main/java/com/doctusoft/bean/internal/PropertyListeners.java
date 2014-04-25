package com.doctusoft.bean.internal;

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

import com.doctusoft.bean.GenericListeners;
import com.doctusoft.bean.ValueChangeListener;

public class PropertyListeners<T> extends GenericListeners<ValueChangeListener<T>> {
	
	public void fireListeners(final T newValue) {
		forEachListener(new ListenerCallback<ValueChangeListener<T>>() {
			@Override
			public void apply(ValueChangeListener<T> listener) {
				listener.valueChanged(newValue);
			}
		});
	}

}
