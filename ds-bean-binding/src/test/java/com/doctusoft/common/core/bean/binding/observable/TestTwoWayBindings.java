package com.doctusoft.common.core.bean.binding.observable;

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


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.doctusoft.common.core.bean.binding.BindingRegistration;
import com.doctusoft.common.core.bean.binding.Bindings;

public class TestTwoWayBindings {
	
	@Test
	public void testTwoWayBindingSingleAttribute() {
		TestBean bean1 = TestBeanRaw.create("abc");
		TestBean bean2 = TestBeanRaw.create("123");
		// bean1 as source, bean2 as target
		BindingRegistration binding = Bindings.bind(Bindings.obs(bean1).get(TestBean._stringValue), Bindings.obs(bean2).get(TestBean._stringValue));
		// the target is expected to be instantly overwritten
		assertEquals("abc", bean2.getStringValue());
		// If bean1 is changed
		bean1.setStringValue("changed");
		// then bean2 should be changed too
		assertEquals("changed", bean2.getStringValue());
		// and if bean2 is changed
		bean2.setStringValue("changed again");
		// then bean1 is changed too
		assertEquals("changed again", bean1.getStringValue());
		// And after the binding is removed,
		binding.unbind();
		// changing both have no effect on the other
		bean1.setStringValue("bean1");
		bean2.setStringValue("bean2");
		assertEquals("bean1", bean1.getStringValue());
		assertEquals("bean2", bean2.getStringValue());
	}

}
