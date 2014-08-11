package com.doctusoft.bean;

public interface ModelObjectDescriptor<Bean> {
	
	Iterable<ObservableProperty<?, ?>> getObservableProperties();
	
	ListenerRegistration addBeanChangeListener(Bean bean, BeanPropertyChangeListener<Bean> listener);
}
