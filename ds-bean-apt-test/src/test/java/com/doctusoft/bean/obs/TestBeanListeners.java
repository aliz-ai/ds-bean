package com.doctusoft.bean.obs;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.doctusoft.bean.BeanPropertyChangeListener;
import com.doctusoft.bean.Property;

public class TestBeanListeners {

	@Test
	public void testBeanListener() {
		final Person person = new Person();
		Assert.assertNull(person.$$listeners);
		person.setName("hello world"); // no NPE is thrown
		Person_._descriptor.addBeanChangeListener(person, new BeanPropertyChangeListener<Person>() {
			@Override
			public <T> void propertyChanged(Person bean, Property<Person, T> property,
					T value) {
				assertEquals(person, bean);
				assertEquals("name", property.getName());
				assertEquals("hello world", value);
			}
		});
		Assert.assertNotNull(person.$$listeners);
		person.setName("hello world"); // no NPE is thrown
		// TODO assert actual listener invocation with some mocking framework
	}
  
}
