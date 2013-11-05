package com.doctusoft.common.core.bean;

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
