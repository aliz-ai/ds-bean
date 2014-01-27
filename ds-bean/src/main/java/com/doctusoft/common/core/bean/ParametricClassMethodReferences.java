package com.doctusoft.common.core.bean;

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


import com.doctusoft.common.core.bean.ParametricObjectMethodReferences.ObjectMethodReference0;
import com.doctusoft.common.core.bean.ParametricObjectMethodReferences.ObjectMethodReference1;
import com.doctusoft.common.core.bean.ParametricObjectMethodReferences.ObjectMethodReference2;

public abstract class ParametricClassMethodReferences {
	
	public abstract static class ClassMethodReference0<Cls, ReturnType> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object) {
			return apply(object);
		}
		@Override
		public ObjectMethodReference0<Cls, ReturnType> on(Cls object) {
			return new ObjectMethodReference0<Cls, ReturnType>(object, this);
		}
	}

	public abstract static class ClassMethodReference1<Cls, ReturnType, Param0> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0) {
			return apply(object, param0);
		}
		@Override
		public ObjectMethodReference1<Cls, ReturnType, Param0> on(Cls object) {
			return new ObjectMethodReference1<Cls, ReturnType, Param0>(object, this);
		}
	}

	public abstract static class ClassMethodReference2<Cls, ReturnType, Param0, Param1> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0, Param1 param1) {
			return apply(object, param0, param1);
		}
		@Override
		public ObjectMethodReference2<Cls, ReturnType, Param0, Param1> on(Cls object) {
			return new ObjectMethodReference2<Cls, ReturnType, Param0, Param1>(object, this);
		}
	}
}
