package com.doctusoft.common.core.bean.binding;

public class ConvertingValueBinding<Source, Target> extends CompositeValueBinding<Source, Target> {
	
	private final Converter<Source, Target> converter;

	public ConvertingValueBinding(ValueBinding<Source> sourceBinding, Converter<Source, Target> converter) {
		super(sourceBinding);
		this.converter = converter;
	}
	
	@Override
	public Target getValue() {
		return converter.convertSource(sourceBinding.getValue());
	}
	
	@Override
	public void setValue(Target value) {
		((ValueBinding<Source>)sourceBinding).setValue(converter.convertTarget(value));
	};

}
