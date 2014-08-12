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


import com.doctusoft.bean.BeanPropertyChangeListener;
import com.doctusoft.bean.GenericListeners;
import com.doctusoft.bean.Property;

public class BeanPropertyListeners<Bean> extends GenericListeners<BeanPropertyChangeListener<Bean>> {

	public <T> void fireListeners(final Bean bean, final Property<Bean, T> property, final T value) {
		forEachListener(new ListenerCallback<BeanPropertyChangeListener<Bean>>() {
			@Override
			public void apply(BeanPropertyChangeListener<Bean> listener) {
				listener.propertyChanged(bean, property, value);
			}
		});
	}


}
