package com.doctusoft.bean;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestAttributeOnGenericTypes {

	@Test
	public void testGenericMapField() {
		Property<GenericBean<?>, Map<String, ?>> field = GenericBean_._genericMapField;
		assertEquals("genericMapField", field.getName());
		assertEquals(Map.class, field.getType());
		assertEquals(GenericBean.class, field.getParent());
	}
	
	@Test
	public void testGenericMultimapField() {
		Property<GenericBean<?>, Map<String, List<?>>> field = GenericBean_._genericMultimapField;
		assertEquals("genericMultimapField", field.getName());
		assertEquals(Map.class, field.getType());
		assertEquals(GenericBean.class, field.getParent());
	}
}
