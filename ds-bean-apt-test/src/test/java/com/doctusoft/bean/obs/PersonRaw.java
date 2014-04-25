package com.doctusoft.bean.obs;

import com.doctusoft.ObservableModel;
import com.doctusoft.Property;

@ObservableModel
public abstract class PersonRaw {
	
	@Property
	private String name;
	
	@Property
	private int birthYear;
	
	@Property
	private boolean resident;

}
