package com.doctusoft.common.core.bean.binding.observable;

import org.easymock.EasyMock;
import org.junit.Test;

import com.doctusoft.bean.binding.Bindings;
import com.doctusoft.bean.binding.EmptyEventHandler;
import com.doctusoft.bean.binding.observable.ObservableList;
import com.doctusoft.bean.binding.observable.PropertyObservingListBinding;

public class TestPropertyObservingListBinding {
	
	@Test
	public void testItemChange() {
		ObservableList<TestBean> list = new ObservableList<TestBean>();
		list.add(new TestBean("a"));
		final EmptyEventHandler event = EasyMock.createStrictMock(EmptyEventHandler.class);
		event.handle();
		EasyMock.replay(event);
		new PropertyObservingListBinding<TestBean>(Bindings.obs(list), TestBean_._stringValue) {
			@Override
			protected void itemChanged(ObservableList<TestBean> list, TestBean element) {
				event.handle();
			}
		};
		list.get(0).setStringValue("b");
		EasyMock.verify(event);
	}
	
	@Test
	public void testListenersRemoved() {
		ObservableList<TestBean> list = new ObservableList<TestBean>();
		TestBean bean = new TestBean("a");
		list.add(bean);
		final EmptyEventHandler event = EasyMock.createStrictMock(EmptyEventHandler.class);
		event.handle();
		EasyMock.replay(event);
		new PropertyObservingListBinding<TestBean>(Bindings.obs(list), TestBean_._stringValue) {
			@Override
			protected void itemChanged(ObservableList<TestBean> list, TestBean element) {
				event.handle();
			}
		};
		bean.setStringValue("b");
		list.clear();
		bean.setStringValue("c");
		EasyMock.verify(event);
	}

	@Test
	public void testNullItem() {
		ObservableList<TestBean> list = new ObservableList<TestBean>();
		list.add(null);
		new PropertyObservingListBinding<TestBean>(Bindings.obs(list), TestBean_._stringValue) {
			@Override
			protected void itemChanged(ObservableList<TestBean> list, TestBean element) {
			}
		};
	}
	
	@Test
	public void testDuplicateItems() {
		ObservableList<TestBean> list = new ObservableList<TestBean>();
		TestBean bean = new TestBean("a");
		list.add(bean);
		list.add(bean);
		final EmptyEventHandler event = EasyMock.createStrictMock(EmptyEventHandler.class);
		event.handle();
		EasyMock.replay(event);
		new PropertyObservingListBinding<TestBean>(Bindings.obs(list), TestBean_._stringValue) {
			@Override
			protected void itemChanged(ObservableList<TestBean> list, TestBean element) {
				event.handle();
			}
		};
		list.get(0).setStringValue("b");
		EasyMock.verify(event);
	}
	

}
