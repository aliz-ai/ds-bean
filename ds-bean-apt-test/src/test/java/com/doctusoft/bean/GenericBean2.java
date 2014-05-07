package com.doctusoft.bean;

import java.util.List;

import com.doctusoft.ObservableProperty;

public class GenericBean2<T extends Comparable<T> & Iterable<T>> {
	
	@ObservableProperty
	private T field;
	
	@ObservableProperty
	private List<T> listField;

}
