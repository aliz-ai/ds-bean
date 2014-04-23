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

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.doctusoft.common.core.bean.binding.observable.ObservableList.ListElementInsertedListener;
import com.doctusoft.common.core.bean.binding.observable.ObservableList.ListElementRemovedListener;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class TestObservableList {
	
	private ObservableList<String> sourceList;
	private List<String> targetList;

	@Before
	public void setup() {
		sourceList = new ObservableList<String>();
		sourceList.addDeleteListener(new ListElementRemovedListener<String>() {
			@Override
			public void removed(ObservableList<String> list, int index,
					String element) {
				targetList.remove(index);
			}
		});
		sourceList.addInsertListener(new ListElementInsertedListener<String>() {
			@Override
			public void inserted(ObservableList<String> list, int index,
					String element) {
				targetList.add(index, element);
			}
		});
		targetList = Lists.newArrayList();
	}
	
	@After
	public void teardown() {
		sourceList = null;
		targetList = null;
	}
	
	protected void assertTargetList(String concatenated) {
		assertEquals(concatenated,  Joiner.on("").join(targetList));
	}
	
	@Test
	public void testAdd() {
		sourceList.add("a");
		sourceList.add("b");
		sourceList.add(1, "c");
		assertTargetList("acb");
	}
	
	@Test
	public void testAddAll() {
		sourceList.addAll(ImmutableList.of("a", "b"));
		sourceList.addAll(1, ImmutableList.of("c", "d"));
		assertTargetList("acdb");
	}

	@Test
	public void testRemoveIndex() {
		sourceList.addAll(ImmutableList.of("a", "b", "c"));
		sourceList.remove(1);
		assertTargetList("ac");
	}

	@Test
	public void testRemoveObject() {
		sourceList.addAll(ImmutableList.of("a", "b", "c"));
		sourceList.remove("b");
		assertTargetList("ac");
	}

	@Test
	public void testRemoveAll() {
		sourceList.addAll(ImmutableList.of("a", "b", "c"));
		sourceList.removeAll(ImmutableList.of("b", "c"));
		assertTargetList("a");
	}
	
	@Test
	public void testRetainAll() {
		sourceList.addAll(ImmutableList.of("a", "b", "c"));
		sourceList.retainAll(ImmutableList.of("b", "c"));
		assertTargetList("bc");
	}
	
	@Test
	public void testSet() {
		sourceList.addAll(ImmutableList.of("a", "b", "c"));
		sourceList.set(1, "d");
		assertTargetList("adc");
	}
	
	@Test
	public void testClear() {
		sourceList.addAll(ImmutableList.of("a", "b", "c"));
		assertTargetList("abc");
		sourceList.clear();
		assertEquals(0, targetList.size());
	}
	
	@Test
	public void testIterator() {
		sourceList.addAll(ImmutableList.of("a", "b", "c"));
		Iterator<String> itr = sourceList.iterator();
		assertEquals(3, targetList.size());
		while (itr.hasNext()) {
			itr.next();
			itr.remove();
		}
		assertEquals(0, targetList.size());
	}
}
