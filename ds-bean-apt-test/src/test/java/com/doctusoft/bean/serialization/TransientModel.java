package com.doctusoft.bean.serialization;

import java.io.Serializable;

import com.doctusoft.ObservableProperty;
import com.doctusoft.bean.internal.BeanPropertyListeners;
import com.doctusoft.bean.internal.PropertyListeners;

/**
 * If specific listeners field are already manually defined, lombok-ds should not override them.
 * This allows for bean customization. This one for example prevents listeners from being serialized with the bean. 
 */
public class TransientModel implements Serializable {
	
	public transient BeanPropertyListeners $$listeners;
	
	public transient PropertyListeners $$stringField$listeners;
	
	@ObservableProperty
	private String stringField;

}
