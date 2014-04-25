package com.doctusoft.bean;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestAttributeOnInterface {
	
	@Test
	public void testCodeCompilation() {
		assertNotNull(BeanInterface_._stringAttribute);
		assertNotNull(BeanInterface_._intAttribute);
		assertNotNull(BeanInterface_._booleanAttribute);
	}

}
