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

import org.junit.Test;

import com.doctusoft.ObservableProperty;
import com.doctusoft.bean.binding.Bindings;
import com.doctusoft.bean.binding.Converter;
import com.doctusoft.bean.binding.observable.BidirectionalConvertingListBinder;
import com.doctusoft.bean.binding.observable.ObservableList;
import com.google.common.base.Joiner;

public class TestConvertingListBinder {
	
	@ObservableProperty
	private ObservableList<Integer> sourceList = new ObservableList<Integer>();
	
	@ObservableProperty
	private ObservableList<String> targetList = new ObservableList<String>();
	
	@Test
	public void testConvertingListBinding() {
		sourceList.add(0);
		new BidirectionalConvertingListBinder<Integer, String>(Bindings.obs(this).get(TestConvertingListBinder_._sourceList),
				new IntegerStringConverter(), Bindings.obs(this).get(TestConvertingListBinder_._targetList));
		// test initial list copy
		assertLists("0");

		// test insertions
		sourceList.add(1);
		assertLists("01");
		targetList.add("2");
		assertLists("012");
		
		// test removals
		sourceList.remove(0);
		assertLists("12");
		sourceList.remove(0);
		assertLists("2");
		
		// test new source list assigned
		ObservableList<Integer> tempSourceList = new ObservableList<Integer>();
		tempSourceList.add(3);
		setSourceList(tempSourceList);
		assertLists("3");
		
		// test new target list assigned
		ObservableList<String> tempTargetList = new ObservableList<String>();
		tempTargetList.add("4");
		setTargetList(tempTargetList);
		assertLists("4");
	}
	
	protected void assertLists(String concatenated) {
		assertEquals(concatenated,  Joiner.on("").join(targetList));
		assertEquals(concatenated,  Joiner.on("").join(sourceList));
	}

	private static class IntegerStringConverter implements Converter<Integer, String> {
		@Override
		public String convertSource(Integer source) {
			return source.toString();
		}
		@Override
		public Integer convertTarget(String target) {
			return Integer.parseInt(target);
		}
	}

}
