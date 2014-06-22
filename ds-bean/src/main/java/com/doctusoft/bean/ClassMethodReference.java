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


public abstract class ClassMethodReference<Cls, ReturnType> implements Serializable {
	
//	public ObjectMethodReference<Cls, ReturnType> on(Cls object) {
//		return ObjectMethodReference.on(this, object);
//	}
	
	public abstract ReturnType applyInner(Cls object, Object [] arguments);
	
	public ObjectMethodReference<Cls, ReturnType> on(Cls object) {
		return new ObjectMethodReference<Cls, ReturnType>(object, this);
	}

	public ReturnType apply(Cls object, Object ... arguments) {
		return applyInner(object, arguments);
	}
	
	public abstract String getName();
	
	public abstract Class<Cls> getParent();
	
	public abstract Class<ReturnType> getReturnType();
}
