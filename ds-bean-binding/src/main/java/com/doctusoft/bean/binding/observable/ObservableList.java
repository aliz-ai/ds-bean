package com.doctusoft.bean.binding.observable;

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


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

import com.doctusoft.bean.GenericListeners;
import com.doctusoft.bean.ListenerRegistration;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.Lists;

public class ObservableList<T> extends ForwardingList<T> implements RandomAccess {
	
	protected InsertListeners<T> insertListeners = new InsertListeners<T>();
	protected RemoveListeners<T> removeListeners = new RemoveListeners<T>();
	
	/**
	 * We use encapsulation, because subclassing can result in unexpected errors, when the ArrayList is implemented differently: for example the GWT implementation of the .remove(Object o) redirects to remove(int index),
	 * while the JVM implementation does not. If we overwrite the methods, the listeners in GWT would fire twice: first for the originally called method and then in the redirected one. 
	 */
	protected List<T> delegate;
	
	@Override
	protected List<T> delegate() {
		return delegate;
	}
	
	public ObservableList() {
		delegate = Lists.newArrayList();
	}
	
	public ObservableList(int initialCapacity) {
		delegate = Lists.newArrayListWithCapacity(initialCapacity);
	}
	
	public ObservableList(Collection<? extends T> c) {
		delegate = Lists.newArrayList(c);
	}
	
	public ListenerRegistration addInsertListener(ListElementInsertedListener<T> listener) {
		return insertListeners.addListener(listener);
	}
	
	public ListenerRegistration addDeleteListener(ListElementRemovedListener<T> listener) {
		return removeListeners.addListener(listener);
	}
	
	
	public interface ListElementInsertedListener<T> {
		public void inserted(ObservableList<T> list, int index, T element);
	}
	
	public interface ListElementRemovedListener<T> {
		public void removed(ObservableList<T> list, int index, T element);
	}
	
	protected class InsertListeners<T> extends GenericListeners<ObservableList.ListElementInsertedListener<T>> {
		public void fireEvent(final ObservableList<T> list, final int index, final T element) {
			forEachListener(new ListenerCallback<ObservableList.ListElementInsertedListener<T>>() {
				public void apply(ObservableList.ListElementInsertedListener<T> listener) {
					listener.inserted(list, index, element);
				};
			});
		}
	};

	protected class RemoveListeners<T> extends GenericListeners<ObservableList.ListElementRemovedListener<T>> {
		public void fireEvent(final ObservableList<T> list, final int index, final T element) {
			forEachListener(new ListenerCallback<ObservableList.ListElementRemovedListener<T>>() {
				public void apply(ObservableList.ListElementRemovedListener<T> listener) {
					listener.removed(list, index, element);
				};
			});
		}
	};
	
	@Override
	public boolean add(T e) {
		boolean result = super.add(e);
		insertListeners.fireEvent(this, size() - 1, e);
		return result;
	}
	
	@Override
	public boolean remove(Object o) {
		int index = indexOf(o);
		boolean result = super.remove(o);
		if (result) {
			removeListeners.fireEvent(this, index, (T) o);
		}
		return result;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		return addAll(size(), c);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean result = super.addAll(index, c);
		if (result) {
			for (int i = index; i < index + c.size(); i ++) {
				insertListeners.fireEvent(this, i, get(i));
			}
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object item : c) {
			modified |= remove(item);
		}
		return modified;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		for (T element : Lists.newArrayList(this)) {
			if (!c.contains(element)) {
				remove(element);
				modified = true;
			}
		}
		return modified;
	}
	
	@Override
	public void clear() {
		while (size() > 0) {
			remove(0);
		}
	}
	
	@Override
	public T set(int index, T element) {
		T oldValue = super.set(index, element);
		removeListeners.fireEvent(this, index, oldValue);
		insertListeners.fireEvent(this, index, element);
		return oldValue;
	}
	
	@Override
	public void add(int index, T element) {
		super.add(index, element);
		insertListeners.fireEvent(this, index, element);
	}
	
	@Override
	public T remove(int index) {
		T oldValue = super.remove(index);
		removeListeners.fireEvent(this, index, oldValue);
		return oldValue;
	}
	
	/**
	 * This works semantically correctly, but this actually copies the given sublist, so it probably doesn't work as fast
	 *  as standard JRE classes.
	 */
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		ObservableList<T> list = new ObservableList<T>();
		list.addAll(super.subList(fromIndex, toIndex));
		return list;
	}
	
	@Override 
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException(); // TODO
	}
	
	@Override 
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int index = 0;
			private int lastReturned = -1;
			
			@Override
			public boolean hasNext() {
				return delegate.size() > index;
			}

			@Override
			public T next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				lastReturned = index;
				return delegate.get(index++);
			}

			@Override
			public void remove() {
				if (lastReturned < 0) {
					throw new IllegalStateException();
				}
				ObservableList.this.remove(lastReturned); // triggers the handlers
				index--;
				lastReturned = -1;
			}
		};
	}
}
