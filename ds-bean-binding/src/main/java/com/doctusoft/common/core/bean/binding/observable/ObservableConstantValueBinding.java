package com.doctusoft.common.core.bean.binding.observable;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;

/**
 * Well, quite an oximoron, but I hope you get the idea :) 
 */
public class ObservableConstantValueBinding<Root> implements ObservableValueBinding<Root> {

	private final Root root;

	public ObservableConstantValueBinding(Root root) {
		this.root = root;
	}
	
	@Override
	public Root getValue() {
		return root;
	}
	
	public void setValue(Root value) {
		throw new UnsupportedOperationException("Root value binding cannot be assigned");
	}
	
	@Override
	public ListenerRegistration addValueChangeListener(ValueChangeListener<Root> listener) {
		// do nothing, it's never invoked
		return new ListenerRegistration() {
			@Override
			public void removeHandler() {
				// do nothing
			}
		};
	}
}
