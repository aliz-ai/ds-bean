/*
 * Copyright (c) 2011 - 2013 Doctusoft. All rights reserved.
 */

package com.doctusoft.common.core.bean;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Function;

/**
 *
 * @since 3.0.0
 */
public final class Attributes {

	private Attributes() {}

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
