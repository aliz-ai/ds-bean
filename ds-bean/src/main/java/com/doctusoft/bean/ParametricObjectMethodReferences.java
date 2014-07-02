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


/**
 * Specialized {@link ObjectMethodReference} subclasses to support type-safe parameters, see {@link ParametricClassMethodReferences} 
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

	public static class ObjectMethodReference3<Cls, ReturnType, Param0, Param1, Param2> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference3(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0, Param1 param1, Param2 param2) {
			return apply(param0, param1, param2);
		}
	}

	public static class ObjectMethodReference4<Cls, ReturnType, Param0, Param1, Param2, Param3> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference4(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0, Param1 param1, Param2 param2, Param3 param3) {
			return apply(param0, param1, param2, param3);
		}
	}
	
	public static class ObjectMethodReference5<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference5(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4) {
			return apply(param0, param1, param2, param3, param4);
		}
	}

	public static class ObjectMethodReference6<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference6(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4, Param5 param5) {
			return apply(param0, param1, param2, param3, param4, param5);
		}
	}

	public static class ObjectMethodReference7<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference7(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4, Param5 param5, Param6 param6) {
			return apply(param0, param1, param2, param3, param4, param5, param6);
		}
	}

	public static class ObjectMethodReference8<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6, Param7> extends ObjectMethodReference<Cls, ReturnType> {
		public ObjectMethodReference8(Cls object,
				ClassMethodReference<Cls, ReturnType> ref) {
			super(object, ref);
		}

		public ReturnType invoke(Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4, Param5 param5, Param6 param6, Param7 param7) {
			return apply(param0, param1, param2, param3, param4, param5, param6, param7);
		}
	}
}
