package com.doctusoft.bean.model;

import com.doctusoft.bean.ObservableProperty;

public class Model2 extends EmptyAbstractModel {
	
	@com.doctusoft.ObservableProperty
	private String property;
	
	@Override
	public Iterable<ObservableProperty<?, ?>> getObservableProperties() {
		return Model2_._observableProperties;
	}

}
