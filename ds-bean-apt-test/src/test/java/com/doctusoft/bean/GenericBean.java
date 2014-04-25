package com.doctusoft.bean;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenericBean<T> {
	
	@com.doctusoft.Property
	private Map<String, T> genericMapField;

	@com.doctusoft.Property
	private Map<String, List<T>> genericMultimapField;
}
