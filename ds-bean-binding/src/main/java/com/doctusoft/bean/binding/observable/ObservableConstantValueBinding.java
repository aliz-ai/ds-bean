package com.doctusoft.bean.binding.observable;

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

/**
 * Well, quite an oximoron, but I hope you get the idea :) 
 */
public class ObservableConstantValueBinding<Root> implements ObservableValueBinding<Root> {

	private final Root root;

	public ObservableConstantValueBinding(Root root) {
		this.root = root;
	}
	
	@Override
	public Root getValue() {
		return root;
	}
	
	public void setValue(Root value) {
		throw new UnsupportedOperationException("Root value binding cannot be assigned");
	}
	
	@Override
	public ListenerRegistration addValueChangeListener(ValueChangeListener<Root> listener) {
		// do nothing, it's never invoked
		return new ListenerRegistration() {
			@Override
			public void removeHandler() {
				// do nothing
			}
		};
	}
}
