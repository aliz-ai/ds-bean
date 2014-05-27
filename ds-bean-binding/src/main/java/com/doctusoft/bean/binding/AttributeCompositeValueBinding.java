package com.doctusoft.bean.binding;

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


import com.doctusoft.bean.Property;

public class AttributeCompositeValueBinding<Source, Target> extends CompositeValueBinding<Source, Target> {
	
	private final Property<? super Source, Target> attribute;

	public AttributeCompositeValueBinding(ValueBinding<? extends Source> sourceBinding, Property<? super Source, Target> attribute) {
		super(sourceBinding);
		this.attribute = attribute;
	}
	
	@Override
	public Target getValue() {
		Source sourceValue = (Source)sourceBinding.getValue();
		// if the source value is null, we silently return null. It is needed in transient states for most UI frameworks
		if (sourceValue == null)
			return null;
		return attribute.getValue(sourceValue);
	}
	
	public void setValue(Target value) {
		Source sourceValue = sourceBinding.getValue();
		if (sourceValue != null) {
			attribute.setValue(sourceValue, value);
		} else {
			// if the source value is null, we silently return null. It is needed in transient states for most UI frameworks
		}
	}

	@Override
	public String toString() {
		return sourceBinding.toString() + "." + attribute.getName();
	}
}
