package com.doctusoft.common.core.bean.binding.observable;

import com.doctusoft.common.core.bean.ValueChangeListener;
import com.doctusoft.common.core.bean.binding.Converter;
import com.doctusoft.common.core.bean.binding.ValueBinding;

public class ObservableConvertingValueBinding<Source, Target> extends ObservableCompositeValueBinding<Source, Target> {
	
	private Converter<Source, Target> converter;

	public ObservableConvertingValueBinding(ObservableValueBinding<Source> sourceBinding, Converter<Source, Target> converter) {
		super(sourceBinding);
		this.converter = converter;
		listenToSourceChange();
	}

	private void listenToSourceChange() {
		((ObservableValueBinding<Source>) sourceBinding).addValueChangeListener(new ValueChangeListener<Source>() {
			@Override
			public void valueChanged(Source newValue) {
				Target targetValue = getValue();
				fireListeners(targetValue);
			}
		});		
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
