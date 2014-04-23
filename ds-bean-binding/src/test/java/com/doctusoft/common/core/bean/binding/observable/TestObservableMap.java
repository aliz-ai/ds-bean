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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.doctusoft.common.core.bean.binding.observable.ObservableMap.MapElementInsertedListener;
import com.doctusoft.common.core.bean.binding.observable.ObservableMap.MapElementRemovedListener;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TestObservableMap {
	
	private ObservableMap<String, String> sourceMap;
	private Map<String, String> targetMap;

	@Before
	public void setup() {
		sourceMap = new ObservableMap<String, String>();
		sourceMap.addDeleteListener(new MapElementRemovedListener<String, String>() {
			@Override
			public void removed(ObservableMap<String, String> map, String key, String element) {
				targetMap.remove(key);
			}
		});
		sourceMap.addInsertListener(new MapElementInsertedListener<String, String>() {
			@Override
			public void inserted(ObservableMap<String, String> map, String key, String element) {
				targetMap.put(key, element);
			}
		});
		targetMap = Maps.newHashMap();
	}
	
	@After
	public void teardown() {
		sourceMap = null;
		targetMap = null;
	}
	
	protected void assertTargetMap(String concatenated) {
		assertEquals(concatenated, entrySetToString(targetMap.entrySet())); 
	}
	
	protected void assertCollection(Collection<String> collection, String concatenated) {
		List<String> sortedSet = Lists.newArrayList(collection); 
		Collections.sort(sortedSet);
		assertEquals(concatenated,  Joiner.on("").join(sortedSet));
	}
	
	protected void assertEntrySet(Set<Entry<String, String>> entries, String concatenated) {
		assertEquals(concatenated, entrySetToString(entries));
	}
	
	private String entrySetToString(Set<Entry<String, String>> entries) {
		List<String> sortedEntries = Lists.newArrayList(Lists.transform(Lists.newArrayList(entries), new Function<Entry<String, String>, String>() {
			@Override
			public String apply(Entry<String, String> input) {
				return input.getKey() + ":" + input.getValue();
			}
		}));
		Collections.sort(sortedEntries);
		return Joiner.on("|").join(sortedEntries);
	}
	
	@Test
	public void testAdd() {
		sourceMap.put("a", "2");
		sourceMap.put("c", "1");
		sourceMap.put("b", "3");
		assertTargetMap("a:2|b:3|c:1");
		sourceMap.put("b", "10"); // replace
		assertTargetMap("a:2|b:10|c:1");
	}
	
	@Test
	public void testAddAll() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
	}

	@Test
	public void testRemove() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		sourceMap.remove("b");
		assertTargetMap("a:2|c:1");
		sourceMap.remove("b"); // to make sure it doesn't fail
		assertTargetMap("a:2|c:1");
	}
	
	@Test
	public void testClear() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
		sourceMap.clear();
		assertEquals(0, targetMap.size());
	}
	
	@Test
	public void testKeySet() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
		Set<String> keys = sourceMap.keySet();
		keys.remove("c");
		assertTargetMap("a:2|b:3");
	}
	
	@Test
	public void testValues() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
		Collection<String> values = sourceMap.values();
		values.remove("2");
		assertTargetMap("b:3|c:1");
	}
	
	@Test
	public void testEntrySet() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
		Set<Entry<String, String>> entries = sourceMap.entrySet();
		entries.removeAll(ImmutableMap.of("a", "2", "c", "1").entrySet());
		assertTargetMap("b:3");
	}
	
	@Test
	public void testKeySet2() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
		Set<String> keys = targetMap.keySet();
		assertCollection(keys, "abc");
		sourceMap.remove("c");
		assertCollection(keys, "ab");
	}
	
	@Test
	public void testValues2() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
		Collection<String> values = targetMap.values();
		assertCollection(values, "123");
		sourceMap.remove("a");
		assertCollection(values, "13");
	}
	
	@Test
	public void testEntrySet2() {
		sourceMap.putAll(ImmutableMap.of("a", "2", "c", "1", "b", "3"));
		assertTargetMap("a:2|b:3|c:1");
		Set<Entry<String, String>> entries = targetMap.entrySet();
		sourceMap.remove("c");
		assertEntrySet(entries, "a:2|b:3");
	}
}
