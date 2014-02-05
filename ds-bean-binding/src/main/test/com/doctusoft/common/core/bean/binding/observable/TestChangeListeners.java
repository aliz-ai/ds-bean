package com.doctusoft.common.core.bean.binding.observable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.doctusoft.common.core.bean.binding.Bindings;

public class TestChangeListeners {
	
	@Test
	public void testSimpleAttributeChange() {
		TestBean bean1 = new TestBean("abc");
		TestBean bean2 = new TestBean("123");
		Bindings.bind(Bindings.obs(bean1).get(TestBean._stringValue), Bindings.on(bean2).get(TestBean._stringValue));
		assertEquals("abc", bean2.getStringValue());
		bean1.setStringValue("qwe");
		assertEquals("qwe", bean2.getStringValue());
	}

	@Test
	public void testChainedAttributeChange() {
		TestContainerBean container1 = new TestContainerBean(new TestBean("abc"));
		TestContainerBean container2 = new TestContainerBean(new TestBean("123"));
		Bindings.bind(Bindings.obs(container1).get(TestContainerBean._testBean).get(TestBean._stringValue),
				Bindings.on(container2).get(TestContainerBean._testBean).get(TestBean._stringValue));
		assertEquals("abc", container2.getTestBean().getStringValue());
		container1.getTestBean().setStringValue("qwe");
		assertEquals("qwe", container2.getTestBean().getStringValue());
	}

	@Test
	public void testBindingChainChange() {
		TestContainerBean container1 = new TestContainerBean(new TestBean("abc"));
		TestContainerBean container2 = new TestContainerBean(new TestBean("123"));
		Bindings.bind(Bindings.obs(container1).get(TestContainerBean._testBean).get(TestBean._stringValue),
				Bindings.on(container2).get(TestContainerBean._testBean).get(TestBean._stringValue));
		assertEquals("abc", container2.getTestBean().getStringValue());
		container1.setTestBean(new TestBean("qwe"));
		assertEquals("qwe", container2.getTestBean().getStringValue());
	}
}
