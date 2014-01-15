package com.doctusoft.common.core.bean.binding;

import com.doctusoft.common.core.bean.Attribute;

public class AttributeCompositeValueBinding<Source, Target> extends CompositeValueBinding<Source, Target> {
	
	private final Attribute<? super Source, Target> attribute;

	public AttributeCompositeValueBinding(ValueBinding<? extends Source> sourceBinding, Attribute<? super Source, Target> attribute) {
		super(sourceBinding);
		this.attribute = attribute;
	}
	
	@Override
	public Target getValue() {
		return attribute.getValue((Source)sourceBinding.getValue());
	}
	
	public void setValue(Target value) {
		attribute.setValue(sourceBinding.getValue(), value);
	}

}