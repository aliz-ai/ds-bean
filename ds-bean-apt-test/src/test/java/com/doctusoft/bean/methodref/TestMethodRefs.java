package com.doctusoft.bean.methodref;

/*
 * #%L
 * ds-bean-apt-test
 * %%
 * Copyright (C) 2014 Doctusoft Ltd.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
