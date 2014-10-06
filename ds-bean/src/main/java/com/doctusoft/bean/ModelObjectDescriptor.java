package com.doctusoft.bean;

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


public interface ModelObjectDescriptor<Bean> {
	
	/**
	 * @return a list of {@link ObservableProperty}-s on the class, including those from superclasses as well. 
	 */
	Iterable<ObservableProperty<?, ?>> getObservableProperties();
	
	/**
	 * Registers the given listener on the bean instance. 
	 * @return you can use this interface to unregister the attached listener from this bean.
	 */
	ListenerRegistration addBeanChangeListener(Bean bean, BeanPropertyChangeListener<Bean> listener);
}
