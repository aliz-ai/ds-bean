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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.doctusoft.bean.ValueChangeListener;
import com.doctusoft.bean.binding.BindingRegistration;
import com.doctusoft.bean.binding.Bindings;
import com.doctusoft.bean.binding.Converter;
import com.google.common.base.Strings;

public class TestTwoWayBindings {
	
	@Test
	public void testTwoWayBindingSingleAttribute() {
		TestBean bean1 = new TestBean("abc");
		TestBean bean2 = new TestBean("123");
		// bean1 as source, bean2 as target
		BindingRegistration binding = Bindings.bind(Bindings.obs(bean1).get(TestBean_._stringValue), Bindings.obs(bean2).get(TestBean_._stringValue));
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

	@Test
	public void testSelfChangingTwoWayBindings() {
		final TestBean bean1 = new TestBean("");
		final TestBean bean2 = new TestBean("");
		// assume that changes on bean2 trigger some application logic that change values
		Bindings.obs(bean2).get(TestBean_._stringValue).addValueChangeListener(new ValueChangeListener<String>() {
			@Override
			public void valueChanged(String newValue) {
				if (!"other".equals(bean2.getStringValue())) {
					bean2.setStringValue("other");
				}
			}
		});
		// assume that the application value is bound to a user interface component
		Bindings.bind(Bindings.obs(bean1).get(TestBean_._stringValue), Bindings.obs(bean2).get(TestBean_._stringValue));
		// now the user interface components gets a new value from the user
		bean1.setStringValue("test");
		// assert that both models are updated by the application
		assertEquals("other", bean1.getStringValue());
		assertEquals("other", bean2.getStringValue());
	}
	
	@Test
	public void testChangeObjectReferenceToNull() {
		final TestBean bean1 = new TestBean(null);
		final TestBean bean2 = new TestBean(null);
		// bind the two string values
		Bindings.bind(Bindings.obs(bean1).get(TestBean_._stringValue), Bindings.obs(bean2).get(TestBean_._stringValue));
		// set bean1 value to a non-null value
		bean1.setStringValue("xx");
		assertEquals("xx", bean2.getStringValue());
		// set bean1 to null
		bean1.setStringValue(null);
		assertEquals(null, bean2.getStringValue());
	}
	
	@Test
	public void testConvertingTwoWayBinding() {
		TestBean2 bean1 = new TestBean2();
		bean1.setNumber(1);
		bean1.setText("1");
		Bindings.bind(Bindings.obs(bean1).get(TestBean2_._text), Bindings.obs(bean1).get(TestBean2_._number).convert(new Converter<Integer, String>() {
			@Override
			public String convertSource(Integer source) {
				return Integer.toString(source);
			}
			@Override
			public Integer convertTarget(String target) {
				return Integer.parseInt(target);
			}
		}));
		bean1.setNumber(2);
	}

	@Test
	public void testDateConvertingTwoWayBinding() {
		TestBean2 bean1 = new TestBean2();
		final DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		Bindings.bind(Bindings.obs(bean1).get(TestBean2_._date), Bindings.obs(bean1).get(TestBean2_._text).convert(new Converter<String, Date>() {
			@Override
			public Date convertSource(String source) {
				if (Strings.isNullOrEmpty(source))
					return null;
				try {
					return format.parse(source);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			@Override
			public String convertTarget(Date target) {
				if (target == null)
					return "";
				return format.format(target);
			}
		}));
		// basically we just assert that things don't end up in an infinite loop
		bean1.setDate(new Date(114, 3, 4));
		bean1.setText("2014.04.04");
	}
}
