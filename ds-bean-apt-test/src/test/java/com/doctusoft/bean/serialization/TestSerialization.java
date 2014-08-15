package com.doctusoft.bean.serialization;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

import com.doctusoft.bean.binding.Bindings;

public class TestSerialization {

	private static class TransientTestHolder implements Serializable {
		TransientModel model1 = new TransientModel();
		TransientModel model2 = new TransientModel();
		public TransientTestHolder() {
			Bindings.bind(Bindings.obs(model1).get(TransientModel_._stringField), Bindings.obs(model2).get(TransientModel_._stringField));
		}
	}
	
	@Test
	public void testTransientModel() {
		TransientTestHolder testHolder = new TransientTestHolder();
		// assert that listeners work
		testHolder.model1.setStringField("1");
		assertEquals("1", testHolder.model2.getStringField());
		// now do the magic on model
		testHolder = serializeAndDeserialize(testHolder);
		// and now assert that listeners aren't saved, the change is not propagated
		testHolder.model1.setStringField("2");
		assertEquals("1", testHolder.model2.getStringField());
	}

	private static class SerializableTestHolder implements Serializable {
		SerializableModel model1 = new SerializableModel();
		SerializableModel model2 = new SerializableModel();
		public SerializableTestHolder() {
			Bindings.bind(Bindings.obs(model1).get(SerializableModel_._stringField), Bindings.obs(model2).get(SerializableModel_._stringField));
		}
	}

	@Test
	public void testSerializableModel() {
		SerializableTestHolder testHolder = new SerializableTestHolder();
		// assert that listeners work
		testHolder.model1.setStringField("1");
		assertEquals("1", testHolder.model2.getStringField());
		// now do the magic on model
		testHolder = serializeAndDeserialize(testHolder);
		// and now assert that listeners and bindings were also serialized and are still working
		testHolder.model1.setStringField("2");
		assertEquals("2", testHolder.model2.getStringField());
	}


	protected static <T> T serializeAndDeserialize(T object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(object);
			return (T) new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
