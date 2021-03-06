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


public class ConvertingValueBinding<Source, Target> extends CompositeValueBinding<Source, Target> {
	
	private final Converter<Source, Target> converter;

	public ConvertingValueBinding(ValueBinding<Source> sourceBinding, Converter<Source, Target> converter) {
		super(sourceBinding);
		this.converter = converter;
	}
	
	@Override
	public Target getValue() {
		return converter.convertSource(sourceBinding.getValue());
	}
	
	@Override
	public void setValue(Target value) {
		((ValueBinding<Source>)sourceBinding).setValue(converter.convertTarget(value));
	}
	
	@Override
	public String toString() {
		return sourceBinding.toString() + "->" + converter.toString();
	}

}
