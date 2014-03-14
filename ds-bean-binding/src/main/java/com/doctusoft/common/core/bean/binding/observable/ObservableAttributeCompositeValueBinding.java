package com.doctusoft.common.core.bean.binding.observable;

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


import com.doctusoft.common.core.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.ObservableAttribute;
import com.doctusoft.common.core.bean.ValueChangeListener;

public class ObservableAttributeCompositeValueBinding<Source, Target> extends ObservableCompositeValueBinding<Source, Target>
		implements ObservableValueBinding<Target> {

	private final ObservableAttribute<? super Source, Target> attribute;
	private Source sourceValue = null;
	private ListenerRegistration sourceValueHandler = null;

	public ObservableAttributeCompositeValueBinding(ObservableValueBinding<? extends Source> sourceBinding, ObservableAttribute<? super Source, Target> attribute) {
		super(sourceBinding);
		this.attribute = attribute;
		// if the attribute value on the source changes, we fire a change event
		bindToSourceValue();
		// but if the source itself changes, we also fire a change event
		listenToSourceChange();
	}
	
	protected void listenToSourceChange() {
		((ObservableValueBinding<Source>) sourceBinding).addValueChangeListener(new ValueChangeListener<Source>() {
			@Override
			public void valueChanged(Source value) {
				// remove the listener on the previous Source value
				if (sourceValueHandler != null) {
					sourceValueHandler.removeHandler();
					sourceValueHandler = null;
				}
				// bind to the new Source.attribute change
				bindToSourceValue();
				// if the new Source is not null, fire a Target value change
				if (sourceValue != null) {
					Target targetValue = attribute.getValue(sourceValue);
					fireListeners(targetValue);
				}
			}
		});
	}

	protected void bindToSourceValue() {
		sourceValue = sourceBinding.getValue();
		if (sourceValue != null) {
			sourceValueHandler = attribute.addChangeListener(sourceValue, new ValueChangeListener<Target>() {
				@Override
				public void valueChanged(Target value) {
					fireListeners(value);
				}
			});
		}
	}
	
	@Override
	public Target getValue() {
		Source sourceValue = sourceBinding.getValue();
		if (sourceValue == null)
			return null;
		return attribute.getValue(sourceValue);
	}
	
	public void setValue(Target value) {
		Source sourceValue = sourceBinding.getValue();
		if (sourceValue != null) {
			attribute.setValue(sourceValue, value);
		} else {
			// in case of an empty source value, we silently fail. This behaviour is important to know about value bindings, but is essential to get things work
		}
	}
	
}
