package com.doctusoft.bean.methodref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.doctusoft.MethodRef;

public class TestMethodRefs {
	
	boolean invoked = false;
	
	@MethodRef
	public void testVoidMethod0() {
		invoked = true;
	}
	
	@MethodRef
	public int testIntMethod0() {
		return 42;
	}
	
	@MethodRef
	public int testIntMethod1(int param) {
		return 2 * param;
	}

	@Test
	public void testMethodRefs() {
		invoked = false;
		TestMethodRefs_.__testVoidMethod0.on(this).apply();
		assertTrue(invoked);
		assertEquals(new Integer(42), TestMethodRefs_.__testIntMethod0.on(this).apply());
	}
}
