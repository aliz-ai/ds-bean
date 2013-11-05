/*
 * Copyright (c) 2011 - 2013 Doctusoft. All rights reserved.
 */

package com.doctusoft.common.core.bean;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.doctusoft.common.core.bean.internal.AttributeImpl;
import com.google.common.base.Function;
import com.google.common.base.Strings;

/**
 *
 * @since 3.0.0
 */
public final class Attributes {

	private Attributes() {}

	/**
	 * @deprecated this implementation relies on {@link AttributeImpl} that uses reflection. This
	 *   solution has been replaced with inline code generation using lombok-ds.
	 */
	@Deprecated
	public static <E, T> Attribute<E, T> of( final Class<E> parent, final String name, final Class<T> type ) {
		checkNotNull( parent );
		checkArgument( !Strings.isNullOrEmpty( name ) );

		return new AttributeImpl<E, T>(parent, name, type);
	}

	public static <E, T> Function<E, T> functionOf( final Attribute<E, T> attribute ) {
		return new AttributeFunction<E, T>( attribute );
	}

	private static class AttributeFunction<E, T> implements Function<E, T> {

		private final Attribute<E, T> attribute;

		public AttributeFunction( final Attribute<E, T> attribute ) {
			checkNotNull( attribute );
			this.attribute = attribute;
		}

		@Override
		public T apply( final E input ) {
			return attribute.getValue( input );
		}
	}

}
