package com.doctusoft.bean.beanlisteners;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.doctusoft.bean.BeanPropertyChangeListener;
import com.doctusoft.bean.Property;

public class TestHierarchicalTypes {
	
	String lastValue;
	int invokeCount = 0;
	
	@Test
	public void testHierarchicalTypes() {
		HC hc = new HC();
		
		// i know there are mock frameworks :) I'll use on later
		hc.getModelObjectDescriptor().addBeanChangeListener(hc, new BeanPropertyChangeListener<HC>() {
			@Override
			public <T> void propertyChanged(HC bean, Property<HC, T> property,
					T value) {
				lastValue = (String) value;
				invokeCount ++;
			}
		});
		
		hc.setField2("c");
		assertEquals("c", lastValue);
		assertEquals(1, invokeCount);
		
		hc.setField1("a");
		assertEquals("a", lastValue);
		assertEquals(2, invokeCount);
	}

}
