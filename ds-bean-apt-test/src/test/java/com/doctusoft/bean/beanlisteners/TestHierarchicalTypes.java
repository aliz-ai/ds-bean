package com.doctusoft.bean.beanlisteners;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.doctusoft.bean.BeanPropertyChangeListener;
import com.doctusoft.bean.Property;

public class TestHierarchicalTypes {
	
	String lastValue;
	
	@Test
	public void testHierarchicalTypes() {
		HC hc = new HC();
		
		hc.getModelObjectDescriptor().addBeanChangeListener(hc, new BeanPropertyChangeListener<HC>() {
			@Override
			public <T> void propertyChanged(HC bean, Property<HC, T> property,
					T value) {
				lastValue = (String) value;
			}
		});
		
		hc.setField2("c");
		assertEquals("c", lastValue);
		
		hc.setField1("a");
		assertEquals("a", lastValue);
	}

}
