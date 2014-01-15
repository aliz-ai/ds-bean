package com.doctusoft.common.core.bean;

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
