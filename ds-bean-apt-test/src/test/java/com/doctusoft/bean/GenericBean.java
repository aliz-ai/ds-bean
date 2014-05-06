package com.doctusoft.bean;

/*
 * #%L
 * ds-bean-apt-test
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
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.doctusoft.Property;

@Getter @Setter
public class GenericBean<T> {
	
	@Property
	private T genericField;
	
	@Property
	private Map<String, T> genericMapField;

	@Property
	private Map<String, List<T>> genericMultimapField;
}
