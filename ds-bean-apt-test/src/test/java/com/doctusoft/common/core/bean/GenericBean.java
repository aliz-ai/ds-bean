package com.doctusoft.common.core.bean;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenericBean<T> {
	
	@com.doctusoft.Attribute
	private Map<String, T> genericMapField;

}
