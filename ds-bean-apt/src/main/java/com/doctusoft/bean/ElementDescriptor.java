package com.doctusoft.bean;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import lombok.Data;

@Data
public class ElementDescriptor {

	private TypeMirror type;
	
	private Element element;

}
