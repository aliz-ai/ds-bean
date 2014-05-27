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


import java.util.List;

import com.doctusoft.bean.ListenerRegistration;
import com.doctusoft.bean.ValueChangeListener;
import com.doctusoft.bean.binding.observable.ObservableList.ListElementInsertedListener;
import com.doctusoft.bean.binding.observable.ObservableList.ListElementRemovedListener;

/**
 * Normally delegates inserted and removed events to the methods you implement, but if the entire list instance changes
 * it translates the change to series of inserted and removed events
 */
public abstract class ListBindingListener<T> {
	
	private ListenerRegistration insertListener;
	private ListenerRegistration deleteListener;
	
	private ObservableList<T> shadowList = new ObservableList<T>();
	
	public ListBindingListener(ObservableValueBinding<? extends List<T>> listBinding) {
		List<T> items = listBinding.getValue();
		if (items != null) {
			valueChanged(items);
		}
		listBinding.addValueChangeListener((ValueChangeListener) new ValueChangeListener<List<T>>() {
			@Override
			public void valueChanged(List<T> newValue) {
				ListBindingListener.this.valueChanged(newValue);
			}
		});
	}
	
	protected void valueChanged(List<T> items) {
		while (shadowList.size() > 0) {
			removed(shadowList, 0, shadowList.remove(0));
		}
		if (items != null) {
			for (int i = 0; i < items.size(); i ++) {
				T element = items.get(i);
				shadowList.add(element);
				inserted(shadowList, i, element);
			}
		}
		bindListListeners(items);
	}

	protected void bindListListeners(List<T> items) {
		if (insertListener != null) {
			insertListener.removeHandler();
		}
		if (deleteListener != null) {
			deleteListener.removeHandler();
		}
		if (items != null && items instanceof ObservableList<?>) {
			ObservableList<T> observableList = (ObservableList<T>) items;
			// TODO make this reusable in ds-bean-binding
			insertListener = observableList.addInsertListener(new ListElementInsertedListener<T>() {
				@Override
				public void inserted(ObservableList<T> list, int index, T element) {
					shadowList.add(index, element);
					ListBindingListener.this.inserted(shadowList, index, element);
				}
			});
			deleteListener = observableList.addDeleteListener(new ListElementRemovedListener<T>() {
				@Override
				public void removed(ObservableList<T> list, int index, T element) {
					shadowList.remove(index);
					ListBindingListener.this.removed(shadowList, index, element);
				}
			});
		}
	}

	public abstract void inserted(ObservableList<T> list, int index, T element);
	
	public abstract void removed(ObservableList<T> list, int index, T element);


}
