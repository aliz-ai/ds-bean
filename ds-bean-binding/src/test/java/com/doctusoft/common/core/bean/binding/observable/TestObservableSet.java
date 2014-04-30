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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.doctusoft.bean.binding.observable.ObservableSet;
import com.doctusoft.bean.binding.observable.ObservableSet.SetElementInsertedListener;
import com.doctusoft.bean.binding.observable.ObservableSet.SetElementRemovedListener;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TestObservableSet {
	
	private ObservableSet<String> sourceSet;
	private Set<String> targetSet;

	@Before
	public void setup() {
		sourceSet = new ObservableSet<String>();
		sourceSet.addDeleteListener(new SetElementRemovedListener<String>() {
			@Override
			public void removed(ObservableSet<String> set, String element) {
				targetSet.remove(element);
			}
		});
		sourceSet.addInsertListener(new SetElementInsertedListener<String>() {
			@Override
			public void inserted(ObservableSet<String> set, String element) {
				targetSet.add(element);
			}
		});
		targetSet = Sets.newHashSet();
	}
	
	@After
	public void teardown() {
		sourceSet = null;
		targetSet = null;
	}
	
	protected void assertTargetSet(String concatenated) {
		List<String> sortedSet = Lists.newArrayList(targetSet); 
		Collections.sort(sortedSet);
		assertEquals(concatenated,  Joiner.on("").join(sortedSet));
	}
	
	@Test
	public void testAdd() {
		sourceSet.add("a");
		sourceSet.add("b");
		sourceSet.add("a");
		assertTargetSet("ab");
	}
	
	@Test
	public void testAddAll() {
		sourceSet.addAll(ImmutableList.of("b", "a", "a"));
		assertTargetSet("ab");
	}

	@Test
	public void testRemoveObject() {
		sourceSet.addAll(ImmutableList.of("a", "b", "c"));
		sourceSet.remove("b");
		assertTargetSet("ac");
	}

	@Test
	public void testRemoveAll() {
		sourceSet.addAll(ImmutableList.of("a", "b", "c"));
		sourceSet.removeAll(ImmutableList.of("b", "c"));
		assertTargetSet("a");
	}
	
	@Test
	public void testRetainAll() {
		sourceSet.addAll(ImmutableList.of("a", "b", "c"));
		sourceSet.retainAll(ImmutableList.of("b", "c"));
		assertTargetSet("bc");
	}
	
	@Test
	public void testClear() {
		sourceSet.addAll(ImmutableList.of("a", "b", "c"));
		assertTargetSet("abc");
		sourceSet.clear();
		assertEquals(0, targetSet.size());
	}
	
	@Test
	public void testIterator() {
		sourceSet.addAll(ImmutableList.of("b", "a", "c"));
		Iterator<String> itr = sourceSet.iterator();
		assertEquals(3, targetSet.size());
		while (itr.hasNext()) {
			itr.next();
			itr.remove();
		}
		assertEquals(0, targetSet.size());
	}
}
