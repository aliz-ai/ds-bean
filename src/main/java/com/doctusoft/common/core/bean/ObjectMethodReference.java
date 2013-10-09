package com.doctusoft.common.core.bean;

public class ObjectMethodReference<Cls, ReturnType> {
	
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
