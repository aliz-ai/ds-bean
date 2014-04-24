package com.doctusoft.common.core.bean;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class TestAttributeOnGenericTypes {

	@Test
	public void testGenericMapField() {
		// TODO put GenericBean<?>
		Attribute<GenericBean, Map<?, ?>> field = GenericBean_.genericMapField;
		assertEquals("genericMapField", field.getName());
		assertEquals(Map.class, field.getType());
		assertEquals(GenericBean.class, field.getParent());
	}
	
}
