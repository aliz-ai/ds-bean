package com.doctusoft.common.core.bean.binding;

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


import com.doctusoft.common.core.bean.Attribute;

public class AttributeCompositeValueBinding<Source, Target> extends CompositeValueBinding<Source, Target> {
	
	private final Attribute<? super Source, Target> attribute;

	public AttributeCompositeValueBinding(ValueBinding<? extends Source> sourceBinding, Attribute<? super Source, Target> attribute) {
		super(sourceBinding);
		this.attribute = attribute;
	}
	
	@Override
	public Target getValue() {
		return attribute.getValue((Source)sourceBinding.getValue());
	}
	
	public void setValue(Target value) {
		attribute.setValue(sourceBinding.getValue(), value);
	}

}