package com.doctusoft.bean.apt;

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


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import com.doctusoft.MethodRef;
import com.doctusoft.ObservableProperty;
import com.doctusoft.Property;
import com.doctusoft.bean.ModelObject;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

@SupportedAnnotationTypes({"com.doctusoft.Property", "com.doctusoft.ObservableProperty", "com.doctusoft.MethodRef"})
public class AnnotationProcessor extends AbstractProcessor {
	
	/**
	 * Property descriptors by class typename
	 */
	private Multimap<TypeElement, ElementDescriptor> elementDescriptors = ArrayListMultimap.create();
	private Filer filer;
	/**
	 * if it's a type parameter
	 */
	private String currentFieldTypeName = null;
	
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		// collect annotations and their elements
		List<PropertyDescriptor> descriptors = Lists.newArrayList();
		for (Element element : roundEnv.getElementsAnnotatedWith(com.doctusoft.Property.class)) {
			try {
				PropertyDescriptor descriptor = new PropertyDescriptor();
				descriptor.setElement(element);
				Property annotation = element.getAnnotation(com.doctusoft.Property.class);
				descriptor.setReadonly(annotation.readonly());
				descriptors.add(descriptor);
			} catch (Exception e) {
				throw new RuntimeException("Exception with element: " + element + ", " + element.getEnclosingElement(), e);
			}
		}
		for (Element element : roundEnv.getElementsAnnotatedWith(com.doctusoft.ObservableProperty.class)) {
			try {
				PropertyDescriptor descriptor = new PropertyDescriptor();
				descriptor.setElement(element);
				ObservableProperty annotation = element.getAnnotation(com.doctusoft.ObservableProperty.class);
				descriptor.setReadonly(annotation.readonly());
				descriptor.setObservable(true);
				descriptors.add(descriptor);
			} catch (Exception e) {
				throw new RuntimeException("Exception with element: " + element + ", " + element.getEnclosingElement(), e);
			}
		}
		// extract some more data
		for (PropertyDescriptor descriptor : descriptors) {
			Element element = descriptor.getElement();
			try {
				if (element.getKind() == ElementKind.CLASS) {
					// TODO handle all fields and / or getters of the class
				}
				if (element.getKind() == ElementKind.FIELD) {
					VariableElement variableElement = (VariableElement) element;
					descriptor.setFieldName(element.getSimpleName().toString());
					descriptor.setType(variableElement.asType());
					descriptor.setElement(element);
					Element enclosingElement = variableElement.getEnclosingElement();
					elementDescriptors.put((TypeElement) enclosingElement, descriptor);
				}
				if (element.getKind() == ElementKind.METHOD) {
					ExecutableElement methodElement = (ExecutableElement) element;
					// ensure that the method is on a getter
					String fieldName = getFieldNameFromGetter(methodElement);
					if (fieldName == null) {
						processingEnv.getMessager().printMessage(Kind.ERROR, "@Property must be on a getter method", methodElement);
					}
					fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
					descriptor.setFieldName(fieldName);
					ExecutableType type = (ExecutableType) methodElement.asType();
					descriptor.setType(type.getReturnType());
					descriptor.setElement(element);
					Element enclosingElement = methodElement.getEnclosingElement();
					elementDescriptors.put((TypeElement) enclosingElement, descriptor); 
				}
			} catch (Exception e) {
				throw new RuntimeException("Exception with element: " + element + ", " + element.getEnclosingElement(), e);
			}
		}
		for (Element element: roundEnv.getElementsAnnotatedWith(MethodRef.class)) {
			try {
				MethodDescriptor descriptor = new MethodDescriptor();
				ExecutableElement methodElement = (ExecutableElement) element;
				TypeMirror type = (TypeMirror) methodElement.getReturnType();
				descriptor.setType(type);
				descriptor.setElement(methodElement);
				descriptor.setMethodName(methodElement.getSimpleName().toString());
				Element enclosingElement = methodElement.getEnclosingElement();
				elementDescriptors.put((TypeElement) enclosingElement, descriptor);
			} catch (Exception e) {
				throw new RuntimeException("Exception with element: " + element + ", " + element.getEnclosingElement(), e);
			}
		}
		// emit source files
		filer = processingEnv.getFiler();
		for (TypeElement typeElement : elementDescriptors.keySet()) {
			try {
				emitClassSource(typeElement, elementDescriptors.get(typeElement));
			} catch (UnresolvedTypeException e) {
				// do nothing, we will not emit this source file, hoping that in the next round we'll succeed
				// (APT should get invoked again and again as long as new source files are generated)
			}
		}
		return true;
 	}
	
	public String getFieldNameFromGetter(ExecutableElement element) {
		String methodName = element.getSimpleName().toString();
		String returnType = ((ExecutableType) element.asType()).getReturnType().toString();
		if (methodName.startsWith("get") || (methodName.startsWith("is") && returnType.equals("boolean"))) {
			if (methodName.startsWith("get")) {
				String fieldName = methodName.substring(3);
				if (fieldName.length() == 0)
					return null;		// not a valid getter
				return fieldName;
			}
			if (methodName.startsWith("is")) {
				String fieldName = methodName.substring(2);
				if (fieldName.length() == 0)
					return null;		// not a valid getter
				return fieldName;
			}
			return null;
		}
		return null;	// this is not a getter
	}
	
	public void emitClassSource(TypeElement enclosingType, Iterable<ElementDescriptor> descriptors) {
		try {
			// generate simple "MyClass_" named static descriptor class
			emitPropertyDescriptorClass(enclosingType, descriptors);
		} catch (FilerException e) {
			// the file probably already existed, nothing to do
			// TODO more concise exception handling
		} catch (UnresolvedTypeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("error creating source file for type: " + enclosingType, e);
		}
	}
	
	public void emitPropertyDescriptorClass(TypeElement enclosingType, Iterable<ElementDescriptor> descriptors) throws Exception {
		PackageElement pck = (PackageElement) enclosingType.getEnclosingElement();
		ByteArrayOutputStream sourceBytes = new ByteArrayOutputStream();
		Writer writer = new PrintWriter(sourceBytes);
		writer.write("package " + pck.getQualifiedName() + ";\n\n");
		writer.write("import com.doctusoft.bean.ModelObject;\n");
		writer.write("import com.doctusoft.bean.Property;\n");
		writer.write("import com.doctusoft.bean.ObservableProperty;\n");
		writer.write("import com.doctusoft.bean.ListenerRegistration;\n");
		writer.write("import com.doctusoft.bean.ValueChangeListener;\n");
		writer.write("import com.doctusoft.bean.internal.PropertyListeners;\n\n");
		writer.write("import com.google.common.collect.ImmutableList;\n");
		DeclaredType holderType = (DeclaredType) enclosingType.asType();
		String holderTypeSimpleName = ((TypeElement) holderType.asElement()).getSimpleName().toString();
		String holderTypeName = holderTypeSimpleName;
		if (!holderType.getTypeArguments().isEmpty()) {
			int parametersCount = holderType.getTypeArguments().size();
			holderTypeName += "<" + Strings.repeat("?,", parametersCount - 1) + "?>";
		}
		writer.write("\npublic class " + holderTypeSimpleName + "_ {\n");
		// write individual descriptors
		for (ElementDescriptor descriptor : descriptors) {
			if (descriptor instanceof PropertyDescriptor) {
				emitPropertyLiteral(writer, (PropertyDescriptor) descriptor, holderType, holderTypeSimpleName);
			}
			if (descriptor instanceof MethodDescriptor) {
				emitMethodLiteral(writer, (MethodDescriptor) descriptor, holderTypeName, holderTypeSimpleName);
			}
		}
		if (typeImplements(enclosingType, ModelObject.class.getName())) {
			emitObservableAttributesList(writer, descriptors, enclosingType);
		}
		writer.write("\n}");
		writer.close();
		// open the file only after everything worked fine and the source is ready
		String fileName = enclosingType.getQualifiedName() + "_";
		JavaFileObject source = filer.createSourceFile(fileName);
		OutputStream os = source.openOutputStream();
		os.write(sourceBytes.toByteArray());
		os.close();
	}
	
	public void emitObservableAttributesList(Writer writer, Iterable<ElementDescriptor> descriptors, TypeElement holderType) throws Exception {
		// write a list of all descriptors
		writer.write("\n    public static Iterable<ObservableProperty<?, ?>> _observableProperties = ImmutableList.<ObservableProperty<?, ?>>builder().add(");
		List<String> descriptorFieldNames = Lists.newArrayList();
		for (ElementDescriptor descriptor : descriptors) {
			if (descriptor instanceof PropertyDescriptor) {
				PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
				if (propertyDescriptor.isObservable()) {
					descriptorFieldNames.add("_" + propertyDescriptor.getFieldName());
				}
			}
		}
		writer.write(Joiner.on(", ").join(descriptorFieldNames));
		writer.write(")");
		// check for supertype
		emitObservableAttributesFromSuperclass(writer, holderType.getSuperclass());
		writer.write(".build();\n\n");
	}
	
	public void emitObservableAttributesFromSuperclass(Writer writer, TypeMirror typeMirror) throws Exception {
		if (typeMirror.getKind() != TypeKind.DECLARED)
			return;
		DeclaredType declaredType = (DeclaredType) typeMirror;
		TypeElement typeElement = (TypeElement) declaredType.asElement();
		if (!typeImplements(typeElement, ModelObject.class.getName()))	// if it's not a ModelObject, we quit the recursion without generating
			return;
		// if it's a modelobject, we have to check if a descriptor class is generated for it
		if (elementDescriptors.containsKey(typeElement)) {
			// if there's a descriptor class, then here we generate the .addAll code
			writer.write(".addAll(" + typeElement.getQualifiedName().toString() + "_._observableProperties)");
		} else {
			// if there's not descriptor class for this superclass, we go further up the hierarchy
			emitObservableAttributesFromSuperclass(writer, typeElement.getSuperclass());
		}
	}

	public void emitMethodLiteral(Writer writer, MethodDescriptor descriptor, String holderTypeName, String holderTypeSimpleName) throws Exception {
		ExecutableElement methodElement = (ExecutableElement) descriptor.getElement();
		int numParams = methodElement.getParameters().size();
		String staticClassName = "com.doctusoft.bean.ParametricClassMethodReferences.ClassMethodReference" + numParams;
		TypeMirror methodType = descriptor.getType();
		ProcessedTypeInfo methodTypeInfo = new ProcessedTypeInfo(methodType, descriptor.getElement());
		String parameterTypes = "";
		for (VariableElement variableElement : methodElement.getParameters()) {
			parameterTypes += "," + getTypeMirrorAsErasedString(variableElement.asType());
		}
		String parametricStaticClassName = staticClassName + "<" + holderTypeName + "," + methodTypeInfo.mappedTypeName + parameterTypes + ">";
		boolean voidType = methodTypeInfo.mappedTypeName.equals("Void");
		
		writer.write("    public static final " + parametricStaticClassName + " __" + methodElement.getSimpleName() + " = new " + parametricStaticClassName + "() {\n"
				+ "        public " + methodTypeInfo.mappedTypeName + " applyInner(" + holderTypeName + " object, Object [] arguments) {\n"
				+ "            " + (voidType?"":"return ") + "object." + methodElement.getSimpleName() + "(");
		List<String> parameterExpressions = Lists.newArrayList();
		for (int i = 0; i < methodElement.getParameters().size(); i ++) {
			VariableElement variableElement = methodElement.getParameters().get(i);
			String typeCast = getTypeMirrorAsErasedString(variableElement.asType());
			if ("?".equals(typeCast)) {
				typeCast = "Object";
			}
			parameterExpressions.add("(" + typeCast + ") arguments[" + i + "]");
		}
		writer.write(Joiner.on(",").join(parameterExpressions));
		writer.write(");\n");
		if (voidType) {
			writer.write("        return null;\n");
		}
		writer.write(
				"        }\n");
		writer.write(
				"        public String getName() { return \"" + methodElement.getSimpleName() + "\";}\n\n");
		writer.write(
				"        public Class<" + holderTypeName + "> getParent() { return (Class)" + holderTypeSimpleName + ".class; }\n\n");
		writer.write(
				"        public Class<" + methodTypeInfo.mappedTypeName + "> getReturnType() { return (Class)" + methodTypeInfo.typeLiteral + ".class; }\n\n");
		writer.write(
				"    };\n\n");
	}
	
	protected class ProcessedTypeInfo {
		public String typeName;
		public String mappedTypeName;
		public String typeLiteral;
		public ProcessedTypeInfo(TypeMirror type, Element errorTarget) {
			typeName = type.toString();
			mappedTypeName = mapPrimitiveTypeNames(typeName);
			typeLiteral = mappedTypeName;
			currentFieldTypeName = null;
			if (type.getKind() == TypeKind.TYPEVAR) {
				currentFieldTypeName = typeName;
				typeLiteral = "Object";
				mappedTypeName = "Object";
				// type parameters of the enclosing type are erased due to the static declaration
				TypeVariable typeVar = (TypeVariable) type;
				TypeParameterElement typeParameterElement = (TypeParameterElement) typeVar.asElement();
				if (typeParameterElement.getBounds().size() > 0) {
					// if the variable has bounds, eg 'T extends Comparable<T>', then the first bound is important, it will be the actual required type, see GenericBean2 in the test project
					TypeMirror firstBound = typeParameterElement.getBounds().get(0);
					String boundString = eraseTypeParametersFromString(firstBound.toString());
					if (!"java.lang.Object".equals(boundString)) {
						typeLiteral = boundString;
						mappedTypeName = boundString;
					}
				}
			}
			if (type.getKind() == TypeKind.DECLARED) {
				// the field type literal is the type qualified name without the type parameters
				DeclaredType declaredType = (DeclaredType) type;
				TypeElement typeElement = (TypeElement) declaredType.asElement();
				typeLiteral = typeElement.getQualifiedName().toString();
				// the type is present with the type parameters, but if the parameter is a type parameter of the enclosing type, it's replaced with a ? wildcard
				mappedTypeName = eraseTypeVariables(declaredType);
			}
			if (type.getKind() == TypeKind.ERROR) {
				// if the name contains dots then we assume it to be fully qualified
				if (!typeLiteral.contains(".")) {
					// if it does not, the generated will would probably not compile (if the given type is not in the same package)
					processingEnv.getMessager().printMessage(Kind.ERROR, "Please use a fully qualified type literal", errorTarget);
					throw new UnresolvedTypeException();
				}
			}
		}
	}
	
	public void emitPropertyLiteral(Writer writer, PropertyDescriptor descriptor, DeclaredType holderType, String holderTypeSimpleName) throws Exception {
		TypeMirror fieldType = descriptor.getType();
		ProcessedTypeInfo fieldTypeInfo = new ProcessedTypeInfo(fieldType, descriptor.getElement());
		String holderTypeName = eraseTypeVariables(holderType);
		String fieldName = descriptor.getFieldName();
		String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		String getterName = "get" + capitalizedFieldName;
		if (fieldTypeInfo.typeName.equals("boolean")) {
			getterName = "is" + capitalizedFieldName;
		}
		String setterName = "set" + capitalizedFieldName;
		String interfaceToImplement = descriptor.isObservable()?"ObservableProperty":"Property";
		String listenersName = "$$" + fieldName + "$listeners";
		writer.write("    public static final " + interfaceToImplement + "<"
				+ holderTypeName + "," + fieldTypeInfo.mappedTypeName + "> _" + fieldName + " = \n"
						+ "    new " + interfaceToImplement + "<" + holderTypeName + "," + fieldTypeInfo.mappedTypeName + ">() {\n"
						+ "    @Override public " + fieldTypeInfo.mappedTypeName + " getValue(" + holderTypeName + " instance) {\n"
						+ "        return (" + fieldTypeInfo.typeLiteral + ") instance." + getterName + "();\n"
						+ "    }\n");
		if (!descriptor.isReadonly()) {
			writer.write(
					"    @Override public void setValue(" + holderTypeName + " instance, " + fieldTypeInfo.mappedTypeName + " value) {\n"
					+ "        ((" + holderTypeSimpleName + ")instance)." + setterName + "((" + fieldTypeInfo.typeLiteral + ")value);\n"
					+ "    }\n");
		} else {
			// readonly attribute
			writer.write(
					"    @Override public void setValue(" + holderTypeName + " instance, " + fieldTypeInfo.mappedTypeName + " value) {\n"
					+ "        throw new UnsupportedOperationException(\"The field " + fieldName + " on type " + holderTypeName + " did not declare a setter.\");\n"
					+ "    }\n");
		}
		if (descriptor.isObservable()) {
			writer.write("        @Override public ListenerRegistration addChangeListener(" + holderTypeName + " object, ValueChangeListener<" + fieldTypeInfo.mappedTypeName + "> valueChangeListener) {\n"
                       + "            if (object." + listenersName + " == null) object. " + listenersName + " = new PropertyListeners();\n"
					   + "            return object." + listenersName + ".addListener(valueChangeListener);\n"
					   + "        }\n");
			writer.write("        @Override public void fireListeners(" + holderTypeName + " object, " + fieldTypeInfo.mappedTypeName + " newValue) {\n"
                       + "            if (object." + listenersName + "!= null)\n"
					   + "                object." + listenersName + ".fireListeners(newValue);\n"
					   + "        }\n");
		}
		writer.write(
						 "    @Override public String getName() {\n"
						+ "        return \"" + fieldName + "\";\n"
						+ "    }\n"
						+ "    @Override public Class<" + fieldTypeInfo.mappedTypeName + "> getType() {\n"
						+ "        return (Class)" + fieldTypeInfo.typeLiteral + ".class;\n"
						+ "    }\n"
						+ "    @Override public Class<" + holderTypeName + "> getParent() {\n"
						+ "        return (Class)" + holderTypeSimpleName + ".class;\n"
						+ "    }\n"
						+ "};\n\n");
	}
	
	/**
	 * Recursively cans for type arguments at full depth and replaces type variables with ? wildcards. Returns the resulting type reference string 
	 */
	public String eraseTypeVariables(DeclaredType declaredType) {
		String result = ((TypeElement) declaredType.asElement()).getQualifiedName().toString();
		if (!declaredType.getTypeArguments().isEmpty()) {
			List<String> parameterStrings = Lists.newArrayList();
			for (TypeMirror typeMirror : declaredType.getTypeArguments()) {
				parameterStrings.add(getTypeMirrorAsErasedString(typeMirror));
			}
			result += "<" + Joiner.on(",").join(parameterStrings) + ">";
		}
		return result;
	}
	
	public String getTypeMirrorAsErasedString(TypeMirror typeMirror) {
		if (typeMirror.getKind() == TypeKind.DECLARED) {
			// normal declared types parameters are kept
			return eraseTypeVariables((DeclaredType) typeMirror);
			
		}
		if (typeMirror.getKind() == TypeKind.TYPEVAR) {
			return "?";
		}
		if (typeMirror.getKind() == TypeKind.ERROR) {
			return "?";
		}
		if (typeMirror.getKind().isPrimitive()) {
			return mapPrimitiveTypeNames(typeMirror.toString());
		}
		return "?";
	}
	
	
	public static final Map<String, String> primitiveTypesMap = ImmutableMap.<String, String>builder()
			.put("void", "Void")
			.put("boolean", "Boolean")
				.put("byte", "Byte")
				.put("short", "Short")
				.put("char", "Character")
				.put("int", "Integer")
				.put("float", "Float")
				.put("long", "Long")
				.put("double", "Double")
				.build();
	
	public static String mapPrimitiveTypeNames(String typeName) {
		return Objects.firstNonNull(primitiveTypesMap.get(typeName), typeName);
	}
	
	public static String eraseTypeParametersFromString(String typeString) {
		return typeString.replaceAll("\\<.*\\>", "");
	}
	
	public static boolean typeImplements(TypeElement typeElement, String ifName) {
		for (TypeMirror ifMirror : typeElement.getInterfaces()) {
			if (ifName.equals(eraseTypeParametersFromString(ifMirror.toString())))
				return true;
		}
		if (typeElement.getSuperclass().getKind() == TypeKind.DECLARED) {
			TypeElement superTypeElement = (TypeElement) ((DeclaredType) typeElement.getSuperclass()).asElement();
			return typeImplements(superTypeElement, ifName);
		}
		return false;
	}
	
	public class UnresolvedTypeException extends RuntimeException {
		
	}
}
