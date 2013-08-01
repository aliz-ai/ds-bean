/* Copyright (c) 2011 - 2013 Doctusoft. All rights reserved. */

package com.doctusoft.common.core.bean;

/**
 *
 * @since 3.0.0
 */
public interface Attribute<E, T> {

	T getValue( E instance );

	void setValue( E instance, T value );

	Class<E> getParent();

	Class<T> getType();

	String getName();

}
