package com.doctusoft.bean;

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
