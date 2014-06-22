package com.doctusoft.bean;

import java.io.Serializable;

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


public class ObjectMethodReference<Cls, ReturnType> implements Serializable {
	
	private final ClassMethodReference<Cls, ReturnType> ref;
	private final Cls object;
	
	public ObjectMethodReference(Cls object, ClassMethodReference<Cls, ReturnType> ref) {
		this.object = object;
		this.ref = ref;
	}
	
	public ReturnType apply(Object ... arguments) {
		return ref.apply(object, arguments);
	}

}
