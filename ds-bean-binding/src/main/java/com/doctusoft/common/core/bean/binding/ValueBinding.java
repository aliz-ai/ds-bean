package com.doctusoft.common.core.bean.binding;

public interface ValueBinding<T>  {
	
	T getValue();
	
	void setValue(T value);
	
}
