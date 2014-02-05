package com.doctusoft.common.core.bean.binding.observable;

import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.ValueBinding;

public interface ObservableValueBinding<T> extends ValueBinding<T> {

	ListenerRegistration addValueChangeListener(ValueChangeListener<T> listener);
}
