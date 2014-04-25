package com.doctusoft.bean;

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

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 *
 * @since 3.0.0
 */
public final class Properties {

	private Properties() {}

	/**
	 * Create a guava {@link Function} based on the given {@link Property}
	 */
	public static <E, T> Function<E, T> functionOf( final Property<E, T> attribute ) {
		return new AttributeFunction<E, T>( attribute );
	}

	/**
	 * Creates a {@link HashMap} of the elements. The keys are determined by the keyAttribute parameter,
	 * the values are the elements themselves. 
	 */
	public static <T, Key> Map<Key, T> map(Iterable<T> elements, Property<? super T, Key> keyAttribute) {
		Map<Key, T> result = Maps.newHashMap();
		for (T element : elements) {
			result.put(keyAttribute.getValue(element), element);
		}
		return result;
	}

	/**
	 * Creates a {@link HashMap} of the element attributes, using the keyAttribute and valueAttribute to extract keys and values. 
	 */
	public static <T, Key, Value> Map<Key, Value> map(Iterable<T> elements, Property<? super T, Key> keyAttribute, Property<? super T, Value> valueAttribute) {
		// I didn't find a way to do this properly with standard Guava methods
		Map<Key, Value> result = Maps.newHashMap();
		for (T element : elements) {
			result.put(keyAttribute.getValue(element), valueAttribute.getValue(element));
		}
		return result;
	}

	private static class AttributeFunction<E, T> implements Function<E, T> {

		private final Property<E, T> attribute;

		public AttributeFunction( final Property<E, T> attribute ) {
			checkNotNull( attribute );
			this.attribute = attribute;
		}

		@Override
		public T apply( final E input ) {
			return attribute.getValue( input );
		}
	}

}
