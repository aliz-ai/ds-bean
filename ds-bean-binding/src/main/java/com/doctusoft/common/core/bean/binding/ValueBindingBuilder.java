package com.doctusoft.common.core.bean.binding;

import com.doctusoft.common.core.bean.Attribute;

public class ValueBindingBuilder<T> implements ValueBinding<T> {
	
	protected ValueBinding<T> source;
	
	public static <R> ValueBindingBuilder<R> on(R object) {
		ValueBindingBuilder<R> builder = new ValueBindingBuilder<R>();
		builder.source = new ConstantValueBinding<R>(object);
		return builder;
	}
	
	public <Target> ValueBindingBuilder<Target> get(Attribute<? super T, Target> attribute) {
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

}
