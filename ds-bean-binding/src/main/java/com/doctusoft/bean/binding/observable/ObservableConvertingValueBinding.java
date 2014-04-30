package com.doctusoft.bean.binding.observable;

/*
 * #%L
 * ds-bean-binding
 * %%
 * Copyright (C) 2014 Doctusoft Ltd.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.doctusoft.bean.ValueChangeListener;
import com.doctusoft.bean.binding.Converter;
import com.doctusoft.bean.binding.ValueBinding;

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
