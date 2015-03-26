package com.doctusoft.common.core.bean.binding.observable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.doctusoft.ObservableProperty;
import com.doctusoft.bean.binding.Bindings;

public class TestBindings {
	
	@ObservableProperty
	private String sourceValue;

	@ObservableProperty
	private String targetValue;
	
	@Test
	public void testReverseObservableBinding() {
		setSourceValue("abc");
		Bindings.bind(Bindings.on(this).get(TestBindings_._sourceValue), Bindings.obs(this).get(TestBindings_._targetValue));
		assertEquals("abc", targetValue);
		setTargetValue("123");
		assertEquals("123", sourceValue);
	}

	@Test
	public void testNonObservableBinding() {
		setSourceValue("abc");
		Bindings.bind(Bindings.on(this).get(TestBindings_._sourceValue), Bindings.on(this).get(TestBindings_._targetValue));
		assertEquals("abc", targetValue);
	}

}
