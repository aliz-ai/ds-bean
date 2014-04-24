package com.doctusoft.common.core.bean;

/*
 * #%L
 * ds-bean-apt
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


import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@SupportedAnnotationTypes("com.doctusoft.Attribute")
public class AnnotationProcessor extends AbstractProcessor {
	
	/**
	 * Attribute descriptors by class typename
	 */
	Multimap<TypeElement, AttributeDescriptor> attributeDescriptors = ArrayListMultimap.create();
	private Filer filer; 
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		// collect data
		for (Element element : roundEnv.getElementsAnnotatedWith(com.doctusoft.Attribute.class)) {
			if (element.getKind() == ElementKind.CLASS) {
				// TODO handle all fields and / or getters of the class
			}
			if (element.getKind() == ElementKind.FIELD) {
				VariableElement variableElement = (VariableElement) element;
				AttributeDescriptor descriptor = new AttributeDescriptor();
				descriptor.setFieldName(element.getSimpleName().toString());
				descriptor.setFieldTypeName(variableElement.asType().toString());
				System.out.println("type: " + variableElement.asType().toString());
				Element enclosingElement = variableElement.getEnclosingElement();
				System.out.println("enclosing type: " + enclosingElement);
				attributeDescriptors.put((TypeElement) enclosingElement, descriptor); 
			}
		}
		filer = processingEnv.getFiler();
		for (TypeElement typeElement : attributeDescriptors.keySet()) {
			emitClassSource(typeElement, attributeDescriptors.get(typeElement));
		}
		return true;
	}

	public void emitClassSource(TypeElement enclosingType, Iterable<AttributeDescriptor> descriptors) {
		try {
			JavaFileObject source = filer.createSourceFile(enclosingType.getQualifiedName() + "_");
			PackageElement pck = (PackageElement) enclosingType.getEnclosingElement();
			Writer writer = source.openWriter();
			writer.write("package " + pck.getQualifiedName() + ";\n");
			writer.write("public class " + enclosingType.getSimpleName() + "_ {\n");
			for (AttributeDescriptor descriptor : descriptors) {
				writer.write("    public static final com.doctusoft.common.core.bean.Attribute<"
						+ enclosingType.getSimpleName() + "," + descriptor.getFieldTypeName() + "> " + descriptor.getFieldName() + " = null;");
			}
			writer.write("}");
			writer.close();
		} catch (FilerException e) {
			// the file probably already existed, nothing to do
			// TODO more concise exception handling
		} catch (Exception e) {
			throw new RuntimeException("error creating source file for type: " + enclosingType, e);
		}
	}
}
