package com.doctusoft.bean.model;

import com.doctusoft.ObservableProperty;

public class Model1 extends ModelBase<String> {

	@Override
	public Iterable<com.doctusoft.bean.ObservableProperty<?, ?>> getObservableProperties() {
		return Model1_._observableProperties;
	}

	@ObservableProperty
	private String firstProperty;

}
