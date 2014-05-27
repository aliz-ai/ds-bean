package com.doctusoft.common.core.bean.binding.observable;

import org.junit.Assert;
import org.junit.Test;

import com.doctusoft.bean.binding.Bindings;
import com.doctusoft.bean.binding.Converter;
import com.doctusoft.bean.binding.observable.ObservableChainedValueBindingBuilder;

public class TestObservableToString {
	
	@Test
	public void testAttributeComposite() {
		TestBean testBean = new TestBean("hello world");
		Assert.assertEquals("TestBean(hello world).stringValue", Bindings.obs(testBean).get(TestBean_._stringValue).toString());
	}

	@Test
	public void testConverted() {
		TestBean testBean = new TestBean("hello world");
		ObservableChainedValueBindingBuilder<String> binding = Bindings.obs(testBean).get(TestBean_._stringValue)
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
