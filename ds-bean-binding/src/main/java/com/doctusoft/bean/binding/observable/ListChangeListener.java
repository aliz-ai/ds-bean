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

public abstract class ListChangeListener extends ListBindingListener<Object> {
	
	public ListChangeListener(ObservableValueBinding<? extends List<?>> listBinding) {
		super((ObservableValueBinding) listBinding);
	}
	
	protected abstract void changed();
	
	@Override
	public void inserted(ObservableList<Object> list, int index, Object element) {
		changed();
	}
	
	@Override
	public void removed(ObservableList<Object> list, int index, Object element) {
		changed();
	}

}
