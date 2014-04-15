package com.doctusoft.common.core.bean.binding.observable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.doctusoft.common.core.bean.binding.BindingRegistration;
import com.doctusoft.common.core.bean.binding.Bindings;

public class TestTwoWayBindings {
	
	@Test
	public void testTwoWayBindingSingleAttribute() {
		TestBean bean1 = new TestBean("abc");
		TestBean bean2 = new TestBean("123");
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
