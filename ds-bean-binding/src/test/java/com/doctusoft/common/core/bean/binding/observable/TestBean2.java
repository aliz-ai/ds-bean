package com.doctusoft.common.core.bean.binding.observable;

import java.util.Date;

import com.doctusoft.ObservableProperty;

public class TestBean2 {
	
	@ObservableProperty
	private int number;
	
	@ObservableProperty
	private String text;
	
	@ObservableProperty
	private Date date;

}
