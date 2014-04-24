package com.doctusoft.common.core.bean;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestAttributeOnInterface {
	
	@Test
	public void testCodeCompilation() {
		assertNotNull(BeanInterface_.stringAttribute);
		assertNotNull(BeanInterface_.intAttribute);
		assertNotNull(BeanInterface_.booleanAttribute);
	}

}
