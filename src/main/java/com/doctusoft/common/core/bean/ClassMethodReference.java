package com.doctusoft.common.core.bean;

public abstract class ClassMethodReference<Cls, ReturnType> {
	
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
}
