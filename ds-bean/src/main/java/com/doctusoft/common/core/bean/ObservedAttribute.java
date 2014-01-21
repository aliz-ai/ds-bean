package com.doctusoft.common.core.bean;

public interface ObservedAttribute<E, T> extends Attribute<E, T> {

	ListenerRegistration addChangeListener(E object, ValueChangeListener<T> valueChangeListener);
}
