package com.doctusoft.common.core.bean.binding;

/*
 * #%L
 * ds-bean-binding
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


import org.junit.Assert;
import org.junit.Test;

import com.doctusoft.bean.binding.Bindings;
import com.doctusoft.bean.binding.Converter;
import com.doctusoft.bean.binding.ValueBindingBuilder;

public class TestToString {
	
	@Test
	public void testAttributeComposite() {
		TestBean testBean = new TestBean("hello world");
		Assert.assertEquals("TestBean(hello world).stringValue", Bindings.on(testBean).get(TestBean_._stringValue).toString());
	}

	@Test
	public void testConverted() {
		TestBean testBean = new TestBean("hello world");
		ValueBindingBuilder<String> binding = Bindings.on(testBean).get(TestBean_._stringValue)
						.convert(new IdentityConverter<String>());
		Assert.assertEquals("TestBean(hello world).stringValue->IdentityConverter", binding.toString());
	}
	
	public static class IdentityConverter<T> implements Converter<T, T> {
		public T convertSource(T source) {
			return source;
		}
		public T convertTarget(T target) {
			return target;
		}
		@Override
		public String toString() {
			return IdentityConverter.class.getSimpleName();
		}
	}
}
