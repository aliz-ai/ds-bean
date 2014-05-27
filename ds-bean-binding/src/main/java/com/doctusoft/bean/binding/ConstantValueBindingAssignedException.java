package com.doctusoft.bean.binding;

public class ConstantValueBindingAssignedException extends UnsupportedOperationException {
	public ConstantValueBindingAssignedException() {
		super("Root value binding cannot be assigned");
	}
}
