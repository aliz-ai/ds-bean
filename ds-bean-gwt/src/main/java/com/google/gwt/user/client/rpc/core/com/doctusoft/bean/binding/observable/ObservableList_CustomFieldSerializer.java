package com.google.gwt.user.client.rpc.core.com.doctusoft.bean.binding.observable;

/*
 * #%L
 * ds-bean-gwt
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
