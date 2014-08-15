package com.doctusoft.bean.serialization;

import java.io.Serializable;

import com.doctusoft.ObservableProperty;

/**
 * By default, all listeners fields thus bindings are also serialized
 */
public class SerializableModel implements Serializable {

	@ObservableProperty
	private String stringField;

}
