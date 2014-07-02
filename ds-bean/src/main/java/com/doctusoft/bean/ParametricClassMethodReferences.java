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
 * 
 */
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference0;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference1;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference2;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference3;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference4;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference5;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference6;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference7;
import com.doctusoft.bean.ParametricObjectMethodReferences.ObjectMethodReference8;

/**
 * Specialized {@link ClassMethodReference} subclasses to support type-safe parameters.
 * <p>
 * Well, this one might not be nice at first look, but this seems to only way to properly support parameter type safety.
 * With the help of these classes you can write for example:
 * </p>
 * <pre>
 *     public &lt;Item&gt; void bindItemClick(ClassMethodReference1&lt;Presenter, Void, ? super Item&gt; methodRef) { ... };
 * </pre>
 * thus not only the containing type (Presenter) and the return type (Void) of the given method is ensured, but also
 * the parameters (only one parameter, that corresponds the type of the clicked item). 
 */
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

	public abstract static class ClassMethodReference3<Cls, ReturnType, Param0, Param1, Param2> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0, Param1 param1, Param2 param2) {
			return apply(object, param0, param1, param2);
		}
		@Override
		public ObjectMethodReference3<Cls, ReturnType, Param0, Param1, Param2> on(Cls object) {
			return new ObjectMethodReference3<Cls, ReturnType, Param0, Param1, Param2>(object, this);
		}
	}

	public abstract static class ClassMethodReference4<Cls, ReturnType, Param0, Param1, Param2, Param3> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0, Param1 param1, Param2 param2, Param3 param3) {
			return apply(object, param0, param1, param2, param3);
		}
		@Override
		public ObjectMethodReference4<Cls, ReturnType, Param0, Param1, Param2, Param3> on(Cls object) {
			return new ObjectMethodReference4<Cls, ReturnType, Param0, Param1, Param2, Param3>(object, this);
		}
	}

	public abstract static class ClassMethodReference5<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4) {
			return apply(object, param0, param1, param2, param3, param4);
		}
		@Override
		public ObjectMethodReference5<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4> on(Cls object) {
			return new ObjectMethodReference5<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4>(object, this);
		}
	}

	public abstract static class ClassMethodReference6<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4, Param5 param5) {
			return apply(object, param0, param1, param2, param3, param4, param5);
		}
		@Override
		public ObjectMethodReference6<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5> on(Cls object) {
			return new ObjectMethodReference6<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5>(object, this);
		}
	}

	public abstract static class ClassMethodReference7<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4, Param5 param5, Param6 param6) {
			return apply(object, param0, param1, param2, param3, param4, param5, param6);
		}
		@Override
		public ObjectMethodReference7<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6> on(Cls object) {
			return new ObjectMethodReference7<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6>(object, this);
		}
	}

	public abstract static class ClassMethodReference8<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6, Param7> extends ClassMethodReference<Cls, ReturnType> {
		public ReturnType invoke(Cls object, Param0 param0, Param1 param1, Param2 param2, Param3 param3, Param4 param4, Param5 param5, Param6 param6, Param7 param7) {
			return apply(object, param0, param1, param2, param3, param4, param5, param6, param7);
		}
		@Override
		public ObjectMethodReference8<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6, Param7> on(Cls object) {
			return new ObjectMethodReference8<Cls, ReturnType, Param0, Param1, Param2, Param3, Param4, Param5, Param6, Param7>(object, this);
		}
	}

}
