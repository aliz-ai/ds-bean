package com.doctusoft.bean.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestTypeAnnotations {
	
	@Test
	public void testProperty() {
		assertEquals("a", PropertyOnType_._a.getName());
		assertEquals("b", PropertyOnType_._b.getName());
	}

	@Test
	public void testObservableProperty() {
		assertEquals("a", ObservablePropertyOnType_._a.getName());
		assertEquals("b", ObservablePropertyOnType_._b.getName());
	}
}
