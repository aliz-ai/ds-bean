package com.doctusoft.common.core.bean.binding.observable;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.ListIterator;

import org.easymock.EasyMock;
import org.junit.Test;

import com.doctusoft.bean.binding.Bindings;
import com.doctusoft.bean.binding.Converter;
import com.doctusoft.bean.binding.observable.BidirectionalConvertingListBinder;
import com.doctusoft.bean.binding.observable.ObservableList;
import com.doctusoft.bean.binding.observable.ObservableList.ListElementInsertedListener;
import com.doctusoft.bean.binding.observable.ObservableList.ListElementRemovedListener;

public class TestObservableListIterator {
	
	@Test
	public void testSet() {
		ObservableList<String> list = new ObservableList<String>();
		list.add("first");
		ListElementInsertedListener insertListener = EasyMock.createStrictMock(ListElementInsertedListener.class);
		ListElementRemovedListener removeListener = EasyMock.createStrictMock(ListElementRemovedListener.class);
		list.addInsertListener(insertListener);
		list.addDeleteListener(removeListener);
		insertListener.inserted(list, 0, "second");
		removeListener.removed(list, 0, "first");
		ListIterator<String> iterator = list.listIterator();
		iterator.next();
		EasyMock.replay(insertListener);
		EasyMock.replay(removeListener);
		iterator.set("second");
		EasyMock.verify(insertListener);
		EasyMock.verify(removeListener);
		assertEquals("second", list.get(0));
	}

	@Test
	public void testObservedSort() {
		ObservableList<String> stringList = new ObservableList<String>();
		ObservableList<Integer> intList = new ObservableList<Integer>();
		Converter<String, Integer> converter = new Converter<String, Integer>() {
			@Override
			public String convertTarget(Integer target) {
				return target.toString();
			}
			
			@Override
			public Integer convertSource(String source) {
				return Integer.valueOf(source);
			}
		};
		new BidirectionalConvertingListBinder<String, Integer>(Bindings.obs(stringList), converter, Bindings.obs(intList));
		stringList.add("3");
		stringList.add("2");
		stringList.add("1");
		assertEquals("[3, 2, 1]", stringList.toString());
		assertEquals("[3, 2, 1]", intList.toString());
		Collections.sort(stringList);
		assertEquals("[1, 2, 3]", stringList.toString());
		assertEquals("[1, 2, 3]", intList.toString());
	}
}
