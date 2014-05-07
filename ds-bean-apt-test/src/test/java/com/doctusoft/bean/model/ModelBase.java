package com.doctusoft.bean.model;

import com.doctusoft.ObservableProperty;
import com.doctusoft.bean.ModelObject;

public abstract class ModelBase<T> implements ModelObject {
	
	@ObservableProperty
	private boolean baseProperty;

}
