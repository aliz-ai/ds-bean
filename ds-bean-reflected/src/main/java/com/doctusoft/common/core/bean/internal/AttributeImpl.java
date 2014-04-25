package com.doctusoft.common.core.bean.internal;

/*
 * #%L
 * ds-bean-reflected
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


import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.doctusoft.bean.Property;

import static com.google.common.base.Preconditions.*;

/**
 * AttributeImpl<SubjectClass, PropertyType>
 * 
 * @deprecated this implementation relies on reflection and apache common-beanutils.
 *   This solution has been replaced with inline code generation using lombok-ds.
 *   It runs faster and works with GWT.
 * 
 * @since 3.0.0
 */
@lombok.Getter
@lombok.EqualsAndHashCode( of = { "parent", "name", "type" }, doNotUseGetters = true )
@Deprecated
public class AttributeImpl<E, T> implements Property<E, T> {

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
