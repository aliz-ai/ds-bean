package com.doctusoft.bean.binding.observable;

import java.util.List;

public abstract class ListChangeListener extends ListBindingListener<Object> {
	
	public ListChangeListener(ObservableValueBinding<? extends List<?>> listBinding) {
		super((ObservableValueBinding) listBinding);
	}
	
	protected abstract void changed();
	
	@Override
	public void inserted(ObservableList<Object> list, int index, Object element) {
		changed();
	}
	
	@Override
	public void removed(ObservableList<Object> list, int index, Object element) {
		changed();
	}

}
