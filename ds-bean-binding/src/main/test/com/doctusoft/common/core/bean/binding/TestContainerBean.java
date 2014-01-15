package com.doctusoft.common.core.bean.binding;

import lombok.AllArgsConstructor;
import lombok.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TestContainerBean {
	
	@Attribute
	private TestBean testBean;

}
