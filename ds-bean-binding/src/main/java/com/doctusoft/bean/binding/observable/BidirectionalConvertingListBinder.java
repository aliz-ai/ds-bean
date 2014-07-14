package com.doctusoft.bean.binding.observable;

import java.io.Serializable;
import java.util.List;

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

	// well this looks like javascript :)
	public BidirectionalConvertingListBinder(final ObservableValueBinding<? extends List<Source>> sourceBinding, final Converter<Source, Target> converter,
			final ObservableValueBinding<? extends List<Target>> targetBinding) {
		this.converter = converter;
		this.targetBinding = targetBinding;
		abstract class ListCopyListener<S, T> {
			private ObservableValueBinding<? extends List<T>> targetBinding;
			public ListCopyListener(final ObservableValueBinding<? extends List<S>> sourceBinding, final ObservableValueBinding<? extends List<T>> targetBinding) {
				this.targetBinding = targetBinding;
				new ListBindingListener<S>(sourceBinding) {
					@Override
					public void inserted(ObservableList<S> list, int index, S element) {
						if (changing)
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
						if (changing)
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
