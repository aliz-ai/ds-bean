package com.doctusoft.bean.model;

/*
 * #%L
 * ds-bean-apt-test
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
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.doctusoft.bean.ObservableProperty;
import com.google.common.collect.Lists;

public class TestModels {
	
	@Test
	public void testModelBase() {
		List<ObservableProperty<?, ?>> list = Lists.newArrayList(ModelBase_.observableProperties);
		assertEquals(1, list.size());
		assertEquals(ModelBase_._baseProperty, list.get(0));
	}
	
	@Test
	public void testModel1() {
		List<ObservableProperty<?, ?>> list  = Lists.newArrayList(new Model1().getModelObjectDescriptor().getObservableProperties());
		assertEquals(2, list.size());
		assertTrue(list.contains(Model1_._firstProperty));
		assertTrue(list.contains(ModelBase_._baseProperty));
	}

}
