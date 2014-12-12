package com.doctusoft.bean.methodref;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.doctusoft.MethodRef;
import com.doctusoft.bean.ReferencedInvocationException;

public class TestDeclaredExceptions {
	
	@MethodRef
	public void dangerousMethod() throws IOException {
		throw new IOException();
	}
	
	@Test
	public void testDeclaredExceptions() {
		try {
			TestDeclaredExceptions_.__dangerousMethod.apply(this);
			Assert.fail("An exception should have occured");
		} catch (ReferencedInvocationException e) {
			Assert.assertTrue(e.getCause() instanceof IOException);
		}
	}

}
