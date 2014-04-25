package com.doctusoft.common.core.bean.binding.observable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.doctusoft.bean.GenericListeners;
import com.doctusoft.bean.ListenerRegistration;
import com.doctusoft.common.core.bean.binding.observable.ObservableList.ListElementRemovedListener;
import com.doctusoft.common.core.bean.binding.observable.ObservableSet.SetElementRemovedListener;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ObservableMap<K, V> extends ForwardingMap<K, V> {

	protected InsertListeners<K, V> insertListeners = new InsertListeners<K, V>();
	protected RemoveListeners<K, V> removeListeners = new RemoveListeners<K, V>();
	
	protected Map<K, V> delegate;
	
	@Override
	protected Map<K, V> delegate() {
		return delegate;
	}
	
	public ObservableMap() {
		delegate = Maps.newHashMap();
	}
	
	public ObservableMap(int size) {
		delegate = Maps.newHashMapWithExpectedSize(size);
	}
	
	public ListenerRegistration addInsertListener(MapElementInsertedListener<K, V> listener) {
		return insertListeners.addListener(listener);
	}
	
	public ListenerRegistration addDeleteListener(MapElementRemovedListener<K, V> listener) {
		return removeListeners.addListener(listener);
	}
	
	public interface MapElementInsertedListener<K, V> {
		public void inserted(ObservableMap<K, V> map, K key, V element);
	}
	
	public interface MapElementRemovedListener<K, V> {
		public void removed(ObservableMap<K, V> map, K key, V element);
	}
	
	protected class InsertListeners<K, V> extends GenericListeners<ObservableMap.MapElementInsertedListener<K, V>> {
		public void fireEvent(final ObservableMap<K, V> map, final K key, final V element) {
			forEachListener(new ListenerCallback<ObservableMap.MapElementInsertedListener<K, V>>() {
				public void apply(ObservableMap.MapElementInsertedListener<K, V> listener) {
					listener.inserted(map, key, element);
				};
			});
		}
	};
	
	protected class RemoveListeners<K, V> extends GenericListeners<ObservableMap.MapElementRemovedListener<K, V>> {
		public void fireEvent(final ObservableMap<K, V> map, final K key, final V element) {
			forEachListener(new ListenerCallback<ObservableMap.MapElementRemovedListener<K, V>>() {
				public void apply(ObservableMap.MapElementRemovedListener<K, V> listener) {
					listener.removed(map, key, element);
				};
			});
		}
	};
	
	// overriding the Map interface methods that change to content:
	
	@Override
	public void clear() {
		for (K key : Sets.newHashSet(this.keySet())) {
			remove(key);
		}
	}
	
	@Override
	public V put(K key, V value) {
		V previous = super.put(key, value);
		if (previous != null) {
			removeListeners.fireEvent(this, key, previous);
		}
		insertListeners.fireEvent(this, key, value);
		return previous;
	}
	
	@Override
	public void putAll(Map<? extends K,? extends V> m) {
		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public V remove(Object key) {
		V removed = super.remove(key);
		if (removed != null) {
			removeListeners.fireEvent(this, (K) key, removed);
		}
		return removed;
	}
	
	/* The keySet, values and entrySet collections must be such, that changes to them are reflected in the original collection and vica-versa
	 * (And thus they should trigger the change handlers, but the default implementations avoid that, modifying the map under the hood, not by its public methods). */
	
	/* Since the change handlers broadcast to everyone, there is always a redundant callback to the original sender in case of two-way bindings like here between the map and its views.
	 * However this doesn't cause an infinite loop in these cases, because the remove operations stop propagating when there is nothing to remove 
	 * (e.g. map.remove() -> keySet.remove() -> map.remove(), but the last one returns null and doesn't fire any more handlers since there was no change to the collection) */ 
	
	@Override
	public Collection<V> values() {
		final ObservableList<V> values = new ObservableList<V>(delegate.values());
		values.addDeleteListener(new ListElementRemovedListener<V>() {
			@Override
			public void removed(ObservableList<V> list, int index, V element) {
				Set<K> keysToRemove = Sets.newHashSet();
				for (Entry<K, V> entry : delegate.entrySet()) {
					if (entry.getValue() == element) { // intentially == not equals
						keysToRemove.add(entry.getKey());
					}
				}
				for (K key : keysToRemove) {
					remove(key);
				}
			}
		});
		addInsertListener(new MapElementInsertedListener<K, V>() {
			@Override
			public void inserted(ObservableMap<K, V> map, K key, V element) {
				values.add(element);
			}
		});
		addDeleteListener(new MapElementRemovedListener<K, V>() {
			@Override
			public void removed(ObservableMap<K, V> map, K key, V element) {
				values.remove(element);
			}
		});
		return values;
	}
	
	@Override
	public Set<K> keySet() {
		final ObservableSet<K> keys = new ObservableSet<K>(delegate.keySet());
		keys.addDeleteListener(new SetElementRemovedListener<K>() {
			@Override
			public void removed(ObservableSet<K> set, K element) {
				remove(element);
			}
		});
		addInsertListener(new MapElementInsertedListener<K, V>() {
			@Override
			public void inserted(ObservableMap<K, V> map, K key, V element) {
				keys.add(key);
			}
		});
		addDeleteListener(new MapElementRemovedListener<K, V>() {
			@Override
			public void removed(ObservableMap<K, V> map, K key, V element) {
				keys.remove(key);
			}
		});
		return keys;
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		final ObservableSet<Entry<K, V>> keys = new ObservableSet<Entry<K, V>>(delegate.entrySet());
		keys.addDeleteListener(new SetElementRemovedListener<Entry<K, V>>() {
			@Override
			public void removed(ObservableSet<Entry<K, V>> set, Entry<K, V> element) {
				remove(element.getKey());
			}
		});
		// the entrySet doesn't support adding elements either
		addInsertListener(new MapElementInsertedListener<K, V>() {
			@Override
			public void inserted(ObservableMap<K, V> map, K key, V element) {
				keys.add(Maps.immutableEntry(key, element));
			}
		});
		addDeleteListener(new MapElementRemovedListener<K, V>() {
			@Override
			public void removed(ObservableMap<K, V> map, K key, V element) {
				keys.remove(Maps.immutableEntry(key, element));
			}
		});
		return keys;
	}
}
