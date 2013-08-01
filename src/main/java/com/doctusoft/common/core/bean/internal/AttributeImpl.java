/*
 * Copyright (c) 2011 - 2013 Doctusoft. All rights reserved.
 */

package com.doctusoft.common.core.bean.internal;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.doctusoft.common.core.bean.Attribute;

import static com.google.common.base.Preconditions.*;

/**
 * AttributeImpl<SubjectClass, PropertyType>
 * 
 * @since 3.0.0
 */
@lombok.Getter
@lombok.EqualsAndHashCode( of = { "parent", "name", "type" }, doNotUseGetters = true )
public class AttributeImpl<E, T> implements Attribute<E, T> {

	protected final Class<E> parent;
	protected final String name;
	protected final Class<T> type;

	public AttributeImpl( final Class<E> parent, final String name, final Class<T> type ) {
		this.parent = parent;
		this.name = name;
		this.type = type;
		// TODO ? check actual type?
	}

	@Override
	public T getValue( final E instance ) {

		if (instance == null) {
			return null;
		}

		try {
			return (T) PropertyUtils.getProperty( instance, name );
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException( e );
		}
		catch (NoSuchMethodException e) {
			throw new RuntimeException( e );
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException( e );
		}
	}

	@Override
	public void setValue( final E instance, final T value ) {
		checkNotNull( instance );

		try {
			PropertyUtils.setProperty( instance, name, value );
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException( e );
		}
		catch (NoSuchMethodException e) {
			throw new RuntimeException( e );
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException( e );
		}
	}

}
