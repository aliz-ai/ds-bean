package com.doctusoft.common.core.bean.binding;

public class ConstantValueBinding<Root> implements ValueBinding<Root> {

	private final Root root;

	public ConstantValueBinding(Root root) {
		this.root = root;
	}
	
	@Override
	public Root getValue() {
		return root;
	}
	
	public void setValue(Root value) {
		throw new UnsupportedOperationException("Root value binding cannot be assigned");
	}
	
}
