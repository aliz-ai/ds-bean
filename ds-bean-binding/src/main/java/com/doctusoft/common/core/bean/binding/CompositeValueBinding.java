package com.doctusoft.common.core.bean.binding;

public abstract class CompositeValueBinding<Source, Target> implements ValueBinding<Target> {
	
	protected final ValueBinding<? extends Source> sourceBinding;

	public CompositeValueBinding(ValueBinding<? extends Source> sourceBinding) {
		this.sourceBinding = sourceBinding;
	}

}
