package com.doctusoft.bean;

import static org.junit.Assert.assertNotNull;
import lombok.Setter;

import org.junit.Test;

import com.doctusoft.Property;

public class TestAttributeOnGetters {
	
	@Setter
	private String stringField;
	
	@Property
	public String getStringField() {
		return stringField;
	}
	
	@Property(readonly=true)
	public boolean isBooleanField() {
		return false;
	}
	
	@Test
	public void testCodeCompilation() {
		assertNotNull(TestAttributeOnGetters_._stringField);
		assertNotNull(TestAttributeOnGetters_._booleanField);
	}
	
}
