package com.google.gwt.user.client.rpc.core.com.doctusoft.bean.binding.observable;

import java.util.List;

import com.doctusoft.bean.binding.observable.ObservableList;
import com.google.common.collect.Lists;
import com.google.gwt.user.client.rpc.CustomFieldSerializer;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

public class ObservableList_CustomFieldSerializer extends CustomFieldSerializer<ObservableList> {
	
	@Override
	public void deserializeInstance(SerializationStreamReader streamReader, ObservableList instance) throws SerializationException {
		deserialize(streamReader, instance);
	}
	
	@Override
	public void serializeInstance(SerializationStreamWriter streamWriter, ObservableList instance) throws SerializationException {
		serialize(streamWriter, instance);
	}
	
	@Override
	public boolean hasCustomInstantiateInstance() {
		return true;
	}
	
	@Override
	public ObservableList instantiateInstance(SerializationStreamReader streamReader) throws SerializationException {
		return new ObservableList();
	}

	public static void deserialize(SerializationStreamReader streamReader, ObservableList instance) throws SerializationException {
		List list = (List) streamReader.readObject();
		instance.addAll(list);
	}
	
	public static void serialize(SerializationStreamWriter streamWriter, ObservableList instance) throws SerializationException {
		streamWriter.writeObject(Lists.newArrayList(instance));
	}
}
