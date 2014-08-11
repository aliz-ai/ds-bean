package com.doctusoft.bean;

public interface BeanPropertyChangeListener<Bean> {
	
	<T> void propertyChanged(Bean bean, Property<Bean, T> property, T value);
}
