package com.doctusoft.common.core.bean;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestAttributeOnGenericTypes {

	@Test
	public void testGenericMapField() {
		Attribute<GenericBean<?>, Map<String, ?>> field = GenericBean_.genericMapField;
		assertEquals("genericMapField", field.getName());
		assertEquals(Map.class, field.getType());
		assertEquals(GenericBean.class, field.getParent());
	}
	
	@Test
	public void testGenericMultimapField() {
		Attribute<GenericBean<?>, Map<String, List<?>>> field = GenericBean_.genericMultimapField;
		assertEquals("genericMultimapField", field.getName());
		assertEquals(Map.class, field.getType());
		assertEquals(GenericBean.class, field.getParent());
	}
}
