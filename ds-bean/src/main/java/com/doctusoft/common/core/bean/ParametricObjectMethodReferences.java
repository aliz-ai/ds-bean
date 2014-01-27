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


public abstract class ParametricObjectMethodReferences {
	
	public static class ObjectMethodReference0<Cls, ReturnType> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference0(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke() {
			return apply();
		}
	}

	public static class ObjectMethodReference1<Cls, ReturnType, Param0> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference1(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0) {
			return apply(param0);
		}
	}

	public static class ObjectMethodReference2<Cls, ReturnType, Param0, Param1> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference2(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0, Param1 param1) {
			return apply(param0, param1);
		}
	}
}
