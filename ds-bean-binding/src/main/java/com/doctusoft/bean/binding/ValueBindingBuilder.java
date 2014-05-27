package com.doctusoft.bean.binding;

/*
 * #%L
 * ds-bean-binding
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


import com.doctusoft.bean.Property;

public class ValueBindingBuilder<T> implements ValueBinding<T> {
	
	protected ValueBinding<T> source;
	
	public static <R> ValueBindingBuilder<R> on(R object) {
		ValueBindingBuilder<R> builder = new ValueBindingBuilder<R>();
		builder.source = new ConstantValueBinding<R>(object);
		return builder;
	}
	
	public static <R> ValueBindingBuilder<R> from(ValueBinding<R> source) {
		ValueBindingBuilder<R> builder = new ValueBindingBuilder<R>();
		builder.source = source;
		return builder;
	}

	public <Target> ValueBindingBuilder<Target> get(Property<? super T, Target> attribute) {
		ValueBindingBuilder<Target> builder = new ValueBindingBuilder<Target>();
		builder.source = new AttributeCompositeValueBinding<T, Target>(source, attribute);
		return builder;
	}
	
	public <Converted> ValueBindingBuilder<Converted> convert(Converter<T, Converted> converter) {
		ValueBindingBuilder<Converted> builder = new ValueBindingBuilder<Converted>();
		builder.source = new ConvertingValueBinding<T, Converted>(this, converter);
		return builder;
	}

	@Override
	public T getValue() {
		return source.getValue();
	}
	
	public void setValue(T value) {
		source.setValue(value);
	}

	@Override
	public String toString() {
		if (source == null)
			return "Empty ValueBindingBuilder";
		return source.toString();
	}
}
