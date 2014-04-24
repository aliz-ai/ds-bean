package com.doctusoft.common.core.bean;

import static org.junit.Assert.assertNotNull;
import lombok.Setter;

import org.junit.Test;

import com.doctusoft.Attribute;

public class TestAttributeOnGetters {
	
	@Setter
	private String stringField;
	
	@Attribute
	public String getStringField() {
		return stringField;
	}
	
	@Attribute(readonly=true)
	public boolean isBooleanField() {
		return false;
	}
	
	@Test
	public void testCodeCompilation() {
		assertNotNull(TestAttributeOnGetters_.stringField);
		assertNotNull(TestAttributeOnGetters_.booleanField);
	}
	
}
