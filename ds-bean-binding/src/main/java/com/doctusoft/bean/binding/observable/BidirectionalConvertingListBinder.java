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


import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.doctusoft.bean.binding.Converter;

/**
 * A converting two-way list binder. Changes in any side of the two lists are propagated to the other list,
 * converting the value with the given converter.
 * Initially, the state of the source list is propagated
 * TODO: move this do ds-bean-binding 
 */
public class BidirectionalConvertingListBinder<Source, Target> implements Serializable {
	
	private Converter<Source, Target> converter;
	private ObservableValueBinding<? extends List<Target>> targetBinding;
	
	private boolean changing = false;
	@Getter @Setter
	private boolean suspended = false;

	// well this looks like javascript :)
	public BidirectionalConvertingListBinder(final ObservableValueBinding<? extends List<Source>> sourceBinding, final Converter<Source, Target> converter,
			final ObservableValueBinding<? extends List<Target>> targetBinding) {
		this.converter = converter;
		this.targetBinding = targetBinding;
		abstract class ListCopyListener<S, T> implements Serializable {
			private ObservableValueBinding<? extends List<T>> targetBinding;
			public ListCopyListener(final ObservableValueBinding<? extends List<S>> sourceBinding, final ObservableValueBinding<? extends List<T>> targetBinding) {
				this.targetBinding = targetBinding;
				new ListBindingListener<S>(sourceBinding) {
					@Override
					public void inserted(ObservableList<S> list, int index, S element) {
						if (changing || suspended)
							return;
						T targetValue = convert(element);
						changing = true;
						try {
							ListCopyListener.this.targetBinding.getValue().add(index, targetValue);
						} finally {
							changing = false;
						}
					}
					
					@Override
					public void removed(ObservableList<S> list, int index, S element) {
						if (changing || suspended)
							return;
						changing = true;
						try {
							ListCopyListener.this.targetBinding.getValue().remove(index);
						} finally {
							changing = false;
						}
					}
				};
			}
			protected abstract T convert(S source); 
		}
		new ListCopyListener<Source, Target>(sourceBinding, targetBinding) {
			@Override
			protected Target convert(Source source) {
				return converter.convertSource(source);
			}
		};
		// bind the other direction
		changing = true; // don't apply initial changes
		new ListCopyListener<Target, Source>(targetBinding, sourceBinding) {
			@Override
			protected Source convert(Target source) {
				return converter.convertTarget(source);
			}
		};
		changing = false;
	}
	
}
