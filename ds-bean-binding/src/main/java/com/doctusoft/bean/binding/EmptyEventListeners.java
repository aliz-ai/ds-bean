package com.doctusoft.bean.binding;

import com.doctusoft.bean.GenericListeners;

public class EmptyEventListeners extends GenericListeners<EmptyEventHandler> {
	
	public void fireListeners() {
		forEachListener(new ListenerCallback<EmptyEventHandler>() {
			@Override
			public void apply(EmptyEventHandler listener) {
				listener.handle();
			}
		});
	}

}
