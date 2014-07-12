package com.doctusoft.common.core.bean.binding.observable;

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
