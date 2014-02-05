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


import com.doctusoft.common.core.bean.ObservableAttribute;
import com.doctusoft.common.core.bean.binding.Converter;

public class ObservableChainedValueBindingBuilder<T> extends ObservableValueBindingBuilder<T> {
	
	public static <R> ObservableChainedValueBindingBuilder<R> on(R object) {
		ObservableChainedValueBindingBuilder<R> builder = new ObservableChainedValueBindingBuilder<R>();
		builder.source = new ObservableConstantValueBinding<R>(object);
		return builder;
	}

	public <Target> ObservableChainedValueBindingBuilder<Target> get(final ObservableAttribute<T, Target> attribute) {
		ObservableChainedValueBindingBuilder<Target> builder = new ObservableChainedValueBindingBuilder<Target>();
		builder.source = new ObservableAttributeCompositeValueBinding<T, Target>(source, attribute);
		return builder;
	}
	
	public <Converted> ObservableChainedValueBindingBuilder<Converted> convert(Converter<T, Converted> converter) {
		ObservableChainedValueBindingBuilder<Converted> builder = new ObservableChainedValueBindingBuilder<Converted>();
		builder.source = new ObservableConvertingValueBinding<T, Converted>(this, converter);
		return builder;
	}
	
//	public <Target> ObservedValueBindingBuilder<Target> get(Attribute<T, Target> attribute) {
//		ObservedValueBindingBuilder<Target> builder = new ObservedValueBindingBuilder<Target>();
//		builder.source = new ObservedAttributeCompositeValueBinding<T, Target>(source, attribute);
//		return builder;
//	}
	
}
