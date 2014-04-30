package com.doctusoft.bean.binding.observable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.doctusoft.bean.GenericListeners;
import com.doctusoft.bean.ListenerRegistration;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ObservableSet<T> extends ForwardingSet<T> {

	protected InsertListeners<T> insertListeners = new InsertListeners<T>();
	protected RemoveListeners<T> removeListeners = new RemoveListeners<T>();
	
	protected Set<T> delegate;
	
	@Override
	protected Set<T> delegate() {
		return delegate;
	}
	
	public ObservableSet() {
		delegate = Sets.newHashSet();
	}
	
	public ObservableSet(int size) {
		delegate = Sets.newHashSetWithExpectedSize(size);
	}
	
	public ObservableSet(Collection<? extends T> c) {
		delegate = Sets.newHashSet(c);
	}
	
	public ListenerRegistration addInsertListener(SetElementInsertedListener<T> listener) {
		return insertListeners.addListener(listener);
	}
	
	public ListenerRegistration addDeleteListener(SetElementRemovedListener<T> listener) {
		return removeListeners.addListener(listener);
	}
	
	public interface SetElementInsertedListener<T> {
		public void inserted(ObservableSet<T> set, T element);
	}
	
	public interface SetElementRemovedListener<T> {
		public void removed(ObservableSet<T> set, T element);
	}
	
	protected class InsertListeners<T> extends GenericListeners<ObservableSet.SetElementInsertedListener<T>> {
		public void fireEvent(final ObservableSet<T> set, final T element) {
			forEachListener(new ListenerCallback<ObservableSet.SetElementInsertedListener<T>>() {
				public void apply(ObservableSet.SetElementInsertedListener<T> listener) {
					listener.inserted(set, element);
				};
			});
		}
	};
	
	protected class RemoveListeners<T> extends GenericListeners<ObservableSet.SetElementRemovedListener<T>> {
		public void fireEvent(final ObservableSet<T> set, final T element) {
			forEachListener(new ListenerCallback<ObservableSet.SetElementRemovedListener<T>>() {
				public void apply(ObservableSet.SetElementRemovedListener<T> listener) {
					listener.removed(set, element);
				};
			});
		}
	};
	
	// overriding the Set interface methods that change to content:
	
	@Override
	public boolean add(T element) {
		boolean changed = super.add(element);
		if (changed) {
			insertListeners.fireEvent(this, element);
		}
		return changed;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		// can't use the delegate's addAll here, because only a subset of the parameter collection is inserted, the others shouldn't trigger anything 
		boolean changed = false;
		for (T item : c) {
			if (super.add(item)) {
				insertListeners.fireEvent(this, item);
				changed = true;
			}
		}
		return changed;
	}
	
	@Override
	public void clear() {
		removeAll(Sets.newHashSet(this));
	}
	
	@Override
	public boolean remove(Object o) {
		boolean changed = super.remove(o);
		if (changed) {
			removeListeners.fireEvent(this, (T) o);
		}
		return changed;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for (Object item : c) {
			if (super.remove(item)) {
				removeListeners.fireEvent(this, (T) item);
				changed = true;
			}
		}
		return changed;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changed = false;
		for (T element : Sets.newHashSet(this)) {
			if (!c.contains(element)) {
				remove(element);
				changed = true;
			}
		}
		return changed;
	}
	
	@Override 
	public Iterator<T> iterator() {
		/* This is a pretty ugly solution: I make a copy at the beginning of the iteration and use that, which satisfies the specification:
		 * " The behavior of an iterator is unspecified if the underlying collection is modified while the iteration is in progress" */
		final List<T> iteratorList = Lists.newArrayList(delegate);
		
		return new Iterator<T>() {
			private int index = 0;
			private int lastReturned = -1;
			
			@Override
			public boolean hasNext() {
				return iteratorList.size() > index;
			}

			@Override
			public T next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				lastReturned = index;
				return iteratorList.get(index++);
			}

			@Override
			public void remove() {
				if (lastReturned < 0) {
					throw new IllegalStateException();
				}
				ObservableSet.this.remove(iteratorList.get(lastReturned)); // triggers the handlers
				iteratorList.remove(lastReturned);
				index--;
				lastReturned = -1;
			}
		};
	}
}
