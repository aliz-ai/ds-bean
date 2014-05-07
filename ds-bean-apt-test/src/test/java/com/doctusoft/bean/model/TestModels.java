package com.doctusoft.bean.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.doctusoft.bean.ObservableProperty;
import com.google.common.collect.Lists;

public class TestModels {
	
	@Test
	public void testModelBase() {
		List<ObservableProperty<?, ?>> list = Lists.newArrayList(ModelBase_._observableProperties);
		assertEquals(1, list.size());
		assertEquals(ModelBase_._baseProperty, list.get(0));
	}
	
	@Test
	public void testModel1() {
		List<ObservableProperty<?, ?>> list  = Lists.newArrayList(new Model1().getObservableProperties());
		assertEquals(2, list.size());
		assertTrue(list.contains(Model1_._firstProperty));
		assertTrue(list.contains(ModelBase_._baseProperty));
	}

}
