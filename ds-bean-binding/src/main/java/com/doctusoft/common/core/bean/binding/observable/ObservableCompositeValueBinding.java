package com.doctusoft.common.core.bean.binding.observable;

import java.util.List;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.CompositeValueBinding;
import com.google.common.collect.Lists;

public abstract class ObservableCompositeValueBinding<Source, Target> extends CompositeValueBinding<Source, Target> implements ObservableValueBinding<Target>{
	
	protected final List<ValueChangeListener<Target>> valueChangeListeners = Lists.newArrayList();
	
	public ObservableCompositeValueBinding(ObservableValueBinding<Source> sourceBinding) {
		super(sourceBinding);
	}

	protected void fireListeners(Target value) {
		for (ValueChangeListener<Target> listener : valueChangeListeners) {
			listener.valueChanged(value);
		}
	}
	
	@Override
	public ListenerRegistration addValueChangeListener(final ValueChangeListener<Target> listener) {
		class BindingListenerRegistration implements ListenerRegistration {
			@Override
			public void removeHandler() {
				valueChangeListeners.remove(listener);
			}
		}
		valueChangeListeners.add(listener);
		return new BindingListenerRegistration();
	}
}
