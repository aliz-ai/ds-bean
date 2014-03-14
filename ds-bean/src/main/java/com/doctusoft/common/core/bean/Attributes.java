package com.doctusoft.common.core.bean;

/*
 * #%L
 * ds-bean
 * %%
 * Copyright (C) 2014 Doctusoft Ltd.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 *
 * @since 3.0.0
 */
public final class Attributes {

	private Attributes() {}

	public static <E, T> Function<E, T> functionOf( final Attribute<E, T> attribute ) {
		return new AttributeFunction<E, T>( attribute );
	}

	public static <T, Key, Value> Map<Key, Value> map(Iterable<T> elements, Attribute<? super T, Key> keyAttribute, Attribute<? super T, Value> valueAttribute) {
		// I didn't find a way to do this properly with standard Guava methods
		Map<Key, Value> result = Maps.newHashMap();
		for (T element : elements) {
			result.put(keyAttribute.getValue(element), valueAttribute.getValue(element));
		}
		return result;
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
