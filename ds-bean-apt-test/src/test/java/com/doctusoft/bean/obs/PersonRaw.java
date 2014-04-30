package com.doctusoft.bean.obs;

import lombok.Getter;
import lombok.Setter;

import com.doctusoft.ObservableModel;
import com.doctusoft.Property;

@ObservableModel
@Getter @Setter
public abstract class PersonRaw {
	
	@Property
	private String name;
	
	@Property
	private int birthYear;
	
	@Property
	private boolean resident;

}
